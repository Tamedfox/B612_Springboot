package com.cf.community.controller;

import com.cf.community.model.Tag;
import com.cf.community.model.entity.Result;
import com.cf.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * tag数据控制层
 */

@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查找所有tag
     * @return
     */
    @GetMapping()
    public Result findAll(){
        return Result.okOf(tagService.findAll());
    }

}
