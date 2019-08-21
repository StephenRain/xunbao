package com.inspiration.xunbao.crawler.page;

import com.inspiration.xunbao.crawler.annotations.PageType;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
public class JSONPage implements Page {
    @Override
    public PageType getType() {
        return PageType.JSON;
    }
}
