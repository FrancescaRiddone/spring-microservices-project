package com.oreilly.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ZuulProxyController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@RequestMapping("/serviceInfo")
	public String serviceInfo() {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("flight-catalog", false);
		
		return instance.getHomePageUrl();
	}
}
