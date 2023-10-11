package com.by.me.freeparty.Controller;

import com.by.me.freeparty.Services.EmailSenderServices;
import com.by.me.freeparty.Services.PersonServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PersonServices personServices;
    @Autowired
    private EmailSenderServices emailSenderServices;
    public void sendMail(){
        emailSenderServices.sendEmail("qwertyuiop213419@gmail.com",
                "This is sybject", "This is body");
    }

}
