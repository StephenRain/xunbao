package com.inspiration.xunbao.crawler.util;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yaotianchi
 * @date 2019/8/20
 */
@Slf4j
public class HttpClientUtil {

    private CloseableHttpClient httpClient;

    private HttpClientContext cookieContext;

    // todo 单例的会出问题呀
    private HttpRequestBase reqObj = null;

    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL = 200;

    /**
     * 最大并发数
     */
    private static final int MAX = 20;

    @SneakyThrows
    private HttpClientUtil() {
        cookieContext = HttpClientContext.create();
        cookieContext.setCookieStore(new BasicCookieStore());
        httpClient = getCloseableHttpClient();
    }

    @SneakyThrows
    private CloseableHttpClient getCloseableHttpClient() {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
        //带连接池的HttpClients初始化方式
        final SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", connectionSocketFactory)
                .build();
        final PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        clientConnectionManager.setMaxTotal(MAX_TOTAL);
        clientConnectionManager.setDefaultMaxPerRoute(MAX);
        return HttpClients.custom().setConnectionManager(clientConnectionManager).build();
    }

    private static class InstaceHolder {
        static HttpClientUtil httpClientUtil = new HttpClientUtil();
    }

    public static HttpClientUtil getInstace() {
        return InstaceHolder.httpClientUtil;
    }

    @SneakyThrows
    public HttpRes request(HttpRequest request, boolean useProxy) {
        HttpRes resp = new HttpRes();
        resp.setRequestedURL(request.getUrl());
        readyForRequest(request, useProxy);
        final CloseableHttpResponse response;
        try {
            response = httpClient.execute(reqObj, cookieContext);
        } catch (Exception e) {
            log.error("Http请求异常：" + e.toString());
            return resp;
        }
        int status = response.getStatusLine().getStatusCode();
        resp.setStatus(status);
        if (status == 302 || status == 301) {
            log.warn("请求URL:[" + request.getUrl() + "]响应码：" + status);
        } else if (status == 200) {
            HttpEntity responseEntity = response.getEntity();
            Header contentTypeHeader = responseEntity.getContentType();
            if (contentTypeHeader != null) {
                resp.setContentType(contentTypeHeader.getValue());
            }
            try {
                String content = EntityUtils.toString(responseEntity);
                resp.setContent(content);
            } finally {
                EntityUtils.consume(responseEntity);
                response.close();
            }
        }
        return resp;
    }

    private void readyForRequest(HttpRequest request, boolean useProxy) {
        HttpMethod method = request.getType();
        switch (method) {
            case POST:
                reqObj = new HttpPost(request.getUrl());
                List<NameValuePair> fields = new ArrayList<>();
                for (Map.Entry<String, String> entry : request.getBody().entrySet()) {
                    NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    fields.add(nvp);
                }
                try {
                    HttpEntity entity = new UrlEncodedFormEntity(fields, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) reqObj).setEntity(entity);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                reqObj = new HttpGet(request.getUrl());
                break;
        }
        //设置代理

        /**
         * 连接建立时间,从连接池获取连接的时间,通信超时
         */
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(30000);
        if (useProxy) {
            // todo
            HttpHost host = new HttpHost("111.231.92.21", 8888);
            builder.setProxy(host);
        }
        reqObj.setConfig(builder.build());
        request.getHeaders().forEach(reqObj::addHeader);
        // 设置Cookies
        request.getCookies().forEach((k, v) -> {
            BasicClientCookie cookie = new BasicClientCookie(k, v);
            cookie.setPath("/");
            cookie.setDomain(reqObj.getURI().getHost());
            cookieContext.getCookieStore().addCookie(cookie);
        });
    }
}
