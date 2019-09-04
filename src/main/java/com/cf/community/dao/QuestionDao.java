package com.cf.community.dao;

import com.cf.community.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 问题数据访问接口
 */
public interface QuestionDao extends JpaRepository<Question,Long>, JpaSpecificationExecutor<Question> {

    @Modifying
    @Transactional
    @Query("update Question q set q.commentCount = q.commentCount + 1 where q.id = ?1")
    void updateCommentCount(Long parentId);

    @Modifying
    @Transactional
    @Query("update Question q set q.viewCount = q.viewCount + 1 where q.id = ?1")
    void updataViewCount(Long id);

    @Modifying
    @Transactional
    @Query("update Question q set q.likeCount = q.likeCount + 1 where q.id = ?1")
    void updateLikeCount(Long id);

    Page<Question> findByCreator(Long id, Pageable pageable);
}
