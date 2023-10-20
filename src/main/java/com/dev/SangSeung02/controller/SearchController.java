package com.dev.SangSeung02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

	@RequestMapping("/markupSearch")
	public String markupSearch() {
		
		return "front/markupSearch";
	}
	
	@RequestMapping("/markupDetail")
	public String markupDetail() {
		
		return "front/markupDetail";
	}
}
