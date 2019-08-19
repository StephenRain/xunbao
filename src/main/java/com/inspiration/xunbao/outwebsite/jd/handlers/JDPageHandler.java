package com.inspiration.xunbao.outwebsite.jd.handlers;

import com.inspiration.xunbao.crawler.handler.PageHandler;
import com.inspiration.xunbao.crawler.page.WebPage;
import org.w3c.dom.Document;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
public class JDPageHandler<T extends WebPage> implements PageHandler<T> {


    @Override
    public void beforeDownload() {


    }

    @Override
    public void afterDownload(Document document) {
        // 放入到队列中

    }
}
