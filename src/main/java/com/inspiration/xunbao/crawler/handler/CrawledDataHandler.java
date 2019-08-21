package com.inspiration.xunbao.crawler.handler;

import com.inspiration.xunbao.crawler.http.HttpRes;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
public interface CrawledDataHandler {

    void handle(HttpRes httpRes);

}
