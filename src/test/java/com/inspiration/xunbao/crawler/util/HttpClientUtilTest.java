package com.inspiration.xunbao.crawler.util;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import org.junit.Test;
import org.springframework.http.HttpMethod;

public class HttpClientUtilTest {

    @Test
    public void request() {
        HttpRequest request = new HttpRequest();
        request.setUrl("http://sss.bnu.edu.cn/");
        request.setType(HttpMethod.GET);
        HttpRes resp = HttpClientUtil.getInstace().request(request,false);
        System.out.println("resp = " + resp);
    }
}