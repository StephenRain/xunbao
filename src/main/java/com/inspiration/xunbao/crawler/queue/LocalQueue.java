package com.inspiration.xunbao.crawler.queue;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 维护一个本地的队列
 *
 * @author yaotianchi
 * @date 2019/8/21
 */
public class LocalQueue<T> {

    private BlockingQueue<T> localQueue;

    public LocalQueue(int capacity) {
        localQueue = new LinkedBlockingQueue<>(capacity);
    }

    public void offer(T t) {
        localQueue.offer(t);
    }

    @SneakyThrows
    public T take() {
        return localQueue.take();
    }

    @SneakyThrows
    public T poll(){
        return localQueue.poll(2, TimeUnit.SECONDS);
    }


}
