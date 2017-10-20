package com.example.bootiful.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class HelloStaticServletInitializer
    extends SpringBootServletInitializer{

    public static void main(String[] args){

        SpringApplication.run(HelloStaticServletInitializer.class, args);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application){

        return application.sources(HelloStaticServletInitializer.class);
    }
}
