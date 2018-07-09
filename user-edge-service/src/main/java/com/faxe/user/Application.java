package com.faxe.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author ZhouXiang
 * @create 2018-07-07 21:34
 **/
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
