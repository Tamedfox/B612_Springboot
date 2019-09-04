package com.cf.community.dao;

import com.cf.community.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 角色访问接口
 */
public interface RoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {


}
