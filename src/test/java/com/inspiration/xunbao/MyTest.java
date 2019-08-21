package com.inspiration.xunbao;

import com.inspiration.xunbao.crawler.annotations.IntoLink;
import com.inspiration.xunbao.crawler.annotations.Selector;
import com.inspiration.xunbao.crawler.page.HtmlPage;
import com.inspiration.xunbao.outwebsite.jd.pages.JDPage;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author yaotianchi
 * @date 2019/8/20
 */
public class MyTest {


    @Test
    public void test1(){

        HtmlPage htmlPage = new HtmlPage();
        Class<? extends HtmlPage> webPageClass = htmlPage.getClass();
        String name = webPageClass.getName();
        String typeName = webPageClass.getTypeName();
        System.out.println("typeName = " + typeName);
        System.out.println("name = " + name);
    }

    @Test
    public void test2(){
        Field[] fields = JDPage.class.getDeclaredFields();
        // 对普通非链接的选择器的处理
        Arrays.stream(fields)
                .filter((field -> field.isAnnotationPresent(Selector.class) && !field.isAnnotationPresent(IntoLink.class)))
                .forEach((field -> {
                    Selector selectorAnno = field.getAnnotation(Selector.class);
                    String selector = selectorAnno.value();
                    String name = field.getName();
                    Type genericType = field.getGenericType();


                }));
    }

    @Test
    @SneakyThrows
    public void test3(){
        String classstr = "com.inspiration.xunbao.outwebsite.jd.pages.JDPage";
        Class<?> clazz = Class.forName(classstr);
        JDPage o = (JDPage)clazz.newInstance();
        System.out.println("o = " + o);
    }

    @SneakyThrows
    @Test
    public void test4(){
        Connection connect = Jsoup.connect("https://www.jd.com/allSort.aspx");
        Document doc = connect.get();
        Elements elements = doc.select("body > div:nth-child(5) > div.main-classify > div.list > div.category-items.clearfix  div:nth-child(1) > div.mc > div.items > dl  dt > a");
        elements.forEach((e)-> System.out.println("提取到的数据：" + e.text()));
    }

}
