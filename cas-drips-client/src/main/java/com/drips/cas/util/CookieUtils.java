package com.drips.cas.util;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\11\23 0023.
 */
public class CookieUtils {

    public static Map<String,Object> strToMap(String cookie){
        if(StringUtils.isEmpty(cookie)){
            return new HashMap<>();
        }
        Map<String,Object> map=new HashMap<>();
        String[] vs=cookie.split("; ");
        for (int i=0;i<vs.length;i++){
            String[] params=vs[i].split("=");
            if(params.length==1){
                continue;
            }
            map.put(params[0],params[1]);
        }
        return map;
    }

    public static String mapToStr(Map<String,Object> map){
        StringBuffer sb=new StringBuffer();
        for (Map.Entry entry:map.entrySet()){
            sb.append(entry.getKey()+"="+ entry.getValue()+"; ");
        }
        return sb.toString();
    }

    public static void put(String newKey,Object newValue,Map<String,Object> map){

        if (map.containsKey(newKey)){
            map.remove(newKey);
        }
        map.put(newKey,newValue);
    }
}
