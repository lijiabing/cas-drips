package com.cas.server.config;

import com.cas.server.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
@Configuration
@ServletComponentScan({"com.cas.server.filter"})
@Import(RedisConfig.class)
public class CasServerConfig {


    @Bean
    @Order(1)
    public FilterRegistrationBean filterRegistrationBean(@Qualifier("localResolver") CookieLocaleResolver cookieLocaleResolver,RedisConfig redisConfig){
        LoginFilter loginFilter=new LoginFilter(redisConfig);
        loginFilter.setCookieLocaleResolver(cookieLocaleResolver);
        return new FilterRegistrationBean(loginFilter,new ServletRegistrationBean[0]);
    }

    @Bean
    @Qualifier("localResolver")
    public CookieLocaleResolver cookieLocaleResolver(){
        CookieLocaleResolver cookieLocaleResolver=new CookieLocaleResolver();
        return cookieLocaleResolver;
    }



}
