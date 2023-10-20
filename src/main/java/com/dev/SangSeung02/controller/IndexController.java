package com.dev.SangSeung02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.SangSeung02.repository.ContactRepository;
import com.dev.SangSeung02.repository.FileRepository;
import com.dev.SangSeung02.service.MemberService;
import com.dev.SangSeung02.service.SMSService;

@Controller
public class IndexController {

	@Autowired
	SMSService smsService;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping({"/index", "/"})
	public String index() {
		
		return "front/index";
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		return memberService.test();
	}
	
	@RequestMapping("/reference")
	public String reference() {
		
		return "front/reference";
	}
	
//	@RequestMapping("/insertTest")
//	public String insertTest(
//			@AuthenticationPrincipal Member member
//			) {
//		List<SangSeungService> list = contactRepository.findAllByMember(member); 
//		System.out.println(list.size());
//		for(SangSeungService s : list) {
//			System.out.println(fileRepository.findAllByService(s).size());
//		}	
//		return "front/index";
//	}
//	
//	@RequestMapping("/messageTest")
//	public String messageTest() {
//		smsService.sendMessage("테스트", "010-3894-3849,010-4866-4894");
//		return "front/index";
//	}
	
	@RequestMapping("/expired")
	@ResponseBody
	public String expired() {
		String msg = "세션이 만료되었습니다. 다시 로그인을 진행 해 주세요.";
		StringBuilder sb = new StringBuilder();
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/memberLoginForm'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		
		return sb.toString();
	}
}
