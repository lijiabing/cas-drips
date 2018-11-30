package com.cas.server.filter;

import com.cas.server.config.RedisConfig;
import com.cas.server.util.CookieUtils;
import com.cas.server.util.TicketUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class LoginFilter extends OncePerRequestFilter{

    private RedisConfig redisConfig;

    private CookieLocaleResolver cookieLocaleResolver;
    public LoginFilter(RedisConfig redisConfig){
        this.redisConfig=redisConfig;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String,Object> cookieMap=CookieUtils.strToMap(request.getHeader("Cookie"));
        String tgcSession=(String) cookieMap.get("CASTGC");
        if(request.getRequestURI().equalsIgnoreCase("/logout")||(request.getRequestURI().equalsIgnoreCase("/login/cas")&&tgcSession==null)){
            filterChain.doFilter(request,response);
            return;
        }

        String ticket=request.getParameter("ticket");
        //判断是否有全局会话，有则重定向到原有的系统地址 并携带Set-Cookie（TGC）和ticket
        if(tgcSession!=null&&isExist(tgcSession)&&StringUtils.isEmpty(ticket)){
            String service=request.getParameter("service");
            String lt=TicketUtil.createTicket(service);
            Jedis jedis=redisConfig.getJedis();
            jedis.setex(lt,300,lt);
            response.sendRedirect(URLDecoder.decode(service,"UTF-8")+"?ticket="+ lt);
            return;
        }
        if(!cookieMap.containsKey("cas-session")){
            Cookie cookie=new Cookie("cas-session", request.getSession(true).getId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        filterChain.doFilter(request,response);
    }

    public void setCookieLocaleResolver(CookieLocaleResolver cookieLocaleResolver) {
        this.cookieLocaleResolver = cookieLocaleResolver;
    }

    public   boolean isExist(String key){
        Jedis jedis=redisConfig.getJedis();
        if(jedis.exists(key)){
            return true;
        }
        return false;
    }

}
