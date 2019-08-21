package com.inspiration.xunbao;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import com.inspiration.xunbao.crawler.util.HttpClientUtil;
import org.junit.Test;
import org.springframework.http.HttpMethod;

/**
 * @author yaotianchi
 * @date 2019/8/21
 */

public class DianpingTest {


    @Test
    public void test1(){

        HttpRequest request = new HttpRequest();
        request.setUrl("http://www.dianping.com/search/map/ajax/json");
        request.setType(HttpMethod.POST);
        request.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        request.addHeader("Cookie","_lxsdk_cuid=16c6aff829cc8-0a70f465347c0a-e353165-100200-16c6aff829dc8; _lxsdk=16c6aff829cc8-0a70f465347c0a-e353165-100200-16c6aff829dc8; _hc.v=7e57e4c9-1894-4457-bba3-61959bf6d5cb.1565163226; Hm_lvt_e6f449471d3527d58c46e24efb4c343e=1565578368; cy=2; cye=beijing; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; s_ViewType=10; _lxsdk_s=16cb303fc78-9c-be2-244%7C%7C37");
        request.addHeader("Referer","http://www.dianping.com/search/map/keyword/2/0_%E5%AE%B6%E6%94%BF");
        request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        request.addPostField("cityId","2");
        request.addPostField("cityEnName","beijing");
        request.addPostField("promoId","0");
        request.addPostField("shopType","");
        request.addPostField("categoryId","");
        request.addPostField("sortMode","");
        request.addPostField("shopSortItem","0");
        request.addPostField("keyword","家政");
        request.addPostField("searchType","2");
        request.addPostField("branchGroupId","0");
        request.addPostField("aroundShopId","0");
        request.addPostField("shippingTypeFilterValue","0");
        request.addPostField("page","1");
//        request.addPostField("glong1","115.71913869873038");
//        request.addPostField("glat1","40.15899366353908");
//        request.addPostField("glong2","116.08100088134756");
//        request.addPostField("glat2","40.12697489347256");
//        request.addPostField("regionId","");

        HttpRes response = HttpClientUtil.getInstace().request(request,false);
        System.out.println("response = " + response);

    }


}
