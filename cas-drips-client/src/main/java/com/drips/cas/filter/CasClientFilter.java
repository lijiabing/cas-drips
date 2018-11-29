package com.drips.cas.filter;

import com.drips.cas.config.Casrop;
import com.drips.cas.handler.LogoutHandler;
import com.drips.cas.util.CookieUtils;
import com.drips.cas.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class CasClientFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(CasClientFilter.class);
    private Casrop casrop;
    @Autowired
    private LogoutHandler logoutHandler;

    public CasClientFilter(Casrop casrop) {
        this.casrop = casrop;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(logoutHandler.isLogoutRequest(request)){//判断是否是登出请求  该判断必须在所有的filter之前
            //如果是则清除 缓存的session以及ticket
            logoutHandler.removeSession(request.getSession(false));
        }


        HttpSession httpSession = request.getSession(false);
        if (httpSession != null && (boolean) httpSession.getAttribute("hasLogin")&&logoutHandler.hasTicket(httpSession)) {//查看本地session是否缓存有用户信息  其次查看本地是否缓存有ticket  两个都有的话说明登录过    目前判断是否有已经授权登录的标识isLogin以及是否有缓存ticket
            filterChain.doFilter(request, response);
            return;
        } else {
            String serviceUrl = casrop.getCasServerLoginUrl();
            String ticket=request.getParameter("ticket");
            if (StringUtils.isEmpty(ticket)) {//没有ST
                this.logger.debug("没有ticket  跳转到认证中心认证");
                response.sendRedirect(serviceUrl + "?service=" + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
                return;
            } else {//有ST，验证ticket
                if (HttpUtils.doGet(casrop.getTicketValidatorUrl() + "?ticket=" + ticket)) {
                    HttpSession httpSession1 = request.getSession();
                    httpSession1.setAttribute("isLogin", true);//相当于把用户信息放入本地session 目前只是做一个已经授权登录的标识
                    //保存ticket和sessionid,用于登出
                    logoutHandler.recodeSession(httpSession1.getId(),ticket);//目前未设置session过期时间
                    response.sendRedirect(request.getRequestURL().toString());
                }
                return;
            }
        }

    }
}
