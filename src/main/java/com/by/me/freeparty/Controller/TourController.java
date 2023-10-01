package com.by.me.freeparty.Controller;


import com.by.me.freeparty.Model.Tour;
import com.by.me.freeparty.Services.PersonServices;
import com.by.me.freeparty.Services.TourServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    @PostMapping("/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                 Tour tour) throws IOException {
        tourServices.addTour(tour, file1);
        return "redirect:/allTour";
    }
    @GetMapping("/create")
    public String createPage(){
        return "/tour/create";
    }

    @GetMapping("/all")
    public String allTour(@ModelAttribute("tour") Tour tour, Model model){
       model.addAttribute("tours",  tourServices.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("userRole", userRole);
        return "product-page";
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
        model.addAttribute("tour", tourServices.getOne(id));
        model.addAttribute("images", tour.getImages());
        return "tour/info";
    }

}
