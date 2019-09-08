package com.cf.community.dao;

import com.cf.community.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 通知数据接口类
 */
public interface NotificationDao extends JpaRepository<Notification,Long>, JpaSpecificationExecutor<Notification> {

    /**
     * 根据收信人id查找所有通知，降序排列
     * @param receiver
     * @return
     */
    Page<Notification> findByReceiverAndStatusEqualsOrderByGmtCreateDesc(Long receiver,Integer status, Pageable pageable);

}
