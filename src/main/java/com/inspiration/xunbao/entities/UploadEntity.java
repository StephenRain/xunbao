package com.inspiration.xunbao.entities;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
@Data
public class UploadEntity {

    // 存储空间
    private String space;

    // 上传者
    private String uploads;

    // 资源
    private MultipartFile file;

    // 资源类型
    private String type;

    // 标签
    private String labels;


}
