package com.cas.server.util;

import org.springframework.util.Base64Utils;

/**
 * Created by Administrator on 2018\11\23 0023.
 */
public class TicketUtil {

    private static final int HASH=17;


    public static String createTicket(String service){
        StringBuffer sb=new StringBuffer();
        sb.append("ST-9-");
        sb.append(Base64Utils.encodeToString(String.valueOf(hashCode(service)).getBytes()));
        sb.append("-cas");
       return  sb.toString();
    }

    private static int hashCode(String service) {
        int hash = HASH * 31 + (service == null?0:service.hashCode());
        return hash;
    }

}
