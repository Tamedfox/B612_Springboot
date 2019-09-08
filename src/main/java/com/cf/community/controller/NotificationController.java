package com.cf.community.controller;

import com.cf.community.model.entity.Result;
import com.cf.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取当前用户的所有通知
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result getAllNotification(@PathVariable Integer page, @PathVariable Integer size){
        return Result.okOf(notificationService.findNotifiPageByUser(page, size));
    }

    /**
     * 更新通知状态至已读
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    public Result updateNotificationStatus(@PathVariable Long id){
        notificationService.updateStatus(id);
        return Result.okOf();
    }
}
