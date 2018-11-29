package com.drips.cas.filter;

import com.drips.cas.config.Casrop;
import com.drips.cas.util.CookieUtils;
import com.drips.cas.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    public CasClientFilter(Casrop casrop) {
        this.casrop = casrop;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null && (boolean) httpSession.getAttribute("hasLogin")) {//查看本地session是否缓存有用户信息    目前判断是否有已经授权登录的标识isLogin
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
                    response.sendRedirect(request.getRequestURL().toString());
                }
                return;
            }
        }

    }
}
