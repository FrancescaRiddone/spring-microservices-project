package com.oreilly.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude=HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
@EnableEurekaClient
public class FlightCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightCatalogApplication.class, args);
	}

}

