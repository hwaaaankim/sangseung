package com.dev.SangSeung02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.repository.MemberRepository;

@Controller
@RequestMapping("/my")
public class DesignController {

	@Autowired
	MemberRepository memberRepository;
	
	@RequestMapping("/contactDesign")
	public String contactDesign(
			@AuthenticationPrincipal Member member,
			Model model
			) {
		model.addAttribute("member", memberRepository.findByUsername(member.getUsername()).get());
		return "front/contactDesign";
	}
}
