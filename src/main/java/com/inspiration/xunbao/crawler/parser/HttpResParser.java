package com.inspiration.xunbao.crawler.parser;

import com.inspiration.xunbao.crawler.http.HttpRes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpResParser {


    public static void parse(HttpRes httpRes) {

        String content = httpRes.getContent();
        Document document = Jsoup.parse(content);



    }


}
