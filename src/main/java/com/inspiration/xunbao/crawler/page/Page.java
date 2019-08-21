package com.inspiration.xunbao.crawler.page;

import com.inspiration.xunbao.crawler.annotations.PageType;

/**
 * 所有要爬取页面的公共接口
 */
public interface Page {

    /**
     * 返回页面类型：
     * @return
     */
    PageType getType();

}
