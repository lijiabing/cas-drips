package com.cas.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by Administrator on 2018\11\20 0020.
 */
@SpringBootApplication
public class CasDripsServerApplication {
    public static void main(String[] args){
        SpringApplication.run(CasDripsServerApplication.class,args);
    }
}
