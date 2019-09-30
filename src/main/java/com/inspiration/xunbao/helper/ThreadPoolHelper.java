package com.inspiration.xunbao.helper;

import java.util.concurrent.*;

/**
 * @author yaotianchi
 * @date 2019/9/30
 */
public class ThreadPoolHelper {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,10,1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(20));

    public static ThreadPoolExecutor getThreadPool(){
        return threadPool;
    }

}
