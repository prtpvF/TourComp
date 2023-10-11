package com.by.me.freeparty.Controller;


import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Services.PersonServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonServices personServices;
    @Autowired
    public AuthController(PersonServices personServices) {
        this.personServices = personServices;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage( Model model,Person person, CsrfToken csrfToken) {
        model.addAttribute("_csrf", csrfToken);
        model.addAttribute("userRole", getRole());
        return "/auth/login";
    }
    public static String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        return userRole;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person")  Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/auth/registration";
        personServices.register(person);
        return "redirect:/auth/login";
    }
    @GetMapping("/hello")
    public String homePage(Model model){
        model.addAttribute("userRole", getRole());
        return "hello";
    }
}
