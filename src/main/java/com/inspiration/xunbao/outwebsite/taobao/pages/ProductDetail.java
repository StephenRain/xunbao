package com.inspiration.xunbao.outwebsite.taobao.pages;

import com.inspiration.xunbao.crawler.annotations.Selector;
import com.inspiration.xunbao.crawler.annotations.Page;
import com.inspiration.xunbao.crawler.annotations.PageField;
import com.inspiration.xunbao.crawler.enums.ValueType;

/**
 * @author yaotianchi
 * @date 2019/8/20
 */
@Page(name = "商品详情页",urlPattern = "1111")
public class ProductDetail {


    @PageField(key = "title",value = ValueType.TEXT)
    @Selector("")
    private String title;

    @PageField(key = "price",value=ValueType.TEXT)
    @Selector("")
    private String price;

    @PageField(key = "comment",value=ValueType.TEXT)
    @Selector("")
    private int comment;

    @PageField(key="imgUrl",value = ValueType.URL)
    @Selector("")
    private String imgUrl;


}
