package com.dev.SangSeung02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServiceController {

	@RequestMapping("/patent")
	public String patent() {
		
		return "front/patent";
	}
	
	@RequestMapping("/design")
	public String design() {
		
		return "front/design";
	}
	
	@RequestMapping("/markup")
	public String markup() {
		
		return "front/markup";
	}
}
