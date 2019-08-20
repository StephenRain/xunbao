package com.inspiration.xunbao.crawler.context;


import com.inspiration.xunbao.crawler.page.WebPage;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放扫描到的带有注解@Page的类
 */
public class PageBeanContext {

    Map<String, WebPage> pageMap = new HashMap<>();

    public void addPage(WebPage page){
        pageMap.put(page.getClass().getName(),page);
    }



}
