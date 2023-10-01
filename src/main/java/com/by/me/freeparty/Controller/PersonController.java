package com.by.me.freeparty.Controller;


import com.by.me.freeparty.Services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonServices personServices;


    @Autowired
    public PersonController(PersonServices personServices) {
        this.personServices = personServices;

    }
    @GetMapping("/hello")
    public String homePage(){
        return "hello";
    }

}
