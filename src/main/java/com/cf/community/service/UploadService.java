package com.cf.community.service;

import com.cf.community.exception.CustomizeException;
import com.cf.community.exception.ErrorCode;
import com.cf.community.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传逻辑层
 */
@Service
public class UploadService {

    @Value("${image.path}")
    private String filePath;

    @Value("${image.show.path}")
    private String showPath;

    @Autowired
    private UUIDUtil uuidUtil;


    /**
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        //获取图片原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件扩展名
        int i = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i);
        //获取新的文件名
        String fileName = uuidUtil.generateUUID() + extName;

        File targetFile = new File(filePath + '/' + fileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new CustomizeException(ErrorCode.UPLOAD_FAIL);
        }
        return showPath.replaceAll("\\*","") + fileName;
    }
}
