package com.oreilly.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HotelCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelCatalogApplication.class, args);
	}

}

