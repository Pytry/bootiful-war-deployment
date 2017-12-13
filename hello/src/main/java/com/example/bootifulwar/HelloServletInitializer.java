package com.example.bootifulwar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class HelloServletInitializer extends SpringBootServletInitializer{

    @Value("${messageForUser}")
    private String message;

    @Value("${whatDoesTheFoxSay:'No body knows.'}")
    private String whatDoesTheFoxSay;

    public static void main(String[] args){

        SpringApplication.run(HelloServletInitializer.class, args);
    }

    @Scheduled(fixedRate = 2000)
    public void sayHelloTo(){

        log.info("Hello! " + message);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application){

        log.info(
            "\n*********************\n" +
                "What does the fox say?\n" +
                whatDoesTheFoxSay +
                "\n*********************\n");
        return application.sources(HelloServletInitializer.class);
    }
}
