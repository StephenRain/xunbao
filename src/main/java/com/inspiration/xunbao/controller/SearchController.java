package com.inspiration.xunbao.controller;

import com.inspiration.xunbao.entities.FileDesc;
import com.inspiration.xunbao.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yaotianchi
 * @date 2019/9/29
 */
@Slf4j
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "search")
    public String search(String input){
        log.info("input:" + input);
        List<FileDesc> search = searchService.search(input);
        return "pages/search/search-res";
    }
}
