package com.inspiration.xunbao.crawler;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import com.inspiration.xunbao.crawler.util.HttpClientUtil;
import org.junit.Test;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
public class ProxyTest {

    @Test
    public void test1(){
        HttpRequest request = new HttpRequest();
        request.setUrl("http://127.0.0.1:8080/hello/hi");
        HttpRes res = HttpClientUtil.getInstace().request(request, true);
    }

}
