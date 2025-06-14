package com.desafio.luizalabs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpotifyMusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyMusicApiApplication.class, args);
	}

}
