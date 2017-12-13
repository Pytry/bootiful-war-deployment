package com.example.bootifulwar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@Slf4j
@SpringBootApplication
public class JeeServletInitializer extends SpringBootServletInitializer{

    @Value("${messageForUser}")
    private String message;

    @Value("${whatDoesTheFoxSay:'No body knows.'}")
    private String whatDoesTheFoxSay;

    public static void main(String[] args){

        SpringApplication.run(JeeServletInitializer.class, args);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application){

        log.info(
            "\n*********************\n" +
                "What does the fox say?\n" +
                whatDoesTheFoxSay +
                "\n*********************\n");
        return application.sources(JeeServletInitializer.class);
    }
}
