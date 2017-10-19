package com.example.remotelogging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController{

    @Value("${messageForUser}")
    private String messageForUser;

    @RequestMapping("/")
    public String greeting(Model model){

        model.addAttribute("messageForUser", messageForUser);

        return "index";
    }
}