package com.inspiration.xunbao.handlers;

import com.inspiration.xunbao.pages.WebPage;
import org.w3c.dom.Document;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public interface PageHandler<T extends WebPage> {

    BlockingQueue<WebPage> pagesQueue = new LinkedBlockingQueue<>();

    void beforeDownload();

    void afterDownload(Document document);

}
