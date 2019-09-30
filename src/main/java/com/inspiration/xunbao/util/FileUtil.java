package com.inspiration.xunbao.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class FileUtil {

    /**
     * 将字节数组写入到文件中
     *
     * @param bytes
     * @param destDir
     * @param fileName
     */
    public static void writeBytes(byte[] bytes, String destDir, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(destDir);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }

            file = new File(destDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(bos!= null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos!= null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
