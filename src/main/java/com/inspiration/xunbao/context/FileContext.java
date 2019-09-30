package com.inspiration.xunbao.context;

import com.inspiration.xunbao.entities.FileDesc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class FileContext {

    public static final String RES_DEST_ROOT_DIR = "D:\\data\\xunbao\\upload\\";

    /**
     * key:文件摘要
     * value:文件信息
     */
    public static ConcurrentHashMap<String, FileDesc> digestMap = new ConcurrentHashMap<>(100);

    /**
     * @param fileName
     * @return
     */
    public static String getDestFilePath(String fileName, String space) {
        // 根据时间来生成,一天一个目录
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        String filePath = FileContext.RES_DEST_ROOT_DIR + space + File.separator + dateStr + fileName;
        return filePath;
    }
}
