package com.cf.community.dao;

import com.cf.community.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论数据访问接口
 */
public interface CommentDao extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {

    List<Comment> findByParentId(Long parentId);

    @Modifying
    @Transactional
    @Query("update Comment c set c.likeCount = c.likeCount + 1 where c.id = ?1")
    void updateLikeCount(Long id);

    void deleteByParentIdEqualsAndTypeEquals(Long id,Integer type);
}
