package com.inspiration.xunbao.crawler.download;

import com.inspiration.xunbao.crawler.http.HttpRequest;
import com.inspiration.xunbao.crawler.http.HttpRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Slf4j
public class HttpClientDownloader extends AbstractDownloader{

    private CloseableHttpClient httpClient;

    private HttpClientContext cookieContext;

    private HttpClientDownloader() {

        cookieContext = HttpClientContext.create();
        cookieContext.setCookieStore(new BasicCookieStore());

        Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
        try {
            //构造一个信任所有ssl证书的httpclient
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
        } catch (Exception ex) {
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", SSLConnectionSocketFactory.getSocketFactory())
                    .build();
        }
        RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
        PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        syncConnectionManager.setMaxTotal(1000);
        syncConnectionManager.setDefaultMaxPerRoute(50);
        httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(clientConfig)
                .setConnectionManager(syncConnectionManager)
                .setRetryHandler(new HttpRequestRetryHandler() {
                    @Override
                    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                        int retryCount = 3;
                        boolean retry = (executionCount <= retryCount);
                        if (log.isDebugEnabled() && retry) {
                            log.debug("retry : " + executionCount);
                        }
                        return retry;
                    }
                }).build();
    }


    @Override
    public HttpRes download(HttpRequest request, int timeout) {

        HttpRes resp = new HttpRes();
        HttpMethod method = request.getType();
        HttpRequestBase reqObj = null;
        switch (method) {
            case GET:
                reqObj = new HttpGet(request.getUrl());
                break;
            case POST:
                reqObj = new HttpPost(request.getUrl());
                break;
            default:
                reqObj = new HttpGet(request.getUrl());
                break;
        }
        request.getHeaders().forEach(reqObj::setHeader);

        // 配置
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000)
                .setConnectTimeout(10000)
                .setRedirectsEnabled(false);
        // 代理
        reqObj.setConfig(builder.build());


        try {
            for(Map.Entry<String, String> entry : request.getCookies().entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
                cookie.setPath("/");
                cookie.setDomain(reqObj.getURI().getHost());
                cookieContext.getCookieStore().addCookie(cookie);
            }
            org.apache.http.HttpResponse response = httpClient.execute(reqObj, cookieContext);
            int status = response.getStatusLine().getStatusCode();
            resp.setStatus(status);
            if(status == 302 || status == 301) {
                String redirectUrl = response.getFirstHeader("Location").getValue();
                //resp.setContent(UrlUtils.relative2Absolute(request.getUrl(), redirectUrl));
            } else if(status == 200) {
                HttpEntity responseEntity = response.getEntity();
//                ByteArrayInputStream raw = toByteInputStream(responseEntity.getContent());
//                resp.setRaw(raw);
                String contentType = null;
                Header contentTypeHeader = responseEntity.getContentType();
                if(contentTypeHeader != null) {
                    contentType = contentTypeHeader.getValue();
                }
                resp.setContentType(contentType);
//                if(!isImage(contentType)) {
//                    String charset = getCharset(request.getCharset(), contentType);
//                    resp.setCharset(charset);
//                    //String content = EntityUtils.toString(responseEntity, charset);
//                    String content = getContent(raw, responseEntity.getContentLength(), charset);
//                    resp.setContent(content);
//                }
            }


        } catch (Exception e) {

        } finally {
            reqObj.releaseConnection();
        }
        return resp;
    }

    @Override
    void intoRequestQueue(HttpRequest httpRequest) {
        this.requestQueue.add(httpRequest);
    }
}
