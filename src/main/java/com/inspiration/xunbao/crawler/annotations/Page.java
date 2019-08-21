package com.inspiration.xunbao.crawler.annotations;

import java.lang.annotation.*;


/**
 * 用于对网络页面的抽象
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Page {

    /**
     * 需要爬取页面的url
     * 先不考虑
     *
     * @return
     */
    String urlPattern();

    /**
     * 该页面的名称
     *
     * @return
     */
    String name() default "";



}
