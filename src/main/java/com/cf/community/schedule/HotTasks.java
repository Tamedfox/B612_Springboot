package com.cf.community.schedule;

import com.cf.community.dao.QuestionDao;
import com.cf.community.model.Question;
import com.cf.community.service.HotQuestionService;
import com.cf.community.service.HotTagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 热门算法：
 * 热门标签：score = 5 * 有此标签的问题数 + 此问题下的评论数
 * 热门问题：score = 评论数 * 3 + 点赞数
 */
@Component
@Slf4j
public class HotTasks {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private HotTagService hotTagService;

    @Autowired
    private HotQuestionService hotQuestionService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${redis.hot.questions.name}")
    private String hot_questions;

    @Value("${redis.hot.tags.name}")
    private String hot_tags;

    /**
     * 计算热门排名
     */
//    @Scheduled(fixedRate = 100000000)
    @Scheduled(cron = "0 0 1 * * * ")//每天凌晨一点执行
    public void hotTagSchedule(){
        int page = 1;
        int size = 20;
        log.info("HotTag & Question schedule start is {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        List<Question> QuestionList= new ArrayList<>();

        Map<Long, Integer> tagPriorities = new HashMap<>();
        Map<Long, Integer> questionPriorities = new HashMap<>();

        while(true){
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            Page<Question> questionPage = questionDao.findAll(pageRequest);
            QuestionList = questionPage.getContent();

            for (Question question : QuestionList) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    //热门标签算法
                    tagPriorities.put(Long.valueOf(tag),tagPriorities.getOrDefault(Long.valueOf(tag),0) + 5 + question.getCommentCount());
                    questionPriorities.put(question.getId(),3 * question.getCommentCount() + 2 * question.getLikeCount() + question.getViewCount());
                }
            }
            if(page == (questionPage.getTotalElements()/size + 1) ){
                break;
            }
            page++;
        }
        List<String> hotTags = hotTagService.upadteHotTags(tagPriorities);
        List<Question> hotQuestions = hotQuestionService.updateHotQuestions(questionPriorities);

        Collections.reverse(hotQuestions);
        Collections.reverse(hotTags);

        redisTemplate.delete(hot_questions);
        redisTemplate.opsForValue().set(hot_questions,hotQuestions);
        redisTemplate.delete(hot_tags);
        redisTemplate.opsForValue().set(hot_tags,hotTags);

        log.info("HotTag & Question schedule stop is {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }

}
