package com.by.me.freeparty.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage(){
        return "hello";
    }
    @GetMapping("/about")
    public String aboutUsPage(){
        return "about";
    }
    @GetMapping("/offer")
    public String offerPage(){
        return "offer";
    }
}
