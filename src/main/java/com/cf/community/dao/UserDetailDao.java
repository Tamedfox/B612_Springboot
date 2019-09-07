package com.cf.community.dao;

import com.cf.community.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户详细信息数据接口
 */
public interface UserDetailDao extends JpaRepository<UserDetail,Long>, JpaSpecificationExecutor<UserDetail> {
}
