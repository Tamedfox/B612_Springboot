package com.cf.community.service;

import com.cf.community.dao.CommentDao;
import com.cf.community.dao.NotificationDao;
import com.cf.community.dao.QuestionDao;
import com.cf.community.dao.UserDao;
import com.cf.community.model.Comment;
import com.cf.community.model.Notification;
import com.cf.community.model.Question;
import com.cf.community.model.User;
import com.cf.community.model.dto.NotificationDTO;
import com.cf.community.model.entity.PageResult;
import com.cf.community.model.enums.NotificationEnum;
import com.cf.community.model.enums.NotificationStatusEnum;
import com.cf.community.util.JwtUtil;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 通知逻辑层
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest request;

    /**
     * 添加评论相关的通知
     * @param comment
     */
    public void addNotification(Comment comment, NotificationEnum notificationEnum){
        //获取当前用户--发送通知人
        User notifierUser = userDao.findByUsername(jwtUtil.getUsernameFromRequest(request));
        //获取接收用户--接收通知人
        Question question = questionDao.findById(comment.getParentId()).get();
        User receiverUser = userDao.findById(question.getCreator()).get();
        //设置通知消息内容
        Notification notification = assemNotification(notificationEnum, notifierUser, question, receiverUser);

        notificationDao.save(notification);
    }

    /**
     * 设置通知消息
     * @param notificationEnum
     * @param notifierUser
     * @param question
     * @param receiverUser
     * @return
     */
    private Notification assemNotification(NotificationEnum notificationEnum, User notifierUser, Question question, User receiverUser) {
        Notification notification = new Notification();
        notification.setNotifier(notifierUser.getId());//通知人id
        notification.setNotifierName(notifierUser.getNickname());//通知人name
        notification.setReceiver(receiverUser.getId());//接收人id
        notification.setOuterId(question.getId());//问题id
        notification.setOuterTitle(question.getTitle());//问题标题
        notification.setType(notificationEnum.getType());//通知类型
        notification.setGmtCreate(System.currentTimeMillis());//通知创建时间
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());//设置未读
        return notification;
    }

    /**
     * 添加问题相关的通知
     * @param question
     * @param notificationEnum
     */
    public void addNotification(Question question, NotificationEnum notificationEnum){
        //获取当前用户--发送通知人
        User notifierUser = userDao.findByUsername(jwtUtil.getUsernameFromRequest(request));
        //获取接收用户--接收通知人
        User receiverUser = userDao.findById(question.getCreator()).get();
        //设置通知消息内容
        Notification notification = assemNotification(notificationEnum, notifierUser, question, receiverUser);

        notificationDao.save(notification);
    }


    /**
     * 查找当前用户的所有通知
     * @return
     * @param page
     * @param size
     */
    public PageResult<Notification> findNotifiPageByUser(Integer page, Integer size){
        User user = userDao.findByUsername(jwtUtil.getUsernameFromRequest(request));
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<Notification> notificationPage = notificationDao.findByReceiverAndStatusEqualsOrderByGmtCreateDesc(user.getId(),0, pageRequest);
        List<Notification> notificationList = notificationPage.getContent();
        List<NotificationDTO> result = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType(NotificationEnum.nameOfType(notification.getType()));
            result.add(notificationDTO);
        }
        return new PageResult(notificationPage.getTotalElements(),result);
    }

    /**
     * 更新通知状态至已读
     */
    @Transactional
    public void updateStatus(Long id) {
        Notification notification = notificationDao.findById(id).get();
        notification.setStatus(1);
        notificationDao.save(notification);
    }
}
