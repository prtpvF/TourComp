package com.by.me.freeparty;

import com.by.me.freeparty.Services.EmailSenderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

public class FreepartyApplication {
	@Autowired
	private EmailSenderServices emailSenderServices;

	public static void main(String[] args) {
		SpringApplication.run(FreepartyApplication.class, args);
	}



}
