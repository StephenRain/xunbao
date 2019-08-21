package com.inspiration.xunbao.outwebsite.taobao.pages;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.annotations.PageField;
import com.inspiration.xunbao.crawler.annotations.Selector;
import com.inspiration.xunbao.crawler.page.HtmlPage;
import lombok.Data;

import java.util.List;

/**
 * @author yaotianchi
 * @date 2019/8/20
 */
@Data
@Page(name = "淘宝网",urlPattern = "xxx")
public class TaoBaoPage {

    @Selector("body > div.screen-outer.clearfix > div.main > div.main-inner.clearfix > div.tbh-service.J_Module > div > ul > li > a")
    private List<FirstLevel> firstLevel;

    @PageField
    @Selector("body > div.tbh-nav.J_Module.tb-pass.tb-bg > div > ul.nav-hd > li:nth-child(2) > a")
    private HtmlPage htmlPage;






}
