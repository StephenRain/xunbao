package com.inspiration.xunbao.crawler.download;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import com.inspiration.xunbao.crawler.util.HttpClientUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Slf4j
public class HttpClientDownloader extends AbstractDownloader {

    private static HttpClientUtil httpClientUtil;

    private static class HttpClientHolder {
        static HttpClientDownloader downloader = new HttpClientDownloader();
    }

    private HttpClientDownloader() {
        httpClientUtil = HttpClientUtil.getInstace();
    }

    public static HttpClientDownloader getInstance() {
        return HttpClientHolder.downloader;
    }

    @SneakyThrows
    @Override
    public HttpRes download(HttpRequest request,boolean useProxy) {
        return httpClientUtil.request(request,useProxy);
    }


}
