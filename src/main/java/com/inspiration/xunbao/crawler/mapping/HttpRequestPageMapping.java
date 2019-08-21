package com.inspiration.xunbao.crawler.mapping;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestPageMapping {

    private HttpRequest request;

    private Class<?> pageClazz;



}
