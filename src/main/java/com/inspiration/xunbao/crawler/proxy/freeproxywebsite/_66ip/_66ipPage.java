package com.inspiration.xunbao.crawler.proxy.freeproxywebsite._66ip;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.handler.CrawledDataHandler;
import com.inspiration.xunbao.crawler.http.HttpRes;
import com.inspiration.xunbao.crawler.page.HtmlPage;
import com.inspiration.xunbao.crawler.proxy.DefaultFreeProxyPool;
import com.inspiration.xunbao.crawler.proxy.HostPort;
import com.inspiration.xunbao.crawler.proxy.ProxyChecking;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Page(name = "66ip网", urlPattern = "http://www.66ip.cn/areaindex_1/1.html")
public class _66ipPage extends HtmlPage implements CrawledDataHandler {


    @Override
    public void handle(HttpRes httpRes) {
        String content = httpRes.getContent();
        if (StringUtils.isEmpty(content)) {
            log.warn("66ip网爬取结果为空");
        }
        Document document = Jsoup.parse(content);
        Elements tbodys = document.select("table");

        // 获取tbody
        if (tbodys != null && !tbodys.isEmpty()) {
            Element tbody = tbodys.get(2);
            Elements trs = tbody.select("tr");
            // 头部去掉(第0行tr)
            deal66ip(trs);
        }

    }

    private static void deal66ip(Elements trs) {

        for (int r = 1; r < trs.size(); r++) {
            Element tr = trs.get(r);
            // 获取每个tr元素里面的所有的td元素
            Elements tds = tr.select("td");
            String type = "3";
            if (tds != null && tds.size() >= 5) {
                // 解析匿名度
                String anony = tds.get(3).text();
                anony = analyzeAnonymity(anony);
                // 解析协议

                // 解析运营商
                String operator = "";
                // ip
                String ip = tds.get(0).text().trim();
                // port
                String port = tds.get(1).text().trim();
                log.debug("66ip网正在解析IP\t" + ip + "\t" + port);
                if (ProxyChecking.checkProxy(new HostPort("ip", Integer.parseInt(port)))) {
                    log.info("66ip网获取到可用代理IP\t" + ip + "\t" + port);
                    DefaultFreeProxyPool.getInstance()
                            .addProxy(new HostPort(ip, Integer.parseInt(port)));
                }
            }
        }

    }

    private static String analyzeAnonymity(String anony) {
        String newAnony;
        if ("高匿".equals(anony)) {
            newAnony = "1";
        } else if ("匿名".equals(anony)) {
            newAnony = "2";
        } else {
            newAnony = "3";
        }
        return newAnony;
    }
}
