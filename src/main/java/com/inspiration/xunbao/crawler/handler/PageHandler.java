package com.inspiration.xunbao.crawler.handler;

import com.inspiration.xunbao.crawler.page.WebPage;
import org.w3c.dom.Document;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface PageHandler<T extends WebPage> {

    BlockingQueue<WebPage> pagesQueue = new LinkedBlockingQueue<>();

    void beforeDownload();

    void afterDownload(Document document);

}
