package com.inspiration.xunbao.starter;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
public class SpiderStarter {

    private String backagePath;

    private String[] startUrls;

    private SpiderStarter() {
    };

    public SpiderStarter of(String... startUrls) {
        return new SpiderStarter();
    }


    public SpiderStarter pagePath(String backagePath) {
        this.backagePath = backagePath;
        return this;
    }

    public SpiderStarter start() {
        return this;
    }


}
