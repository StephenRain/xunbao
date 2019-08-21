package com.inspiration.xunbao.crawler.proxy;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@Slf4j
public class ProxyChecking {

    public static boolean checkProxy(HostPort hostPort) {
        try {
            Jsoup.connect("https://sp0.baidu.com").timeout(3 * 1000).proxy(hostPort.getHost(), hostPort.getPort()).get();
            return true;
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() != null) {
                String cause = e.getCause().getCause().getMessage();
                if (cause == null || (!cause.contains("time") && cause.contains("certification") && cause.contains("SSLHandshakeException"))) {
                    log.error("检测代理ip异常", e);
                }
            }
            return false;
        }
    }



}
