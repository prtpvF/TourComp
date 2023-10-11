package com.by.me.freeparty.Controller;


import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Model.Tour;
import com.by.me.freeparty.Security.PersonDetails;
import com.by.me.freeparty.Services.EmailSenderServices;
import com.by.me.freeparty.Services.PersonServices;
import com.by.me.freeparty.Services.TourServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourController {
private final TourServices tourServices;
private final PersonServices personServices;
@Autowired
private EmailSenderServices emailSenderServices;

    @PostMapping("/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                 Tour tour) throws IOException {
        tourServices.addTour(tour, file1);
        return "redirect:/tour/all";
    }
    @GetMapping("/create")
    public String createPage(){
        return "/tour/create";
    }

    @GetMapping("/all")
    public String allTour(@ModelAttribute("tour") Tour tour, Model model){
       model.addAttribute("tours",  tourServices.findAll());
       model.addAttribute("images", tour.getImages());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();

        model.addAttribute("userRole", userRole);
        // model.addAttribute("person", personServices.getOne());
        return "product-page";
    }


    @PostMapping("/add/person/{id2}/to/tour/{id}")
    public String addPersonToTour(@PathVariable("id") int id, @PathVariable("id2") int id2, Model model){
        model.addAttribute("person", personServices.getPerson(id2));
        model.addAttribute("tour", tourServices.getOne(id));
        tourServices.addTourToUser(id);
        return "redirect:/tour/all";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTour(@PathVariable("id") int id, Model model){

        tourServices.delete(id);

        return "redirect:/tour/all";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        Tour tour = tourServices.getOne(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userrole = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("userrole", userrole);
        Person person = personServices.getAuth();
        model.addAttribute("tour", tourServices.getOne(id));
        model.addAttribute("person", person);
        return "tour/info";
    }
    @GetMapping("/get/all/guests/on/tour/{id}")
    public String getAllGuest(@PathVariable("id") int id, Model model){
        model.addAttribute("persons", tourServices.getAllGuests(id));
        model.addAttribute("tour", tourServices.getOne(id));
        return "tour/allGuests";
    }

    @DeleteMapping("/delete/person/{id2}/to/tour/{id}")
    public String deletePersonToTour(@PathVariable("id") int id, @PathVariable("id2") int id2, Model model){
        model.addAttribute("person", personServices.getPerson(id2));
        model.addAttribute("tour", tourServices.getOne(id));
        tourServices.deleteTourFromUser(id, id2);
        return "redirect:/tour/all";
    }

}
