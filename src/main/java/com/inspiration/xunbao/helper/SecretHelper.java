package com.inspiration.xunbao.helper;

import com.inspiration.xunbao.context.FileContext;
import com.inspiration.xunbao.entities.FileDesc;
import com.inspiration.xunbao.entities.UploadEntity;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

import static com.inspiration.xunbao.exception.ExceptionMessage.DIGESTCONFLICT;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class SecretHelper {


    @SneakyThrows
    public static String digest(InputStream is) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        int ch;
        while ((ch = is.read()) != -1) {
            messageDigest.update((byte) ch);
        }
        byte[] digest = messageDigest.digest();
        return Arrays.toString(digest);
    }

    @SneakyThrows
    public static FileDesc check(UploadEntity uploadEntity) {
        MultipartFile file = uploadEntity.getFile();
        String fileName = file.getOriginalFilename();
        Objects.requireNonNull(file);
        InputStream is = file.getInputStream();
        String digest = digest(is);
        if (FileContext.digestMap.containsKey(digest)) {
            throw new RuntimeException(DIGESTCONFLICT.getDesc());
        } else {
            FileDesc fileDesc = new FileDesc();
            String destFilePath = FileContext.getDestFilePath(file.getOriginalFilename(),uploadEntity.getSpace());
            fileDesc.setDirPath(destFilePath.replace(fileName, ""));
            fileDesc.setFileName(fileName);
            fileDesc.setFilePath(FileContext.getDestFilePath(file.getOriginalFilename(), uploadEntity.getSpace()));
            fileDesc.setUploadEntity(uploadEntity);
            FileContext.digestMap.put(digest, fileDesc);
            return fileDesc;
        }
    }
}
