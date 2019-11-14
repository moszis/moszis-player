package com.moszis.moszisplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MoszisPlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoszisPlayerApplication.class, args);
	}

	@Scheduled(fixedRate = 5000)
	public void gameLoop(){
		System.out.println("running...");
	}
}
