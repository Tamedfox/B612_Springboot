package com.cf.community.dao;

import com.cf.community.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 站点通知数据接口层
 */
public interface AnnouncementDao extends JpaRepository<Announcement,Long>, JpaSpecificationExecutor<Announcement> {

    /**
     * 按时间顺序排
     * @return
     */
    List<Announcement> findFirst5ByOrderByGmtCreateDesc();

}
