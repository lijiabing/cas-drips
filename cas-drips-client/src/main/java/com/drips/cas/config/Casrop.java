package com.drips.cas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018\11\21 0021.
 */
@Component
public class Casrop {

    @Value("${cas.server.host.url}")
    private String casServerUrl;
    @Value("${cas.server.host.login_url:/login/cas}")
    private String casServerLoginUrl;
    @Value("${app.server.host.url}")
    private String appServerUrl;
    @Value("${app.login.url:/login}")
    private String appLoginUrl;
    @Value("${cas.server.ticket.validate.url:${cas.server.host.url}}")
    private String ticketValidatorUrl;


    public String getCasServerUrl() {
        return casServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        this.casServerUrl = casServerUrl;
    }

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }

    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getAppServerUrl() {
        return appServerUrl;
    }

    public void setAppServerUrl(String appServerUrl) {
        this.appServerUrl = appServerUrl;
    }

    public String getAppLoginUrl() {
        return appLoginUrl;
    }

    public void setAppLoginUrl(String appLoginUrl) {
        this.appLoginUrl = appLoginUrl;
    }

    public String getTicketValidatorUrl() {
        return ticketValidatorUrl;
    }

    public void setTicketValidatorUrl(String ticketValidatorUrl) {
        this.ticketValidatorUrl = ticketValidatorUrl;
    }
}
