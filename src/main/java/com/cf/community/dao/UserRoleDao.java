package com.cf.community.dao;

import com.cf.community.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRoleDao extends JpaRepository<UserRole,Long>, JpaSpecificationExecutor<UserRole> {

    /**
     * 根据用户id查找权限
     * @param id
     * @return
     */
    public List<UserRole> findByUserid(Long id);

}
