package com.inspiration.xunbao.crawler.dao;

import com.inspiration.xunbao.crawler.page.HtmlPage;

/**
 * 用来将已经被数据渲染后的Page类持久化到数据库等操作
 *
 * @param <T>
 */
public interface PageDao<T extends HtmlPage> {

    void dao(HtmlPage htmlPage);


}
