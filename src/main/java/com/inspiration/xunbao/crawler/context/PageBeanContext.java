package com.inspiration.xunbao.crawler.context;


import com.inspiration.xunbao.crawler.page.HtmlPage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 存放扫描到的带有注解@Page的类
 */
public class PageBeanContext {

    /**
     * key：Page类的全类名
     * Value:该Page类
     */
    final static Map<String, HtmlPage> pageClassMap = new HashMap<>();

    /**
     * 将实现了com.inspiration.xunbao.crawler.handler.CrawledDataHandler接口的Page类
     * 的类对象放入到这里
     */
    public final static Set<Class> dataHandlerPages = new HashSet<>();



    public static void put(HtmlPage page) {
        pageClassMap.put(page.getClass().getName(), page);
    }


}
