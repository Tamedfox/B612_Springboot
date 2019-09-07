package com.cf.community;

import com.cf.community.dao.SearchDao;
import com.cf.community.dao.TagDao;
import com.cf.community.dao.UserDao;
import com.cf.community.dao.UserRoleDao;
import com.cf.community.model.Question;
import com.cf.community.model.Tag;
import com.cf.community.model.entity.QuestionSearch;
import com.cf.community.service.QuestionService;
import com.cf.community.service.RoleServcie;
import com.cf.community.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.PriorityQueue;

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
//
//    @Autowired
//    private TagService tagService;
//
//    @Test
//    public void testDao(){
//        List<Tag> tags = tagService.findAll();
//        System.out.println(tags.toString());
//    }

//    @Autowired
//    private SearchDao searchDao;
//
//    @Test
//    public void test(){
////        Iterable<QuestionSearch> all = searchDao.findAll();
////        all.forEach(System.out::println);
//        PageRequest pageRequest = PageRequest.of(0, 5);
//
//        Page<QuestionSearch> questionSearchPage = searchDao.findByTitleOrDescriptionLike("测试", "测试", pageRequest);
//        System.out.println(questionSearchPage.getTotalElements());
//    }

//    @Autowired
//    private QuestionService questionService;
//
//    @Test
//    public void test(){
//        Question question = new Question();
//        question.setTitle("rabbitmq测试");
//        question.setDescription("mq测试");
//        questionService.add(question);
//        System.out.println("hello");
//    }


//    @Test
//    public void findKMax(){
//        int[] nums = new int[]{1, 6, 2, 3, 5, 4, 8, 7, 9};
//
//        PriorityQueue<Integer> q = new PriorityQueue<>(3);
//
//        for (int num : nums) {
//            if(q.size() < 3){
//                q.offer(num);
//            }else if(q.peek() < num){
//                q.poll();
//                q.offer(num);
//            }
//        }
//
//        while (!q.isEmpty()){
//            System.out.println(q.poll());
//        }
//
//    }


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedist(){
        redisTemplate.opsForValue().set("test","test数据");
        System.out.println("放入数据");
//        取出
        String test = (String) redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }
}
