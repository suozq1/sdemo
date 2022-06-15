package com.suo.sdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
@EnableCaching
@EnableAsync
public class SDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SDemoApplication.class, args);
	}
}
