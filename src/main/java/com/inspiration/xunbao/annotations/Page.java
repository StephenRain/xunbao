package com.inspiration.xunbao.annotations;

import java.lang.annotation.*;



/**
 * 用于对网络页面的抽象
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Page {

    /**
     * 需要爬取页面的url
     * @return
     */
    String urlPattern() default "";

    /**
     * 该页面的名称
     * @return
     */
    String name() default "";


}
