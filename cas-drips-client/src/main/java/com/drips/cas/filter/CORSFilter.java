package com.drips.cas.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018\11\28 0028.
 */
@Order(-2147483648)
@WebFilter(
        filterName = "CORSFilter",
        urlPatterns = {"/*"}
)
public class CORSFilter implements Filter {
    @Value("${authox.access-control-allow-origin:*}")
    private String allowOrigin;

    public CORSFilter() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)res;
        response.setHeader("Access-Control-Allow-Origin", this.allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
