package com.example.registery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryServerApplication.class, args);
	}

}
