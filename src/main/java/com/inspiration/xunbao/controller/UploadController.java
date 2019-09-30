package com.inspiration.xunbao.controller;

import com.inspiration.xunbao.entities.UploadEntity;
import com.inspiration.xunbao.service.UploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaotianchi
 * @date 2019/9/29
 */
@Slf4j
@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    private Map<String,Object> map = new HashMap<>(200);

    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(UploadEntity uploadEntity) {
        MultipartFile file = uploadEntity.getFile();
        log.info("接受文件:" + file.getOriginalFilename());
        boolean upload = uploadService.upload(uploadEntity);
        return String.valueOf(upload);
    }
}
