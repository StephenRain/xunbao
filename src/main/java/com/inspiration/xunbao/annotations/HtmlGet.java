package com.inspiration.xunbao.annotations;

import com.inspiration.xunbao.enums.HtmlGetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 要从当前页面的某个字段中获取什么数据
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlGet {

    /*
        给需要获取的数据起个字段名用来映射
     */
    String key() default "";

    HtmlGetType values();

}
