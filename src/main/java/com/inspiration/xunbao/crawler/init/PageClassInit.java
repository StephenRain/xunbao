package com.inspiration.xunbao.crawler.init;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.context.PageBeanContext;
import com.inspiration.xunbao.crawler.context.URLContext;
import com.inspiration.xunbao.crawler.handler.CrawledDataHandler;
import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.mapping.HttpRequestPageMapping;
import com.inspiration.xunbao.crawler.queue.RequestPageMappingQueue;
import com.inspiration.xunbao.crawler.util.PackageScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Set;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@Slf4j
public class PageClassInit {

    public static void initPageClass(String backagePath) {
        // 扫描包
        Set<Class<?>> classSet = PackageScanner.scanPackageWithAnno(backagePath, Page.class);
        classSet.forEach((pageClass) -> {
            log.info("扫描到Page类：" + pageClass.getName());
            Page pageAnno = pageClass.getAnnotation(Page.class);
            String urlPattern = pageAnno.urlPattern();
            URLContext.putURLPattern(urlPattern, pageClass);

            // 判断其接口
            Class<?>[] interfaces = pageClass.getInterfaces();
            boolean anyMatch = Arrays.stream(interfaces)
                    .anyMatch((e) -> e.getName().equals(CrawledDataHandler.class.getName()));
            if (anyMatch) {
                log.debug(pageClass.getName() + "实现了接口：" + CrawledDataHandler.class.getName());
                PageBeanContext.dataHandlerPages.add(pageClass);
            }
        });
        log.info("初始化Page类完毕");
    }

    /**
     * 初始化种子URL
     *
     * @param beginUrls
     */
    public static void initBeginUrls(String... beginUrls) {
        // 匹配URL找到对应的Page类
        for (String url : beginUrls) {
            Class<?> pageClazz = URLContext.matchURL(url);
            if (pageClazz == null) {
                log.warn("未匹配到对应的Page：" + url);
            } else {
                // 将实际URL和对应的Page存起来
                URLContext.putRealURL(url, pageClazz);
                RequestPageMappingQueue.mappingLocalQueue
                        .offer(new HttpRequestPageMapping(HttpRequest.of(url), pageClazz));
            }
        }
    }
}
