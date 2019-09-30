package com.inspiration.xunbao.service.impl;

import com.inspiration.xunbao.context.FileContext;
import com.inspiration.xunbao.entities.FileDesc;
import com.inspiration.xunbao.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Override
    public List<FileDesc> search(String input) {
        // 先从已上传的文件中查找
        ConcurrentHashMap.KeySetView<String, FileDesc> keySet = FileContext.digestMap.keySet();
        keySet.parallelStream().filter((e)->{
            Object filePath = FileContext.digestMap.get(e);
            if(filePath != null){
                String filePathStr = String.valueOf(filePath);
                // todo 重写查找的逻辑
                return filePathStr.contains(input);
            }
            return false;
        });

        // 找不到再从网上查找
        return null;
    }
}
