package com.csc301.songmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SongMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SongMicroserviceApplication.class, args);
		System.out.println("Song Microservice is running on port 3001");
	}
}
