package com.inspiration.xunbao.crawler.http;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
@Accessors(chain = true)
public class HttpRequest {

    protected String url;

    protected HttpMethod type = HttpMethod.GET;

    protected String charset = "utf-8";

    protected boolean useProxy = false;

    protected Map<String, String> params = new HashMap<>(8);

    protected Map<String, String> cookies = new HashMap<>(8);

    protected Map<String, String> headers = new HashMap<>(16);

    protected Map<String, String> body = new HashMap<>(16);

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
