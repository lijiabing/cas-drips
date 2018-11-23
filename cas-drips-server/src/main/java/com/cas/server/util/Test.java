package com.cas.server.util;

import java.util.UUID;

/**
 * Created by Administrator on 2018\11\23 0023.
 */
public class Test {

    public static void main(String[] args){
        System.out.println(TicketUtil.createTicket("http://127.0.0.1:8080/login"));
    }
}
