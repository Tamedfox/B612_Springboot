package com.cf.community.service;

import com.cf.community.dao.AnnouncementDao;
import com.cf.community.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    public List<Announcement> findFive(){
        return announcementDao.findFirst5ByOrderByGmtCreateDesc();
    }

}
