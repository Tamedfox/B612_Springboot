package com.cf.community.controller;


import com.cf.community.dao.TagDao;
import com.cf.community.model.Question;
import com.cf.community.model.entity.Result;
import com.cf.community.service.QuestionService;
import com.cf.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hot")
public class HotController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionService questionService;

    @Value("${redis.hot.questions.name}")
    private String hot_questions;

    @Value("${redis.hot.tags.name}")
    private String hot_tags;

    /**
     * 获取热门标签
     * @return
     */
    @GetMapping("/hotTags")
    public Result findHotTags(){
        List<String> hotTags = (List<String>) redisTemplate.opsForValue().get(hot_tags);
        //如果没有
        if(hotTags == null){
            //从数据库中取假数据
//            hotTags = tagService.findFive();
//            redisTemplate.opsForValue().set(hot_tags,hotTags);
        }
        return Result.okOf(hotTags);

    }

    @GetMapping("/hotQuestions")
    public Result findHotQuestions(){
        List<Question> hotQuestions = (List<Question>) redisTemplate.opsForValue().get(hot_questions);
        //如果没有，从数据库中取评论和浏览量排名的前六条
        if(hotQuestions == null){
//            hotQuestions = questionService.findSix();
//            redisTemplate.opsForValue().set(hot_questions,hotQuestions);
        }

        return Result.okOf(hotQuestions);
    }



}
