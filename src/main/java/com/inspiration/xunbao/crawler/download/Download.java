package com.inspiration.xunbao.crawler.download;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;

public interface Download {


    HttpRes download(HttpRequest request, int timeout);

}
