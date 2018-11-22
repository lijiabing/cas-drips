//package com.drips.cas.config;
//
//import org.jasig.cas.client.session.SingleSignOutFilter;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
//import org.jasig.cas.client.validation.TicketValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.cas.ServiceProperties;
//import org.springframework.security.cas.authentication.CasAuthenticationProvider;
//import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//
//import javax.servlet.http.HttpSessionEvent;
//
///**
// * Created by Administrator on 2018\11\21 0021.
// */
//@Configuration
//public class CasSecurityConfig {
//
//    @Autowired
//    private Casrop casrop;
//
//    @Bean
//    public ServiceProperties serviceProperties(){
//        ServiceProperties serviceProperties=new ServiceProperties();
//        serviceProperties.setService(casrop.getAppServerUrl()+"/main");
//        serviceProperties.setSendRenew(false);
//        return serviceProperties;
//    }
//
//    @Bean
//    @Primary
//    public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties serviceProperties){
//        CasAuthenticationEntryPoint authenticationEntryPoint=new CasAuthenticationEntryPoint();
//        authenticationEntryPoint.setLoginUrl(casrop.getCasServerUrl()+casrop.getCasServerLoginUrl());
//        authenticationEntryPoint.setServiceProperties(serviceProperties);
//        return authenticationEntryPoint;
//    }
//
//    @Bean
//    public TicketValidator ticketValidator(){
//        return new Cas30ServiceTicketValidator(casrop.getCasServerUrl());
//    }
//
//    //cas认证
//    @Bean
//    public CasAuthenticationProvider casAuthenticationProvider() {
//
//        CasAuthenticationProvider provider = new CasAuthenticationProvider();
//        provider.setServiceProperties(serviceProperties());
//        provider.setTicketValidator(ticketValidator());
//        //固定响应用户，在生产环境中需要额外设置用户映射
//        provider.setUserDetailsService(
//                s -> new User("auth-user", "123", true, true, true, true,
//                        AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
//        provider.setKey("CAS_PROVIDER_LOCALHOST_8123");
//        return provider;
//    }
//
//
//    @Bean
//    public SecurityContextLogoutHandler securityContextLogoutHandler() {
//        return new SecurityContextLogoutHandler();
//    }
//
//    @Bean
//    public LogoutFilter logoutFilter() {
//        //退出后转发路径
//        LogoutFilter logoutFilter = new LogoutFilter(
//                casrop.getCasServerUrl() + "/logout",
//                securityContextLogoutHandler());
//        //cas退出
//        logoutFilter.setFilterProcessesUrl("/logout/cas");
//        return logoutFilter;
//    }
//
//    @Bean
//    public SingleSignOutFilter singleSignOutFilter() {
//        //单点退出
//        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix(casrop.getCasServerUrl());
//        singleSignOutFilter.setIgnoreInitConfiguration(true);
//        return singleSignOutFilter;
//    }
//
//    //设置退出监听
//    @EventListener
//    public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(
//            HttpSessionEvent event) {
//        return new SingleSignOutHttpSessionListener();
//    }
//
//}
