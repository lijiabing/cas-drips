package com.drips.cas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018\11\21 0021.
 */
@Component
public class Casrop {

    @Value("${cas.server.host.login_url:http://127.0.0.1:8080/login/cas}")
    private String casServerLoginUrl;
    @Value("${cas.server.ticket.validate.url:http://127.0.0.1:8080/cas/validate}")
    private String ticketValidatorUrl;

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }

    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getTicketValidatorUrl() {
        return ticketValidatorUrl;
    }

    public void setTicketValidatorUrl(String ticketValidatorUrl) {
        this.ticketValidatorUrl = ticketValidatorUrl;
    }
}
