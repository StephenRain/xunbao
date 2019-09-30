package com.inspiration.xunbao.crawler.http;

import lombok.Data;

import java.util.*;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
public class HttpResponse {

    private String content;

    private String contentType;

    private String charset;

    private int status;

    /**
     * 表示请求那个URL响应的该结果
     */
    private String serverUrl;

    private Map<String,String> headers = new HashMap<>();

    private List<Map<String,String>> cookies = new ArrayList<>();


    /**
     * 通过当前对象中构建一个cookie出来，如果没有cookie则使用默认传入的值
     *
     * @param defaultCookie
     * @return
     */
    public String genCookie(String defaultCookie) {
        StringBuilder sb = new StringBuilder(defaultCookie);
        List<Map<String, String>> cookies = this.getCookies();
        if(cookies.isEmpty()){
            return defaultCookie;
        }
        cookies.stream().forEach((e)->{
            Set<String> keySet = e.keySet();
            for (String key:keySet){
                sb.append(key).append("=").append(e.get(key)).append(";");
            }
        });
        return sb.toString();
    }

}
