package com.cf.community.controller;

import com.cf.community.dao.QuestionDao;
import com.cf.community.exception.ErrorCode;
import com.cf.community.model.Question;
import com.cf.community.model.dto.QuestionDTO;
import com.cf.community.model.entity.PageResult;
import com.cf.community.model.entity.QuestionSearch;
import com.cf.community.model.entity.Result;
import com.cf.community.model.enums.NotificationEnum;
import com.cf.community.service.NotificationService;
import com.cf.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    /**
     * 查找全部问题
     * @return
     */
    @GetMapping("/index")
    public Result<List> findAll(){
        return Result.okOf(questionService.findAll());
    }

    /**
     * 分页查找
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/{page}/{size}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer size){
        PageResult<QuestionDTO> pageResult = questionService.findByPage(page, size);
        return Result.okOf(pageResult);
    }

    /**
     * 根据id查找问题
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findOne(@PathVariable Long id){
        try{
            return Result.okOf(questionService.findById(id));
        } catch (RuntimeException e){
            return Result.errorOf(ErrorCode.NOT_FOUND);
        }
    }

    /**
     * 增加
     * @param question
     * @return
     */
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public Result add(Question question){
        questionService.add(question);
        return Result.okOf();
    }

    /**
     * 问题点赞
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/like/{id}")
    public Result updateLikeCount(@PathVariable Long id){
        Question question = questionService.updateLikeCount(id);
        //通知消息
        notificationService.addNotification(question, NotificationEnum.LIKE_QUESTION);
        return Result.okOf(question.getLikeCount());
    }

    /**
     * 更新
     * @param id
     * @param question
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable Long id,@RequestBody Question question){
        questionService.update(id,question.getTitle(),question.getDescription(),question.getTag());
        return Result.okOf();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value= "{id}")
    public Result delete(@PathVariable Long id){
        questionService.delete(id);
        return Result.okOf();
    }

    /**
     * 查找当前用户的问题
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/myquestion/{page}/{size}")
    public Result findQuestionByUser(@PathVariable Integer page, @PathVariable Integer size){
        if(page <= 0 | size <= 0){
            return Result.errorOf(ErrorCode.PARAMS_ERROR);
        }
        Page<Question> questionPage = questionService.findQuestionByUser(page, size);
        return Result.okOf(new PageResult<>(questionPage.getTotalElements(),questionPage.getContent()));
    }

    /**
     * 关键字分页搜索
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{keyword}/{page}/{size}")
    public Result search(@PathVariable String keyword, @PathVariable Integer page, @PathVariable Integer size){
        Page<QuestionSearch> pageResult = questionService.search(keyword,page,size);
        return Result.okOf(new PageResult<QuestionSearch>(pageResult.getTotalElements(),pageResult.getContent()));
    }
}
