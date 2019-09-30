package com.inspiration.xunbao.outwebsite.dianping.pages;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.page.JSONPage;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */
@Page(name="大众点评",urlPattern = "http://www.dianping.com/search/map/ajax/json")
public class DianpingPage extends JSONPage {

    //
    private String shopeName;

    private String stars;





}
