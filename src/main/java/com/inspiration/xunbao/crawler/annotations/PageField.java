package com.inspiration.xunbao.crawler.annotations;
import com.inspiration.xunbao.crawler.enums.HtmlLabel;

import java.lang.annotation.*;


@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageField {

    String cssPath() default "";

    HtmlLabel label() default HtmlLabel.TEXT;

    /**
     * 如果是a标签的话是否进入该页面抓取
     * @return
     */
    boolean intoOrNot() default false;

}
