package com.cas.server.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018\11\20 0020.
 * 第一件事情：验证用户信息是否正确，并将登录成功的用户信息保存到Redis数据库中。
 * 第二件事情：负责判断用户令牌是否过期，若没有则刷新令牌存活时间。
 * 第三件事情：负责从Redis数据库中删除用户信息
 */
@Service
public class UserService {
}
