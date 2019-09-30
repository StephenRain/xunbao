package com.inspiration.xunbao.crawler.util;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.*;

@Slf4j
public enum HttpUtil {
    /**
     * 单例
     */
    IST;

    private static final String KEYSTORE = "mWMxstzKX3";

    private static final String certPath = "src/test/resources/certificate/cert.p12";


    /**
     * 最大连接数
     */
    public static final int MAX_TOTAL = 200;

    RequestConfig.Builder builder = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000);

    /**
     * 最大并发数
     */
    public static final int MAX = 20;

    /**
     * 这里单例,内部初始化了连接池
     */
    private CloseableHttpClient httpClient;

    HttpUtil() {
        httpClient = getCloseableHttpClient(certPath);
    }

    private static int retryCount = 0;

    @SneakyThrows
    private static CloseableHttpClient getCloseableHttpClient(String p12Path) {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream inputStream = new FileInputStream(new File(p12Path))) {
            keyStore.load(inputStream, KEYSTORE.toCharArray());
        }
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
        return HttpClients.custom()
                .setConnectionManager(clientConnectionManager)
                .setRetryHandler((exception, executionCount, context) -> {
                    boolean retry = (executionCount < retryCount);
                    log.info("Http请求重试次数：" + executionCount + ",Exception:" + exception.getMessage());
                    return retry;
                }).build();
    }


    /**
     * 发起Http请求
     *
     * @param request 自定义Http请求
     * @return 自定义的返回值
     */
    public HttpResponse request(HttpRequest request) {
        Objects.requireNonNull(request);
        log.debug("正在执行请求:" + request);
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setServerUrl(request.getUrl());
        HttpRequestBase baseRequest = buildBaseRequest(request);
        CloseableHttpResponse response = request(baseRequest);
        if (response == null) {
            log.error("HTTP请求异常！URL:" + request.getUrl());
            throw new RuntimeException("HTTP请求异常！URL:" + request.getUrl());
        }
        // 转换
        Header[] headers = response.getAllHeaders();
        Map<String, String> headerMap = httpResponse.getHeaders();
        for (int i = 0; i < headers.length; i++) {
            if ("Set-Cookie".equals(headers[i].getName())) {
                String value = headers[i].getValue();
                String[] kvs = value.split(";");
                for (int j = 0; j < kvs.length; j++) {
                    String[] kv = kvs[j].split("=");
                    HashMap<String, String> map = new HashMap<>();
                    map.put(kv[0], kv[1]);
                    httpResponse.getCookies().add(map);
                }
            } else {
                headerMap.put(headers[i].getName(), headers[i].getValue());
            }
        }
        HttpEntity responseEntity = response.getEntity();
        Header contentTypeHeader = responseEntity.getContentType();
        if (contentTypeHeader != null) {
            httpResponse.setContentType(contentTypeHeader.getValue());
        }
        try {
            String content = null;
            try {
                content = EntityUtils.toString(responseEntity);
            } catch (Exception e) {
                log.error("responseEntity异常：" + e.getMessage());
            }
            httpResponse.setContent(content);
        } finally {
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    /**
     * 使用apache Http Client 发起请求
     *
     * @param baseRequest
     * @return
     */
    protected CloseableHttpResponse request(HttpRequestBase baseRequest) {
        RequestConfig requestConfig = baseRequest.getConfig();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(baseRequest);

        } catch (HttpHostConnectException e) {
            log.warn("Http请求异常：" + e.getMessage());
            // 可能是代理IP不可用
            if (requestConfig.getProxy() != null) {
                //ProxyPool.getInstance().remove(requestConfig.getProxy());
                log.warn("代理IP不可用：Host:[" + e.getHost() + "],Error Msg:" + e.getMessage());
                // 切换IP重试
                log.info("当前使用代理:" + requestConfig.getProxy() + ",即将切换代理IP重试");
                baseRequest.setConfig(buildRequestConfig(true));
                return request(baseRequest);
            }
            return response;
        } catch (IOException e) {
            log.error("请求异常：" + e.getMessage());
            if (requestConfig.getProxy() != null) {
                //ProxyPool.getInstance().remove(requestConfig.getProxy());
                // 切换IP重试
                log.info("当前使用代理:" + requestConfig.getProxy() + ",即将切换代理IP重试");
                baseRequest.setConfig(buildRequestConfig(true));
                return request(baseRequest);
            }
            return response;
        }
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            // 其他的状态码
            log.warn("响应状态码：[" + status + "],URI:" + baseRequest.getURI());
            if (requestConfig.getProxy() != null) {
                if (status == 302 || status == 301) {
                    //ProxyPool.getInstance().remove(requestConfig.getProxy());
                    String redirectUrl = response.getFirstHeader("Location").getValue();
                    log.info("重定向地址：" + redirectUrl);
                }

                // 切换IP重试
                log.info("当前使用代理:" + requestConfig.getProxy() + ",即将切换代理IP重试");
                baseRequest.setConfig(buildRequestConfig(true));
                return request(baseRequest);
            }
        }
        return response;
    }


    private HttpRequestBase buildBaseRequest(HttpRequest request) {
        HttpMethod method = request.getType();
        HttpRequestBase baseRequest = null;
        switch (method) {
            case POST:
                baseRequest = new HttpPost(request.getUrl());
                List<NameValuePair> fields = new ArrayList<>();
                for (Map.Entry<String, String> entry : request.getBody().entrySet()) {
                    NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    fields.add(nvp);
                }
                try {
                    HttpEntity entity = new UrlEncodedFormEntity(fields, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) baseRequest).setEntity(entity);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                baseRequest = new HttpGet(request.getUrl());
                break;
        }
        RequestConfig requestConfig = buildRequestConfig(request.isUseProxy());
        baseRequest.setConfig(requestConfig);
        request.getHeaders().forEach(baseRequest::addHeader);
        return baseRequest;
    }

    private RequestConfig buildRequestConfig(boolean useProxy) {
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000);
        // 代理
//        ProxyIp proxyIp = null;
//        if (useProxy) {
//            proxyIp = ProxyPool.getInstance().getByStrategy(ProxyStrategy.ROUND);
//            log.info("取出代理IP:" + proxyIp);
//            if (proxyIp != null) {
//                HttpHost host = new HttpHost(proxyIp.getIp(), proxyIp.getPort());
//                builder.setProxy(host);
//            }
//        }
        return builder.build();
    }
}
