package com.example.reservation_system;

import com.example.reservation_system.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ReservationSystemApplication {

	@Autowired
	private EmailSenderService senderService;

	public static void main(String[] args) {

		SpringApplication.run(ReservationSystemApplication.class, args);
	}

/*	@EventListener(ApplicationReadyEvent.class)
	public void sendMail(){
		senderService.SendEmail("adisiwek@gmail.com", "Subject", "body");
	}*/
}
