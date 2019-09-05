package com.cf.community;

import com.cf.community.dao.TagDao;
import com.cf.community.dao.UserDao;
import com.cf.community.dao.UserRoleDao;
import com.cf.community.model.Tag;
import com.cf.community.service.RoleServcie;
import com.cf.community.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private UserRoleDao userRoleDao;
//
//    @Autowired
//    private RoleServcie roleServcie;

    @Test
    public void contextLoads() {
    }

//    @Test
//    public void addUser(){
//        User user = new User();
//        user.setUsername("1233");
//        user.setPassword("22222");
//        userDao.save(user);
//    }

//    @Test
//    public void testUserRoleDao(){
//        UserRole userRole = new UserRole();
//        userRole.setUserid(2L);
//        userRole.setRoleid(3L);
//        userRoleDao.save(userRole);
//    }

//    @Test
//    public void testUserRoleDao(){
//        List<Role> roles = roleServcie.findByUserId(2L);
//        for (Role role : roles) {
//            System.out.println(role.getDescription());
//        }
//    }

//    @Autowired
//    private QuestionDao questionDao;
//
//    @Test
//    public void testCommentCount(){
//        questionDao.updateCommentCount(1L);
//    }


//    @Test
//    public void testSub(){
//        String file = "1234.jpg";
//        int i = file.lastIndexOf(".");
//        String substring = file.substring(i);
//        System.out.println(substring);
//    }
//    @Autowired
//    private HttpServletRequest request;
//
//    @Test
//    public void test(){
//        String contextPath = request.getSession().getServletContext().getContextPath();
//        System.out.println(contextPath);
//        File file = new File( "G:/images/123.jpg");
//        System.out.println("OOK?");
//    }

    @Autowired
    private TagService tagService;

    @Test
    public void testDao(){
        List<Tag> tags = tagService.findAll();
        System.out.println(tags.toString());
    }
}