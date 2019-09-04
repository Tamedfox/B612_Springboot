package com.cf.community.service;

import com.cf.community.dao.CommentDao;
import com.cf.community.dao.QuestionDao;
import com.cf.community.dao.UserDao;
import com.cf.community.model.Comment;
import com.cf.community.model.Question;
import com.cf.community.model.User;
import com.cf.community.model.dto.CommentDTO;
import com.cf.community.model.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 评论数据逻辑接口
 */

@Service
public class CommentServcie {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    /**
     * 查找所有的评论
     * @return
     */
    public List<CommentDTO> findAll(){
        List<Comment> commentList = commentDao.findAll();
        List<CommentDTO> list = getCommentDTOS(commentList);

        return list;
    }

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    public Comment findById(Long id){
        return commentDao.findById(id).get();
    }

    /**
     * 新增
     * @param comment
     */
    public void add(Comment comment, Long parentId){
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setParentId(parentId);
        comment.setCommentator(1L);
        comment.setLikeCount(0);
        comment.setType(1);
        commentDao.save(comment);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id){
        commentDao.deleteById(id);
    }

    /**
     * 根据parentId查找commen列表
     * @param id
     * @return
     */
    public List<CommentDTO> findByParentId(Long id) {
        List<Comment> commentList = commentDao.findByParentId(id);
        return getCommentDTOS(commentList);
    }

    /**
     * 新增评论
     * @param comment
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Comment comment) {
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setType(1);
        comment.setCommentator(1L);
        questionDao.updateCommentCount(comment.getParentId());
        commentDao.save(comment);
    }

    /**
     * 将传入的评论列表转换为带用户信息的CommentDTO
     * @param commentList
     * @return
     */
    private List<CommentDTO> getCommentDTOS(List<Comment> commentList) {
        List<CommentDTO> list = new ArrayList<>();
        //java8新特性转为用户主键的set集合
        Set<Long> commentors = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
//        List<Long> userIds = new ArrayList<>();
//        userIds.addAll(commentors);
        //查询用户
        List<User> users = userDao.findByIdIn(commentors);
        //将user列表转化为map集合
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //将用户信息和评论信息匹配
        list = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            BeanUtils.copyProperties(userMap.get(comment.getCommentator()), userDTO);
            commentDTO.setUserDTO(userDTO);
            return commentDTO;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 更新comnent的likeCount
     * @param id
     * @return
     */
    public Comment updateLikeCount(Long id) {
        commentDao.updateLikeCount(id);
        return commentDao.findById(id).get();
    }
}
