package io.ktrade.ipfs.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Random;


public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     *
     * @param url            url地址
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        //post请求返回结果
        //DefaultHttpClient httpClient = new DefaultHttpClient();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);

            }
            //httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,1000*60*15);
            //httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,1000*60*15);

            /*HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 60000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 600000);*/



/*
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            method.setEntity(new UrlEncodedFormEntity(nvps));*/

            CloseableHttpResponse result = httpClient.execute(method);

            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     *
     * @param url    路径
     * @return
     */

    private static CookieStore cookieStore;

    public static JSONObject httpGet(String url) {
        return httpGet(url, 0, 0);
    }

    public static JSONObject httpGet(String url, int waitMin, int waitMax) {
        if (waitMax > 0) {
            try {
                int number = new Random().nextInt(waitMax - waitMin) + waitMin;
                //System.out.println("Random number = " + number);
                Thread.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            //DefaultHttpClient client = new DefaultHttpClient();
            //CloseableHttpClient client = HttpClients.createDefault();
            HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            //发送get请求
            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36"); // 设置请求头消息User-Agent
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); // 设置请求头消息User-Agent
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br"); // 设置请求头消息User-Agent
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9"); // 设置请求头消息User-Agent
            httpGet.setHeader("cache-control", "max-age=0"); // 设置请求头消息User-Agent
            httpGet.setHeader("upgrade-insecure-requests", "1"); // 设置请求头消息User-Agent
            httpGet.setHeader("Connection", "keep-alive");
            //httpGet.setHeader("cookie", "_ga=GA1.2.352291941.1505216094; _gid=GA1.2.417361201.1515119656; _globalGA=GA1.2.352291941.1505216094; _globalGA_gid=GA1.2.417361201.1515119656; Hm_lvt_02d6fe6e6e4acce5c8d372b5b6e4ef18=1515119655,1515222863,1515222934,1515225361; Hm_lpvt_02d6fe6e6e4acce5c8d372b5b6e4ef18=1515246442");

            HttpResponse response = client.execute(httpGet);
            cookieStore = setCookieStore(response);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == 200) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                // String cookie=response.getFirstHeader("Set-Cookie").getValue();
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    public static CookieStore setCookieStore(HttpResponse httpResponse) {

        CookieStore cookieStore = new BasicCookieStore();
        if (httpResponse.getFirstHeader("Set-Cookie") == null) {
            return null;
        }
        //Set-Cookie由服务器发送，它包含在响应请求的头部中。它用于在客户端创建一个Cookie
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
        System.out.println("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("https://chain.api.btc.com");
        cookie.setPath("/");

        cookieStore.addCookie(cookie);

        return cookieStore;
    }

}