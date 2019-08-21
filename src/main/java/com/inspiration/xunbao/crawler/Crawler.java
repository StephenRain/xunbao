package com.inspiration.xunbao.crawler;

import com.inspiration.xunbao.crawler.context.ProxyContext;
import com.inspiration.xunbao.crawler.download.Download;
import com.inspiration.xunbao.crawler.download.HttpClientDownloader;
import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import com.inspiration.xunbao.crawler.mapping.HttpRequestPageMapping;
import com.inspiration.xunbao.crawler.handler.DefaultHandler;
import com.inspiration.xunbao.crawler.queue.RequestPageMappingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * 真正的爬虫，负责从队列中获取需要请求的数据，然后爬取对应网页，
 * 然后提取指定数据,再将提取后的数据放入到队列中
 *
 * @author yaotianchi
 * @date 2019/8/21
 */
@Slf4j
public class Crawler extends Thread {

    private Download downloader = HttpClientDownloader.getInstance();

    public Crawler() {
    }

    public Crawler(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (!RequestPageMappingQueue.out) {
            HttpRequestPageMapping requestPageMapping = RequestPageMappingQueue.mappingLocalQueue.poll();
            if (requestPageMapping == null) {
                RequestPageMappingQueue.out = true;
                log.info("待请求队列已为空，结束获取请求");
                break;
            }
            HttpRequest request = requestPageMapping.getRequest();
            HttpRes httpRes = downloader.download(request, ProxyContext.useProxy);
            // todo 选择合适的方式处理结果
            new DefaultHandler().handle(httpRes);
        }
    }
}
