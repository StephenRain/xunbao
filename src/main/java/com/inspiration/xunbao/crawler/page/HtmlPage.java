package com.inspiration.xunbao.crawler.page;

import com.inspiration.xunbao.crawler.annotations.PageType;
import lombok.Data;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
public class HtmlPage implements Page{

    @Override
    public PageType getType() {
        return PageType.HTML;
    }

}
