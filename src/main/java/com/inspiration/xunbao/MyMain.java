package com.inspiration.xunbao;

import com.inspiration.xunbao.crawler.starter.SpiderStarter;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
public class MyMain {

    public static void main(String[] args) {
        SpiderStarter.create()
                .backagePath("com.inspiration.xunbao.outwebsite")
                .beginUrls("https://www.jd.com/allSort.aspx")
                .go();
    }
}
