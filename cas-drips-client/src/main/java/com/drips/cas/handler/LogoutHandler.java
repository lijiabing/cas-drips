package com.drips.cas.handler;

import org.springframework.util.ConcurrentReferenceHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class LogoutHandler {

    private final ConcurrentReferenceHashMap<String,String> concurrentReferenceHashMap=new ConcurrentReferenceHashMap<>();
    private final ConcurrentReferenceHashMap<String,String> reversconcurrentReferenceHashMap=new ConcurrentReferenceHashMap<>();

    public boolean isLogoutRequest(HttpServletRequest request){
        Object o=request.getHeader("logout");
        return o!=null&&String.valueOf(o).equalsIgnoreCase("y")?true:false;
    }

    public void removeSession(HttpServletRequest request){
        String value=reversconcurrentReferenceHashMap.remove(request.getParameter("ticket"));
        concurrentReferenceHashMap.remove(value);
        HttpSession httpSession=request.getSession(false);
        if (httpSession==null){
            return;
        }
        httpSession.invalidate();
    }

    public void recodeSession(String session,String ticket){
        concurrentReferenceHashMap.put(session,ticket);
        reversconcurrentReferenceHashMap.put(ticket,session);
        System.out.println();
    }

    public boolean hasTicket(HttpSession httpSession){
        if(httpSession==null){
            return false;
        }
        return concurrentReferenceHashMap.containsKey(httpSession.getId());
    }

}
