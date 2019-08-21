package com.inspiration.xunbao.crawler.http;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

import java.util.*;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
@Accessors(chain = true)
public class HttpRequest {

    private String url;

    /**
     * 请求方式
     */
    private HttpMethod type = HttpMethod.GET;

    private String charset = "utf-8";

    private Map<String, String> params = new HashMap<>(8);

    private Map<String, String> cookies = new HashMap<>(8);

    private Map<String, String> headers = new HashMap<>(8);

    private Map<String, String> body = new HashMap<>(16);

    public HttpRequest() {
        headers.put("Content-Type", "application/json; charset=utf-8");
    }

    public static HttpRequest of(String url) {
        return new HttpRequest().setUrl(url);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addPostField(String key, String value) {
        body.put(key, value);
    }


}
