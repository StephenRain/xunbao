package com.inspiration.xunbao.crawler.http;

import lombok.Data;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
public class HttpRes {

    private String content;

    private String contentType;

    private String charset;

    private int status;

}
