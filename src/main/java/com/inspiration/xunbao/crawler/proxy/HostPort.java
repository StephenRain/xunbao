package com.inspiration.xunbao.crawler.proxy;

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
public class HostPort {
    private String host;
    private int port;
}

