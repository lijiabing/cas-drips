package com.drips.cas.config;

import com.drips.cas.filter.CasClientFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
@Configuration
@ServletComponentScan({"com.drips.cas.filter"})
public class CasClientConfig {

    @Bean
    @Qualifier("filterRegistrationBean")
    @Order(1)
    public FilterRegistrationBean filterRegistrationBean(Casrop casrop){
        CasClientFilter casClientFilter=new CasClientFilter(casrop);
        return new FilterRegistrationBean(casClientFilter,new ServletRegistrationBean[0]);
    }
}
