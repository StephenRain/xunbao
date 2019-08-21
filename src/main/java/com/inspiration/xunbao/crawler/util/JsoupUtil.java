package com.inspiration.xunbao.crawler.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author yaotianchi
 * @date 2019/8/20
 */
public class JsoupUtil {


    public static Elements select(Document doc,String selector){
        return doc.select(selector);
    }
}
