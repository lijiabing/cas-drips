package com.drips.cas.handler;

import com.drips.cas.cache.SessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class LogoutHandler {


    @Autowired
    private SessionCache sessionCache;

    public boolean isLogoutRequest(HttpServletRequest request){
        Object o=request.getParameter("logout");
        return o==null?false:true;
    }

    public void removeSession(HttpSession httpSession){
        if (httpSession==null){
            return;
        }
        sessionCache.removeSession(httpSession.getId());
    }

    public void recodeSession(String session,String ticket){
        sessionCache.recodeSession(ticket,session);
    }

    public boolean hasTicket(HttpSession httpSession){
        if(httpSession==null){
            return false;
        }
        return sessionCache.hasKey(httpSession.getId());
    }

}
