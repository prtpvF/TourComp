package com.by.me.freeparty.Controller;


import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Security.PersonDetails;
import com.by.me.freeparty.Services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String homePage(int id, Model model){

        return "hello";
    }

    @GetMapping("/get/all/tours/{id}")
    public String getAllTours(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personServices.getPerson(id));
        model.addAttribute("tours", personServices.getAllTours(id));
        model.addAttribute("userRole", getRole());
        return "person/home";
    }
    public static String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        return userRole;
    }
    @GetMapping("/home")
    public String getHomePage(Model model){

        model.addAttribute("userRole", getRole());
        model.addAttribute("person", personServices.getOne());
        model.addAttribute("tours", personServices.getOne().getTours());
        return "person/home";
    }


}
