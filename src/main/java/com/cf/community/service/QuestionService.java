package com.cf.community.service;

import com.cf.community.dao.CommentDao;
import com.cf.community.dao.QuestionDao;
import com.cf.community.dao.TagDao;
import com.cf.community.dao.UserDao;
import com.cf.community.exception.CustomizeException;
import com.cf.community.exception.ErrorCode;
import com.cf.community.model.Question;
import com.cf.community.model.Tag;
import com.cf.community.model.User;
import com.cf.community.model.dto.QuestionDTO;
import com.cf.community.model.dto.UserDTO;
import com.cf.community.model.entity.PageResult;
import com.cf.community.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(1L);
        question.setViewCount(0);
        question.setLikeCount(0);
        question.setCommentCount(0);
        questionDao.save(question);
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
        return questionDao.findByCreator(user.getId(),pageRequest);
    }

}
