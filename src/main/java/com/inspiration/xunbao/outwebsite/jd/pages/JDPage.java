package com.inspiration.xunbao.outwebsite.jd.pages;


import com.inspiration.xunbao.crawler.annotations.HtmlGet;
import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.annotations.PageField;
import com.inspiration.xunbao.crawler.enums.HtmlGetType;
import com.inspiration.xunbao.crawler.enums.HtmlLabel;
import com.inspiration.xunbao.crawler.page.WebPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Page(urlPattern = "https://www.jd.com/allSort.aspx",
        name = "京东总分类页面")
public class JDPage extends WebPage {


    @Page(name = "一级分类页面")
    @HtmlGet(key = "categories", values = HtmlGetType.TEXT)
    @PageField(cssPath = "body  div.main-classify > div.list > div.category-items.clearfix > div:nth-child(1) > div:nth-child(1) > div.mc > div.items  dt > a")
    private List<JDCategory> categories;

}
