package com.cas.server.service;

import com.cas.server.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018\11\20 0020.
 * 第一件事情：验证用户信息是否正确，并将登录成功的用户信息保存到Redis数据库中。
 * 第二件事情：负责判断用户令牌是否过期，若没有则刷新令牌存活时间。
 * 第三件事情：负责从Redis数据库中删除用户信息
 */
@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object uname=request.getParameter("username");
        Map<String,String[]> map=request.getParameterMap();
        String username=map.get("amp;username")[0];
        String password=map.get("amp;password")[0];
        if(username.equals("123")&&password.equals("1234")){
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);

            System.out.println(request.getParameter("service"));
            //发放票据
           response.sendRedirect(request.getParameter("service"));
        }


    }

    public void checkTick(){

    }
}
