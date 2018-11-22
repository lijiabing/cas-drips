package com.cas.server.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class LoginFilter extends OncePerRequestFilter{

    private CookieLocaleResolver cookieLocaleResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie  cookie= WebUtils.getCookie(request,cookieLocaleResolver.getCookieName());

        //判断是否有全局会话，有则重定向到原有的系统地址 并携带令牌

        //没有全局会话则返回登录界面
        if(cookie!=null){

        }

        filterChain.doFilter(request,response);
    }

    public void setCookieLocaleResolver(CookieLocaleResolver cookieLocaleResolver) {
        this.cookieLocaleResolver = cookieLocaleResolver;
    }

}
