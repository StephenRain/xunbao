package com.inspiration.xunbao.crawler.http;

import com.google.common.collect.Lists;
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
    private HttpMethod type;

    private String charset;

    private Map<String, String> params;

    private Map<String, String> cookies;

    private Map<String, String> headers;

    public static HttpRequest of(String url) {
        return new HttpRequest().setUrl(url);
    }

    public static List<HttpRequest> ofAll(String... urls) {
        List<HttpRequest> list = new ArrayList<>();
        Arrays.stream(urls).forEach((url) -> {
            list.add(of(url));
        });
        return list;
    }


    {
        charset = "utf-8";
        params = new HashMap<>(8);
        cookies = new HashMap<>(8);
        headers = new HashMap<>(8);
    }
}
