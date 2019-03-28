package com.oreilly.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"com.oreilly.cloud.repository"})
public class ReservationHandlerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ReservationHandlerApplication.class, args);
	}

}

