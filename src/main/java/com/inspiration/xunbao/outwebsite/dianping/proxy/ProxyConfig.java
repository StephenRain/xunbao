package com.inspiration.xunbao.outwebsite.dianping.proxy;

import com.google.common.collect.Lists;
import com.inspiration.xunbao.crawler.proxy.HostPort;
import com.inspiration.xunbao.crawler.proxy.ProxyPool;

import java.util.List;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@com.inspiration.xunbao.crawler.annotations.ProxyPool
public class ProxyConfig implements ProxyPool {

    /**
     * 115.171.111.127	9000
     * 61.157.136.105	808
     * 163.204.247.159	9999
     *
     * @return
     */
    @Override
    public List<HostPort> produceProxyHostPorts() {
        return Lists.newArrayList(
                new HostPort("61.157.136.105", 808),
                new HostPort("163.204.247.159", 9999));
    }
}
