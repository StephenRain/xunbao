package com.inspiration.xunbao;

import com.inspiration.xunbao.starter.SpiderStarter;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
public class MyMain {

    public static void main(String[] args) {
        new SpiderStarter()
                .pagePath("com.inspiration.xunbao.pages")
                .start();
    }
}
