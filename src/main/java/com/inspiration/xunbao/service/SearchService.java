package com.inspiration.xunbao.service;

import com.inspiration.xunbao.entities.FileDesc;

import java.util.List;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public interface SearchService {

    List<FileDesc> search(String input);
}
