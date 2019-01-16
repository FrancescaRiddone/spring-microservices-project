package com.oreilly.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController {
	
	@RequestMapping("/reserve")
	public String reserve() {
		return "reserved......";
	}

}
