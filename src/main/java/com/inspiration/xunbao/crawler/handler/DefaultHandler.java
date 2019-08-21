package com.inspiration.xunbao.crawler.handler;

import com.inspiration.xunbao.crawler.annotations.Selector;
import com.inspiration.xunbao.crawler.context.URLContext;
import com.inspiration.xunbao.crawler.http.HttpRes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultHandler implements CrawledDataHandler {


    @SneakyThrows
    public void handle(HttpRes httpRes) {
        String content = httpRes.getContent();
        Document doc = Jsoup.parse(content);
        Class clazz = URLContext.getPageByURL(httpRes.getRequestedURL());
        Object page = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .filter((field -> field.isAnnotationPresent(Selector.class)))
                .forEach((field -> {
                    Selector selectorAnno = field.getAnnotation(Selector.class);
                    String selector = selectorAnno.value();
                    Elements elements = doc.select(selector);
                    if (!elements.isEmpty()) {
                        String genericTypeName = field.getGenericType().getTypeName();
                        field.setAccessible(true);
                        // 目前该genericType 只有基本数据类型和List接口
                        if (!genericTypeName.startsWith("java.util.List")) {
                            // String 类型
                            String val = elements.get(0).text();
                            try {
                                field.set(page, val);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // List<String>类型
                            List<String> list = elements.stream().map(Element::text).collect(Collectors.toList());
                            try {
                                field.set(page, list);
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
        log.debug("反射得到对象：" + page);
    }
}
