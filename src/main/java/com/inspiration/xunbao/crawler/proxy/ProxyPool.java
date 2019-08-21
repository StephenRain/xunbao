package com.inspiration.xunbao.crawler.proxy;

import java.util.List;

public interface ProxyPool {

    List<HostPort> produceProxyHostPorts();

}
