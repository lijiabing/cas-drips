package com.cas.server.service;

import com.cas.server.config.RedisConfig;
import com.cas.server.dao.TicketGrantToken;
import com.cas.server.dao.User;
import com.cas.server.util.TicketUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018\11\20 0020.
 * 第一件事情：验证用户信息是否正确，并将登录成功的用户信息保存到Redis数据库中。
 * 第二件事情：负责判断用户令牌是否过期，若没有则刷新令牌存活时间。
 * 第三件事情：负责从Redis数据库中删除用户信息
 */
@Service
public class UserService {

    @Autowired
    private RedisConfig redisConfig;

    public void checkLogin(String username ,String password,String service,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(checkUser(username,password)){
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setId(UUID.randomUUID().toString());
            String ticket=createTicket(request,response,service,user);
            //发放票据,并重定向值web应用
            System.out.println(service);
            response.sendRedirect(service+"?ticket="+ticket);
        }
    }

    /**
     * 认证用户
     * @return
     */
    public boolean checkUser(String username,String password){
        if(username.equals("123")&&password.equals("1234")){

            return true;
        }else{
            return false;
        }
    }

    /**
     * 签发ST(即ticket)   1.生成ST 并缓存  2.生成TGT(包含cookie的值以及session(目前我们只保存session对应的用户信息))  3. 以TGC(也就是sessionID) 为key 缓存TGT
     */
    public String createTicket(HttpServletRequest request,HttpServletResponse response,String service,User user){
        HttpSession httpSession=request.getSession();
        //1.根据Service生成ST(即Ticket)
        String ticket= TicketUtil.createTicket(service);
        //缓存ST 并设置过期时间 10s
        cacheST(ticket);
        //2.生成登录票据（实际上就是session）TGT (集成了用户信息)
        String TGT=request.getCookies()[0].getValue();
        TicketGrantToken ticketGrantToken=new TicketGrantToken();
        ticketGrantToken.setId(TGT);
        ticketGrantToken.setUser(user);
        //3.生成一个服务票据TGC(ST与CAS会话标识)  TGC就是标识这个Session存到Cookie中的SessionID  并以TGC(也就是sessionID) 为key 缓存TGT
        String TGC=httpSession.getId();
        cacheTGT(TGC,ticketGrantToken);
        System.out.println("ticket="+ticket);
        return ticket;
    }

    /**
     * 校验ST
     */
    public boolean ticketValidate(String ticket){
        System.out.println("validate ticket="+ticket);
        Jedis jedis=redisConfig.getJedis();
        if(jedis.exists(ticket)){
            return true;
        }
        return false;
    }

    /**
     * 缓存ST
     * @param ticket
     */
    public  synchronized void cacheST(String ticket){
        Jedis jedis=redisConfig.getJedis();
        if(jedis.exists(ticket)){
           jedis.del(ticket);
        }
        jedis.setex(ticket,60000,ticket);//利用redis实现ST的缓存 设置ST 的过期时间
    }

    /**
     * 缓存TGT
     */

    public synchronized void cacheTGT(String key,TicketGrantToken ticketGrantToken){

        Jedis jedis=redisConfig.getJedis();
        if(jedis.exists(key)){
            jedis.del(key);
        }
        jedis.set(key.getBytes(), SerializationUtils.serialize(ticketGrantToken));//利用redis实现ST的缓存
    }
}
