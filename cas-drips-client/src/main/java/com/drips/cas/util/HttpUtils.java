package com.drips.cas.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Map;


/**
 * Created by Administrator on 2018\11\23 0023.
 */
public class HttpUtils {

    private static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    public static boolean doGet(String url) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(request);
            ObjectMapper objectMapper = new ObjectMapper();
            Boolean result = objectMapper.readValue(httpResponse.getEntity().getContent(), Boolean.class);
            return result == null ? false : result.booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }

}
