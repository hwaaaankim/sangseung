package com.dev.SangSeung02.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.repository.ContactSMSRepository;
import com.dev.SangSeung02.repository.MemberRepository;
import com.dev.SangSeung02.repository.SMSSettingRepository;
import com.dev.SangSeung02.service.MemberService;
import com.dev.SangSeung02.service.SMSService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	SMSService smsService;

	@Autowired
	ContactSMSRepository contactSMSRepository;
	
	@Autowired
	SMSSettingRepository smsSettingRepository;
	
	@RequestMapping("/memberLoginForm")
	public String memberLoginForm() {
		log.info("MEMBER LOGIN FORM");
		return "front/client/memberLoginForm";
	}
	
//	@RequestMapping("/memberLogin")
//	public String memberLoginForm(
//			@RequestParam(value="error", required=false) String error,
//			@RequestParam(value="exception", required = false) String exception,
//			Model model
//			) {
//
//		log.info("MEMBER LOGIN");
//		model.addAttribute("error", error);
//		model.addAttribute("exception", exception);
//		return "front/client/memberLoginForm";
//	}
	
	@RequestMapping("/memberRegisterForm")
	public String memberRegisterForm() {
		
		return "front/client/memberRegisterForm";
	}
	
	@RequestMapping("/memberDupCheck")
	@ResponseBody
	public String memberDupCheck(String username) {
		if(memberRepository.findByUsername(username).isPresent()) {
			
			return "fail";
		}else {
			return "success";
		}
	}
	
	@RequestMapping("/memberPhoneCheck")
	@ResponseBody
	public String memberPhoneCheck(String phone) {
		if(memberRepository.findByPhone(phone).isPresent()) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	@RequestMapping("/memberRegister")
	@ResponseBody
	public String memberRegister(Member member){
		Boolean sign = smsSettingRepository.findByName("MEMBER_INSERT").get().getSign();
		if(memberService.save(member)!=null) {
			String msg = "회원 가입이 완료 되었습니다.";
			StringBuilder sb = new StringBuilder();
			sb.append("alert('"+msg+"');");
			sb.append("location.href='/index'");
			sb.insert(0, "<script>");
			sb.append("</script>");
			if (contactSMSRepository.findAll().size() > 0 && sign) {
				for (int a = 0; a < contactSMSRepository.findAll().size(); a++) {
					smsService.sendMessage(
							"[회원가입발생]" + System.lineSeparator() + "가입자 성함 : " + member.getName()
									+ System.lineSeparator() + "연락처 : " + member.getPhone(),
							contactSMSRepository.findAll().get(a).getSmsnumber());

				}
			}
			return sb.toString();
		}else {
			String msg = "에러가 발생 하였습니다. 다시 시도해 주세요.";
			StringBuilder sb = new StringBuilder();
			sb.append("alert('"+msg+"');");
			sb.append("location.href='/memberRegisterForm'");
			sb.insert(0, "<script>");
			sb.append("</script>");
			
			return sb.toString();
		}
	}
	
	
}















