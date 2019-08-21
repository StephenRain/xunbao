package com.inspiration.xunbao.crawler.starter;

import com.inspiration.xunbao.crawler.Crawler;
import com.inspiration.xunbao.crawler.context.ProxyContext;
import com.inspiration.xunbao.crawler.enums.PersistenceType;
import com.inspiration.xunbao.crawler.init.PageClassInit;
import com.inspiration.xunbao.crawler.util.AsyncProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Slf4j
public class SpiderStarter extends Thread {

    private String backagePath;

    private String[] beginUrls;

    private PersistenceType persistenceType;

    private boolean useProxy = false;

    private SpiderStarter() {
        persistenceType = PersistenceType.CONSOLE;
    }

    ;

    public static SpiderStarter create() {
        return new SpiderStarter();
    }


    public SpiderStarter backagePath(String backagePath) {
        this.backagePath = backagePath;
        return this;
    }

    public SpiderStarter persistent(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
        return this;
    }

    public SpiderStarter beginUrls(String... beginUrls) {
        this.beginUrls = beginUrls;
        return this;
    }

    public SpiderStarter useProxy(boolean useProxy) {
        ProxyContext.useProxy = useProxy;
        return this;
    }

    public void go() {
        this.setName("爬虫启动器");
        this.start();
    }

    @Override
    public void run() {
        // 初始化
        PageClassInit.initPageClass(this.backagePath);
        PageClassInit.initBeginUrls(this.beginUrls);
        log.info("爬虫程序启动中...");
        AsyncProcessor.executeTask(new Crawler("我的小爬虫"));

    }

}
