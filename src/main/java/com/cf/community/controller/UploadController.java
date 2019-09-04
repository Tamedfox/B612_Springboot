package com.cf.community.controller;

import com.cf.community.exception.ErrorCode;
import com.cf.community.model.entity.Result;
import com.cf.community.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/image/upload")
    public Result imageUpload(MultipartFile file){
        if(file == null){
            return Result.errorOf(ErrorCode.PARAMS_ERROR);
        }

        if(StringUtils.isBlank(file.getOriginalFilename())){
            return Result.errorOf(ErrorCode.FILE_FORMAT_ERROR);
        }

        String url = uploadService.uploadImage(file);
        return Result.okOf(url);
    }

}
