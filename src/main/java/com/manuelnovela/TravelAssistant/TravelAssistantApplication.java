package com.manuelnovela.TravelAssistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class TravelAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAssistantApplication.class, args);
	}

}
