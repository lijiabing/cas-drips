package com.cas.server.controller;

import com.cas.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void login(HttpServletRequest request,HttpServletResponse response){
        userService.checkLogin(request,response);
    }
}
