package com.example.bootiful.war;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequestMapping("/api/v1")
@RestController
public class MessageController{

    @RequestMapping(
        value = "/message",
        produces = {TEXT_PLAIN_VALUE})
    public String message(){

        return "This is a static message I made.";
    }
}