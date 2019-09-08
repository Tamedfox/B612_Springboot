package com.cf.community.controller;

import com.cf.community.model.Comment;
import com.cf.community.model.entity.Result;
import com.cf.community.model.enums.NotificationEnum;
import com.cf.community.service.CommentServcie;
import com.cf.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentServcie commentServcie;

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取全部评论
     * @return
     */
    @GetMapping
    public Result findAll(){
        return Result.okOf(commentServcie.findAll());
    }

    /**
     * 根据父类查找评论
     * @param parentId
     * @return
     */
    @GetMapping("/list/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        return Result.okOf(commentServcie.findByParentId(parentId));
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */

    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    @Transactional
    public Result add(Comment comment){
        //保存评论
        commentServcie.add(comment);
        //通知消息
        notificationService.addNotification(comment, NotificationEnum.REPLY_QUESTION);
        return Result.okOf();
    }

    /**
     * 根据主键更新comemnt的likeCount
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/like/{id}")
    public Result updateCommentLike(@PathVariable Long id){
        //保存点赞数
        Comment comment = commentServcie.updateLikeCount(id);
        //通知消息
        notificationService.addNotification(comment, NotificationEnum.LIKE_COMMENT);
        return Result.okOf(comment.getLikeCount());
    }
}
