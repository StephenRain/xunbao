package com.inspiration.xunbao.outwebsite.jd.pages;

import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.annotations.Selector;

@Page(name = "京东分类总页面", urlPattern = "https://channel.jd.com/1319-1523.html")
public class JDCategory {

    @Selector("#nf_ftplsigle2_4 > div > div > div.nf_ftplsigle2_tab_bd.J_tab_content > ul:nth-child(1) > li:nth-child(1) > p.ftpl_mod_goods_title > a")
    private String productName;

    @Selector("#nf_ftplsigle2_4 > div > div > div.nf_ftplsigle2_tab_bd.J_tab_content > ul:nth-child(1) > li:nth-child(1) > div.ftpl_mod_goods_price > span > span")
    private String price;




}