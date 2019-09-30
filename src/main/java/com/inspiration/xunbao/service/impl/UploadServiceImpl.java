package com.inspiration.xunbao.service.impl;

import com.inspiration.xunbao.entities.FileDesc;
import com.inspiration.xunbao.entities.UploadEntity;
import com.inspiration.xunbao.helper.SecretHelper;
import com.inspiration.xunbao.helper.ThreadPoolHelper;
import com.inspiration.xunbao.helper.UploadHelper;
import com.inspiration.xunbao.service.UploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
@Slf4j
@Service(value = "uploadService")
public class UploadServiceImpl implements UploadService {

    @SneakyThrows
    @Override
    public boolean upload(UploadEntity uploadEntity) {
        MultipartFile file = uploadEntity.getFile();
        String filename = file.getOriginalFilename();
        FileDesc fileDesc = null;
        // 校验
        try {
            fileDesc = SecretHelper.check(uploadEntity);
        } catch (Exception e) {
            log.error("文件上传失败:" + filename + ",mes:" + e.getMessage());
            return false;
        }
        UploadHelper uploadHelper = new UploadHelper(fileDesc);
        ThreadPoolHelper.getThreadPool().execute(uploadHelper);
        log.info("文件上传成功:" + filename);
        return true;
    }
}
