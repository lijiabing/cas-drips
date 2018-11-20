package com.drips.cas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018\11\20 0020.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
