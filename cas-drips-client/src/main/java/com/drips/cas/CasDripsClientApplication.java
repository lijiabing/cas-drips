package com.drips.cas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Administrator on 2018\11\20 0020.
 */
@SpringBootApplication
public class CasDripsClientApplication {
    public static void main(String[] args){
        SpringApplication.run(CasDripsClientApplication.class,args);
    }
}
