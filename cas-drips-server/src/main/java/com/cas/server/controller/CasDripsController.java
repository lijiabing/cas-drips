package com.cas.server.controller;

import com.cas.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void login(@RequestParam String username,@RequestParam String password,@RequestParam String service, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        userService.checkLogin(username,password,service,request,response);
    }
    @RequestMapping(value = "/cas/validate",method = RequestMethod.GET)
    public Map<String,Object> validateTicket(@RequestParam String ticket){
        Map<String,Object> result=new HashMap<>();
        result.put("validate",userService.ticketValidate(ticket));
        return result;
    }
}
