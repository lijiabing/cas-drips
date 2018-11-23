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

    private static String CONTENT_TYPE= "application/x-www-form-urlencoded; charset=UTF-8";

    public static boolean doGet(String url)
            throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse  httpResponse=httpClient.execute(request);
        ObjectMapper objectMapper=new ObjectMapper();
        Map map=objectMapper.readValue(httpResponse.getEntity().getContent(),Map.class);
        Boolean result=(Boolean)map.get("validate");
        System.out.println(result.booleanValue());
        return result==null?false:result.booleanValue();
    }

}
