package com.drips.cas.filter;

import com.drips.cas.config.Casrop;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class CasClientFilter extends OncePerRequestFilter{

    private Casrop casrop;

    public CasClientFilter(Casrop casrop){
        this.casrop=casrop;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session != null) {
            filterChain.doFilter(request, response);
        } else {
            String serviceUrl = casrop.getCasServerLoginUrl();
            String ticket = null;
            if(StringUtils.isEmpty(ticket) ) {//没有ST
                this.logger.debug("no ticket and no assertion found");
                this.logger.debug("request.getRequestURL()="+request.getRequestURL());
                response.sendRedirect(serviceUrl+"?service="+request.getRequestURL());
            } else {//有ST
                filterChain.doFilter(request,response);
            }
        }


    }
}
