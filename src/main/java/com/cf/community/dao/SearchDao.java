package com.cf.community.dao;

import com.cf.community.model.entity.QuestionSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 搜索dao层
 */

public interface SearchDao extends ElasticsearchRepository<QuestionSearch,Long> {


    /**
     * 搜索
     * @param title
     * @param description
     * @param pageable
     * @return
     */
    public Page<QuestionSearch> findByTitleOrDescriptionLike(String title, String description, Pageable pageable);

}
