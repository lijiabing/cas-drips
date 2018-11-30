package com.cas.server.controller;

import com.cas.server.dao.TicketGrantToken;
import com.cas.server.service.UserService;
import com.cas.server.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\11\20 0020.
 * 负责跳转登录页面跳转  负责用户的登录，退出，获取令牌的操作
 */
@RestController
@RequestMapping
public class CasDripsController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestParam String username, @RequestParam String password, @RequestParam String service, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        userService.checkLogin(username,password,service,request,response);
    }

    @RequestMapping(value = "/cas/validate",method = RequestMethod.GET)
    public boolean validateTicket(@RequestParam String ticket, HttpServletRequest request,HttpServletResponse response) throws IOException {
        return userService.ticketValidate(ticket,request,response);
    }

    @GetMapping(value = "/logout")
    public void logout( HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.logout(request,response);
    }
}
