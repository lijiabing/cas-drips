package com.drips.cas.filter;

import com.drips.cas.config.Casrop;
import com.drips.cas.util.CookieUtils;
import com.drips.cas.util.HttpUtils;
import org.jasig.cas.client.validation.TicketValidator;
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
import java.util.Map;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class CasClientFilter extends OncePerRequestFilter{

    private Logger logger= LoggerFactory.getLogger(CasClientFilter.class);

    private Casrop casrop;

    public CasClientFilter(Casrop casrop){
        this.casrop=casrop;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session != null) {//有session说明用户已经登录过了
            filterChain.doFilter(request, response);
        } else {
            String serviceUrl = casrop.getCasServerLoginUrl();
            String ticket = request.getParameter("ticket");
            if(StringUtils.isEmpty(ticket) ) {//没有ST
                String cookie=request.getHeader("Cookie");
                System.out.println("Cookie="+cookie);
                System.out.println("Set-Cookie="+response.getHeader("Set-Cookie"));
                this.logger.debug("没有ticket  跳转到认证中心认证");
                response.sendRedirect(serviceUrl+"?service="+request.getRequestURL());
            } else {//有ST，验证ticket
                this.logger.debug("有ticket="+ticket);
                try {
                    if (HttpUtils.doGet(casrop.getTicketValidatorUrl() + "?ticket=" + ticket)){
                        System.out.println("验证ticket成功 返回正常网页");
                    }else{
                        System.out.println("验证ticket失败 转向认证界面");
                        response.sendRedirect(serviceUrl+"?service="+request.getRequestURL());
                    }
                    filterChain.doFilter(request,response);
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect(serviceUrl+"?service="+request.getRequestURL());
                }
            }
        }


    }
}
