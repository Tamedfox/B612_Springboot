package com.cf.community.dao;

import com.cf.community.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * tag数据接口层
 */
public interface TagDao extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Tag> {


    /**
     *  根据列表查找tags
     * @param list
     * @return
     */
    List<Tag> findByIdIn(List<Long> list);
}
