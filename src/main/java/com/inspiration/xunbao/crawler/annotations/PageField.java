package com.inspiration.xunbao.crawler.annotations;
import com.inspiration.xunbao.crawler.enums.HtmlLabel;
import com.inspiration.xunbao.crawler.enums.ValueType;

import java.lang.annotation.*;


@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageField {

    String name() default "";

    String key() default "";

    ValueType value() default ValueType.TEXT;
}
