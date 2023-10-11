package com.by.me.freeparty.Controller;

import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Security.PersonDetails;
import com.by.me.freeparty.Services.PersonServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PersonServices personServices;

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

    @RequestMapping("/header")
    public String header(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        model.addAttribute("userRole", userRole);
        model.addAttribute("person", person);
        return "header";
    }
}
