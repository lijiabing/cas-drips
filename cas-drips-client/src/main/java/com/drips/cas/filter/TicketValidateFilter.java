//package com.drips.cas.filter;
//
//import com.drips.cas.config.Casrop;
//import com.drips.cas.util.HttpUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by Administrator on 2018\11\22 0022.
// */
//public class TicketValidateFilter extends OncePerRequestFilter{
//    @Autowired
//    private Casrop casrop;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//
//        String ticket = request.getParameter("ticket");
//        this.logger.debug("验证ticket的有效性！");
//        try{
//            if(HttpUtils.doGet(casrop.getTicketValidatorUrl()+"?ticket="+ticket)==null){
//
//            }
//        }catch (Exception e){
//
//        }
//
//        filterChain.doFilter(request,response);
//    }
//}
