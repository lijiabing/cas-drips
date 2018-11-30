package com.cas.server.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * Created by Administrator on 2018\11\23 0023.
 */
public class HttpUtils {

    private static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    public static void doGet(String url) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.setHeader("logout","y");
        Boolean result=null;
        try {
            HttpResponse httpResponse = httpClient.execute(request);
            int code=httpResponse.getStatusLine().getStatusCode();
            if (code!=200){
                System.out.println("code="+code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
