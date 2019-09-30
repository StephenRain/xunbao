package com.inspiration.xunbao.entities;

import lombok.Data;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
@Data
public class FileDesc {

    private String fileName;

    // 文件路径
    private String filePath;

    // 文件所在目录
    private String dirPath;

    // 上传时的参数
    private UploadEntity uploadEntity;

}
