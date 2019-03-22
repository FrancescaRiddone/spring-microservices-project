package com.oreilly.cloud.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration()
@Profile("local")
public class ReservationHandlerConfig {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		System.out.println("restTemplate construction");
	
	   return builder.build();
	}

}
