package com.example.reservation_system;

import com.example.reservation_system.service.EmailSenderService;
import com.example.reservation_system.storage.StorageProperties;
import com.example.reservation_system.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
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

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
