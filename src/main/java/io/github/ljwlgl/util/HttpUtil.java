package io.github.ljwlgl.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author zqgan
 * @since 2018/9/1 http请求相关类
 */
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final PoolingHttpClientConnectionManager phccm =
            new PoolingHttpClientConnectionManager();

    static { // 这边来设置并发
        phccm.setDefaultMaxPerRoute(10);
        phccm.setMaxTotal(20);
    }

    private static CloseableHttpClient client =
            HttpClients.custom().setConnectionManager(phccm).build();

    private static final RequestConfig CONFIG =
            RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(10000).build();


    public static String doGet(String uri) throws IOException {
        HttpGet get = new HttpGet(uri);
        get.setConfig(CONFIG);
        try (CloseableHttpResponse httpResponse = client.execute(get)) {
            return generateHttpResponse(httpResponse);
        }
    }

    public static String doGet(String url, Map<String, String> queryParam) throws IOException {
        URI uri = generateURLParams(url, queryParam);

        HttpGet get = new HttpGet(uri);
        get.setConfig(CONFIG);
        try (CloseableHttpResponse httpResponse = client.execute(get)) {
            return generateHttpResponse(httpResponse);
        }
    }

    /**
     * POST请求, json主体，可用于raw请求
     *
     * @param url    api
     * @param params query string
     * @param json   请求体内是json字符串
     */
    public static String doPost(String url, Map<String, String> params, String json)
            throws IOException {
        URI uri = generateURLParams(url, params);
        HttpPost post = new HttpPost(uri);
        post.setConfig(CONFIG);
        HttpEntity entity = new StringEntity(json, DEFAULT_CHARSET);
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse httpResponse = client.execute(post)) {
            return generateHttpResponse(httpResponse);
        }
    }

    /**
     * POST请求, json主体
     *
     * @param url     api
     * @param params  query string
     * @param json    请求体内是json字符串
     * @param headers 请求header
     */
    public static String doPost(
            String url, Map<String, String> params, String json, Map<String, String> headers)
            throws IOException {
        URI uri = generateURLParams(url, params);
        HttpPost post = new HttpPost(uri);
        post.setConfig(CONFIG);
        headers.entrySet().forEach(item -> post.addHeader(item.getKey(), item.getValue()));
        HttpEntity entity = new StringEntity(json, DEFAULT_CHARSET);
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse httpResponse = client.execute(post)) {
            return generateHttpResponse(httpResponse);
        }
    }

    /**
     * POST请求,form主体
     *
     * @param url  api
     * @param form form表单
     */
    public static String doPost(String url, Map<String, String> form) throws IOException {
        URI uri = generateURLParams(url, null);
        List<NameValuePair> urlEncodedForm = generateUrlEncodeForm(form);
        HttpPost post = new HttpPost(uri);
        post.setConfig(CONFIG);
        HttpEntity entity = new UrlEncodedFormEntity(urlEncodedForm, DEFAULT_CHARSET);
        post.setEntity(entity);
        try (CloseableHttpResponse httpResponse = client.execute(post)) {
            return generateHttpResponse(httpResponse);
        }
    }

    public static String doPost(String url) throws IOException {
        URI uri = generateURLParams(url, null);
        HttpPost post = new HttpPost(uri);
        post.setConfig(CONFIG);
        try (CloseableHttpResponse httpResponse = client.execute(post)) {
            return generateHttpResponse(httpResponse);
        }
    }

    private static URI generateURLParams(String url, Map<String, String> params) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params) {
                for (Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            return uri;
        }
        return uri;
    }

    private static List<NameValuePair> generateUrlEncodeForm(Map<String, String> form) {
        if (form == null || form.size() == 0) {
            return null;
        }
        List<NameValuePair> list = new ArrayList<>();
        for (Entry<String, String> entry : form.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private static String generateHttpResponse(CloseableHttpResponse httpResponse)
            throws IOException {
        if (httpResponse == null) {
            return null;
        }
        HttpEntity entity = null;
        entity = httpResponse.getEntity();
        String res = EntityUtils.toString(entity, DEFAULT_CHARSET);
        httpResponse.close();
        return res;
    }
}
