package com.inspiration.xunbao.outwebsite.jd.pages;


import com.inspiration.xunbao.crawler.annotations.IntoLink;
import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.annotations.Selector;
import com.inspiration.xunbao.crawler.dao.PageDao;
import com.inspiration.xunbao.crawler.page.HtmlPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * 思路：1、还可以添加一种可以自定义过滤的功能，
 * 这样的话用户自己写过滤规则，我这边根据他的过滤规则来提取数据
 * 比如实现某个接口。
 *
 * 这里目前只支持String类型及String 类型的集合类
 *
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Page(name = "京东总分类页面",urlPattern = "https://www.jd.com/allSort.aspx")
public class JDPage extends HtmlPage implements PageDao {

    /*
        这里获取的是各种分类的名称
     */
    @IntoLink(refer = "京东分类总页面")
    @Selector(value = "body > div:nth-child(5) > div.main-classify > div.list > div.category-items.clearfix  div:nth-child(1) > div.mc > div.items > dl  dt > a")
    private List<String> categories;

    @Selector("#shortcut-2014 > div > ul.fr > li.fore2 > div > a")
    private String myorder;


    @Override
    public void dao(HtmlPage htmlPage) {
        JDPage jdPage = (JDPage) htmlPage;
         

    }
}
