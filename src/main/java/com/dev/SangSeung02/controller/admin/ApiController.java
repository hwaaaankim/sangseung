package com.dev.SangSeung02.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.service.MemberService;

@Controller
@RequestMapping("/api/v1")
public class ApiController {

	
	@Autowired
	MemberService memberService;
	
	@PostMapping("/adminJoin")
	@ResponseBody
	public String adminJoin(Member member) {
		memberService.insertAdmin(member);
		return "success";
	}
	
	@PostMapping("/roleInsert")
	@ResponseBody
	public String roleInsert() {
		
		return "success";
	}
}
