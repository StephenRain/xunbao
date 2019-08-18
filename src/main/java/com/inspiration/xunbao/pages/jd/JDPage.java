package com.inspiration.xunbao.pages.jd;


import com.inspiration.xunbao.annotations.HtmlGet;
import com.inspiration.xunbao.annotations.HtmlGetType;
import com.inspiration.xunbao.annotations.Page;
import com.inspiration.xunbao.annotations.PageField;
import com.inspiration.xunbao.enums.HtmlLabel;
import com.inspiration.xunbao.handlers.PageHandler;
import lombok.Data;

import java.util.List;

@Data
@Page(urlPattern ="https://www.jd.com/allSort.aspx",name = "京东总分类页面")
public class JDPage implements PageHandler {


    @HtmlGet(key = "categories",values = HtmlGetType.TEXT)
    @PageField(cssPath = "body  div.main-classify > div.list > div.category-items.clearfix > div:nth-child(1) > div:nth-child(1) > div.mc > div.items  dt > a",
            label = HtmlLabel.A,
            intoOrNot = true)
    private List<Category> categories;



    @Override
    public void handle() {

    }
}
