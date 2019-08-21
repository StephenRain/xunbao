package com.inspiration.xunbao.crawler.context;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用来管理请求网页的URL地址
 *
 * @author yaotianchi
 * @date 2019/8/20
 */
public class URLContext {

    /**
     * key:实际的URL,
     * Value:对应的Page类的Class对象
     */
    private static Map<String, Class<?>> realURLPageMap = new ConcurrentHashMap<>(128);

    private static Map<String, Class<?>> urlPatternPageMap = new ConcurrentHashMap<>(128);

    private static BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>(512);

    public static void intit(String[] urls) {
        urlQueue.addAll(Arrays.asList(urls));
    }

    public static void putURLPattern(String urlPattern, Class<?> pageClass) {
        urlPatternPageMap.put(urlPattern, pageClass);
    }

    @SneakyThrows
    public static Class getPageByURL(String realURL) {
        return realURLPageMap.get(realURL);
    }

    public static void putRealURL(String realURL,Class clazz){
        realURLPageMap.put(realURL,clazz);
    }



    public static Class<?> matchURL(String realURL) {
        Optional<String> first = urlPatternPageMap.keySet().stream()
                .filter((urlPattern) -> {
                    // 这里简单考虑，只比较是否相等，以后再改
                    return urlPattern.equals(realURL);
                }).findFirst();
        String s = null;
        if (first.isPresent()) {
            s = first.get();
            return urlPatternPageMap.get(s);
        }
        return null;
    }

}
