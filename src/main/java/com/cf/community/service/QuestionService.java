package com.cf.community.service;

import com.cf.community.dao.*;
import com.cf.community.exception.CustomizeException;
import com.cf.community.exception.ErrorCode;
import com.cf.community.model.Question;
import com.cf.community.model.Tag;
import com.cf.community.model.User;
import com.cf.community.model.dto.QuestionDTO;
import com.cf.community.model.dto.UserDTO;
import com.cf.community.model.entity.PageResult;
import com.cf.community.model.entity.QuestionSearch;
import com.cf.community.util.JwtUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 问题数据逻辑层
 */
@Service
public class QuestionService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private SearchDao searchDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 查找所有问题数据
     * @return
     */
    public List<Question> findAll(){
        return questionDao.findAll();
    }

    /**
     * 根据主键查询实体
     * @param id
     * @return
     */
    @Transactional
    public QuestionDTO findById(Long id){
        //浏览数+1
        questionDao.updataViewCount(id);
        //查找问题详情
        Question question = questionDao.findById(id).get();
        if(question == null){
            throw new CustomizeException(ErrorCode.NOT_FOUND);
        }
        //查找用户
        User user = userDao.findById(question.getCreator()).get();

        //查找标签
        String tagStr = question.getTag();
        String[] split = tagStr.split(",");
        List<String> list = new ArrayList<String>(split.length);
        Collections.addAll(list,split);
        List<Long> tagsIdLislt = list.stream().map(i -> Long.valueOf(i)).collect(Collectors.toList());
        List<Tag> tagList = tagDao.findByIdIn(tagsIdLislt);

        //封装到questionDTO和userDTO
        QuestionDTO questionDTO = new QuestionDTO();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(question,questionDTO);
        BeanUtils.copyProperties(user,userDTO);
        questionDTO.setUserDTO(userDTO);
        questionDTO.setTags(tagList);
        return questionDTO;
    }

    /**
     * 增加
     * @param question
     */
    public void add(Question question){
        User user = userDao.findByUsername(jwtUtil.getUsernameFromRequest(request));
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setViewCount(0);
        question.setLikeCount(0);
        question.setCommentCount(0);
        Question questionSave = questionDao.save(question);
        //异步发送搜索索引
        this.sendSearchInfo(questionSave,"add");
    }

    /**
     * 更新
     * @param id
     * @param title
     * @param description
     * @param tag
     */
    public void update(Long id,String title,String description,String tag){
        Question question = questionDao.findById(id).get();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtModified(System.currentTimeMillis());
        questionDao.save(question);
        //更新搜索数据
        this.sendSearchInfo(question,"update");
    }

    /**
     * 删除
     * @param id
     */
    @Transactional
    public void delete(Long id){
        //删除问题，同时删除问题下评论
        questionDao.deleteById(id);
        commentDao.deleteByParentIdEqualsAndTypeEquals(id,1);
        //更新搜索数据
        this.sendSearchInfo(questionDao.findById(id).get(),"delete");
    }

    /**
     * 分页查找
     * @param page
     * @param size
     * @return
     */
    public PageResult<QuestionDTO> findByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<Question> questionPages = questionDao.findAll(pageRequest);
        List<Question> questionList = questionPages.getContent();
        //获取user的列表
        Set<Long> userSets = questionList.stream().map(question -> question.getCreator()).collect(Collectors.toSet());
        List<User> userList = userDao.findByIdIn(userSets);
        //转换为map
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //将问题与user匹配
        List<QuestionDTO> list = questionList.stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userMap.get(question.getCreator()), userDTO);
            questionDTO.setUserDTO(userDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return new PageResult<>(questionPages.getTotalElements(),list);
    }

    /**
     * 喜爱数+1
     * @param id
     * @return
     */
    public Question updateLikeCount(Long id) {
        questionDao.updateLikeCount(id);
        Question question = questionDao.findById(id).get();
        return question;
    }

    /**
     * 分页查找当前用户的提问
     * @param page
     * @param size
     * @return
     */
    public Page<Question> findQuestionByUser(Integer page,Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        User user = userDao.findByUsername(jwtUtil.getUsernameFromRequest(request));
        Page<Question> questionPage = questionDao.findByCreator(user.getId(), pageRequest);

        return questionPage;
    }

    /**
     * 关键字分页查询
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public Page<QuestionSearch> search(String keyword, Integer page, Integer size) {

        //使用elasticsearch进行搜索
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<QuestionSearch> searchPage = searchDao.findByTitleOrDescriptionLike(keyword, keyword, pageRequest);
        return searchPage;
    }

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    /**
     * 发送搜索信息
     * @param question
     */
    public void sendSearchInfo(Question question,String action){
        Map<String,String> map = new HashMap<>();
        map.put("action",action);
        map.put("id",String.valueOf(question.getId()));
        map.put("title",question.getTitle());
        map.put("description",question.getDescription());
        rabbitTemplate.convertAndSend(queueName,map);
    }

    /**
     * 从数据库中取出假热门数据
     * @return
     */
//    public List<Question> findSix() {
//        return questionDao.findAll();
//    }
}
