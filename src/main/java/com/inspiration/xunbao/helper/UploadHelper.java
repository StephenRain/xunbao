package com.inspiration.xunbao.helper;

import com.inspiration.xunbao.entities.FileDesc;
import com.inspiration.xunbao.util.FileUtil;

import java.io.IOException;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class UploadHelper implements Runnable {

    private FileDesc fileDesc;

    public UploadHelper(FileDesc fileDesc) {
        this.fileDesc = fileDesc;
    }

    @Override
    public void run() {
        try {
            byte[] bytes = fileDesc.getUploadEntity().getFile().getBytes();
            FileUtil.writeBytes(bytes,fileDesc.getDirPath(),fileDesc.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
