package com.dev.SangSeung02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.repository.MemberRepository;
import com.dev.SangSeung02.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/my")
public class MyController {

	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/myMarkupService")
	public String myMarkupService() {
		log.info("MY MARKUP SERVICE");
		return "front/client/myMarkupService";
	}
	
	@RequestMapping("/myPatentService")
	public String myPatentService() {
		
		return "front/client/myPatentService";
	}
	
	@RequestMapping("/myDesignService")
	public String myDesignService() {
		
		return "front/client/myDesignService";
	}
	
	@RequestMapping("/myPayment")
	public String myPayment() {
		
		return "front/client/myPayment";
	}
	
	@RequestMapping("/myInquiry")
	public String myInquiry() {
		
		return "front/client/myInquiry";
	}
	
	@RequestMapping("/myInfo")
	public String myInfo(
			@AuthenticationPrincipal Member member,
			Model model
			) {
		model.addAttribute("member", memberRepository.findByUsername(member.getUsername()).get());
		return "front/client/myInfo";
	}
	
	@GetMapping("/myInfoChange")
	public String myInfoChangePage(
			@AuthenticationPrincipal Member member,
			Model model
			) {
		
		model.addAttribute("member", memberRepository.findByUsername(member.getUsername()).get());
		return "front/client/myInfoChange";
	}
	
	@PostMapping("/myInfoChange")
	public String myInfoChange(
			Member member,
			Model model,
			String key
			) {
		model.addAttribute("member", memberService.infoChange(member, key));
		return "front/client/myInfoChange";
	}
	
	@PostMapping("/memberPassCheck")
	@ResponseBody
	public String memberPassCheck(
			String username,
			String password
			) {
		return memberService.passCheck(username, password);
	}
}





















