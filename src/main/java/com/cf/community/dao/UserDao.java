package com.cf.community.dao;

import com.cf.community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
 * 用户数据接口层
 */
public interface UserDao extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 查找Set中的用户
     * @param ids
     * @return
     */
    public List<User> findByIdIn(Set<Long> ids);

    /**
     * 查找最新的五个用户
     * @param state
     * @return
     */
    List<User> findFirst5ByStateOrderByCmtCreateDesc(Integer state);

    User findByNickname(String nickname);
}
