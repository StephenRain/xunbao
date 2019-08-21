package com.inspiration.xunbao.crawler.queue;

import com.inspiration.xunbao.crawler.mapping.HttpRequestPageMapping;

import java.util.Queue;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
public class RequestPageMappingQueue {

    public static LocalQueue<HttpRequestPageMapping> mappingLocalQueue = new LocalQueue<>(256);

    public static volatile boolean out = false;

}
