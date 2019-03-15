package com.oreilly.cloud.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ReservationHandlerConfig {
	
	@Bean(name="restTemplateExecution")
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   return builder.build();
	}
	
	@Bean(name="restTemplateTest")
	public RestTemplate restTemplate1(RestTemplateBuilder builder) {
	   return builder.build();
	}

}
