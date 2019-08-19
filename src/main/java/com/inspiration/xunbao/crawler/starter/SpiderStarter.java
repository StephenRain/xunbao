package com.inspiration.xunbao.crawler.starter;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.download.HttpClientDownloader;
import com.inspiration.xunbao.crawler.enums.PersistenceType;
import com.inspiration.xunbao.crawler.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Slf4j
public class SpiderStarter implements Runnable {

    private String backagePath;

    private String[] beginUrls;

    private int threadCount;

    ExecutorService threadPool;



    private SpiderStarter() {
    }

    ;

    public static SpiderStarter create() {
        return new SpiderStarter();
    }


    public SpiderStarter backagePath(String backagePath) {
        this.backagePath = backagePath;
        return this;
    }

    public SpiderStarter thread(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    public void start() {
        threadPool = Executors.newFixedThreadPool(this.threadCount);
        Future<?> future = threadPool.submit(this);
        try {
            Object o = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        log.debug("爬虫启动...");
        // 准备阶段，将初始URL组装为 HttpRequest
        List<HttpRequest> httpRequests = HttpRequest.ofAll(this.beginUrls);
        // 放入队列中
        HttpClientDownloader


        // 扫描包
        Reflections reflections = new Reflections(this.backagePath);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Page.class);
        classSet.forEach((page) -> {
            log.debug("发现Page类：" + page.getName());
            Page pageAnnotation = page.getAnnotation(Page.class);
            String name = pageAnnotation.name();
            String urlPattern = pageAnnotation.urlPattern();
            PersistenceType persistenceType = pageAnnotation.persistentType();






        });
        //

    }

    public SpiderStarter beginUrls(String... beginUrls) {
        this.beginUrls = beginUrls;
        return this;
    }
}
