package com.inspiration.xunbao.handlers.jd;

import com.inspiration.xunbao.handlers.PageHandler;
import com.inspiration.xunbao.pages.WebPage;
import org.w3c.dom.Document;

import java.util.concurrent.BlockingQueue;

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
