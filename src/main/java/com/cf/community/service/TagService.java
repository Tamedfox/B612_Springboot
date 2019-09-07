package com.cf.community.service;

import com.cf.community.dao.TagDao;
import com.cf.community.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * 查找所有的标签
     * @return
     */
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    /**
     * 查询前五条数据
     * @return
     */
//    public List<String> findFive(){
//        List<String> list = new ArrayList<>();
//        List<Tag> tagList = tagDao.findFirst5ByOrderByIdDesc();
//        for (Tag tag : tagList) {
//            list.add(tag.getName());
//        }
//        return list;
//    }
}
