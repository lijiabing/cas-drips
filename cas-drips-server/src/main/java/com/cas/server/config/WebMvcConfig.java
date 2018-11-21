package com.cas.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018\11\21 0021.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    private String loginPage="/login/cas";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(loginPage).setViewName("login");
    }
}
