package com.inspiration.xunbao.crawler.annotations;

import java.lang.annotation.*;

/**
 * 表明进入该链接接着进行爬取数据
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PageField
@Documented
public @interface IntoLink {

    /**
     * 指向哪个Page类，值是对应Page类中@Page的name属性
     * @return
     */
    String refer();


}
