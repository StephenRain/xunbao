package com.inspiration.xunbao.crawler.proxy;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
public class DefaultFreeProxyPool implements ProxyPool {

    private DefaultFreeProxyPool() {

    }

    static class InstanceHolder {
        static DefaultFreeProxyPool defaultFreeProxyPool = new DefaultFreeProxyPool();
    }

    public static DefaultFreeProxyPool getInstance(){
        return InstanceHolder.defaultFreeProxyPool;
    }

    private List<HostPort> proxyList = new LinkedList<>();


    public void addProxy(HostPort hostPort) {
        proxyList.add(hostPort);
    }

    /**
     * 默认的是从免费IP代理网站爬取
     *
     * @return
     */
    @Override
    public List<HostPort> produceProxyHostPorts() {
        return proxyList;
    }


}
