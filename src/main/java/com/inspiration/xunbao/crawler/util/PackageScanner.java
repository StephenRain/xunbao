package com.inspiration.xunbao.crawler.util;

import com.inspiration.xunbao.crawler.annotations.Page;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 用来扫描包
 *
 * @author yaotianchi
 * @date 2019/8/20
 */
public class PackageScanner {


    /**
     * 扫描指定包下获取带有某个注解的类
     *
     * @param packagePath
     * @param clazz
     * @return
     */
    public static Set<Class<?>> scanPackageWithAnno(String packagePath, Class<? extends Annotation> clazz) {
        Reflections reflections = new Reflections(packagePath);
        return reflections.getTypesAnnotatedWith(clazz) ;
    }


}
