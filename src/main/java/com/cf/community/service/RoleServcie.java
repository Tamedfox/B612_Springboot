package com.cf.community.service;

import com.cf.community.dao.RoleDao;
import com.cf.community.dao.UserRoleDao;
import com.cf.community.model.Role;
import com.cf.community.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据逻辑类
 */

@Service
public class RoleServcie {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 根据用户id查找管理角色
     * @param id
     * @return
     */
    public List<Role> findByUserId(Long id){
        List<Role> list = new ArrayList<>();
        //查找中间表的多个结果
        List<UserRole> userRoles = userRoleDao.findByUserid(id);
        if(userRoles != null){
            for (UserRole userRole : userRoles) {
                Role role = roleDao.findById(userRole.getRoleid()).get();
                    list.add(role);
            }
        }
        return list;
    }

}
