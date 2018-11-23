package com.cas.server.filter;

import com.cas.server.config.RedisConfig;
import com.cas.server.util.CookieUtils;
import com.cas.server.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class LoginFilter extends OncePerRequestFilter{

    @Autowired
    private RedisConfig redisConfig;

    private CookieLocaleResolver cookieLocaleResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Map<String,Object> cookieMap=CookieUtils.strToMap(request.getHeader("Cookie"));
        String tgcSession=(String) cookieMap.get("session");
        //判断是否有全局会话，有则重定向到原有的系统地址 并携带Set-Cookie（TGC）和ticket
        if(tgcSession!=null&&isExist(tgcSession)){
            response.addHeader("Set-Cookie",CookieUtils.mapToStr(cookieMap));
            String service=request.getParameter("service");
            response.sendRedirect(service+"?ticket="+ TicketUtil.createTicket(service));
        }else{
            //没有全局会话则返回登录界面
            filterChain.doFilter(request,response);
        }
    }

    public void setCookieLocaleResolver(CookieLocaleResolver cookieLocaleResolver) {
        this.cookieLocaleResolver = cookieLocaleResolver;
    }

    public boolean isExist(String key){
        Jedis jedis=redisConfig.getJedis();
        if(jedis.exists(key)){
            return true;
        }
        return false;
    }

}
