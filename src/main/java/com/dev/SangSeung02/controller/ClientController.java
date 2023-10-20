package com.dev.SangSeung02.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.SangSeung02.model.service.TempService;
import com.dev.SangSeung02.repository.ContactSMSRepository;
import com.dev.SangSeung02.repository.MemberRepository;
import com.dev.SangSeung02.repository.SMSSettingRepository;
import com.dev.SangSeung02.repository.TempServiceRepository;
import com.dev.SangSeung02.service.SMSService;
import com.dev.SangSeung02.utils.StringUtils;

@Controller
public class ClientController {

	
	@Autowired
	TempServiceRepository tempServiceRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ContactSMSRepository contactSMSRepository;
	
	@Autowired
	SMSService smsService;
	
	@Autowired @Qualifier("saveBean")
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SMSSettingRepository smsSettingRepository;
	
	@RequestMapping("/notice")
	public String notice() {
		
		return "front/notice";
	}
	
	@RequestMapping("/faq")
	public String faq() {
		
		return "front/faq";
	}
	
	@RequestMapping("/privacy")
	public String privacy() {
		
		return "front/privacy";
	}
	
	@RequestMapping("/manual")
	public String manual() {
		
		return "front/manual";
	}
	
	@RequestMapping("/findID")
	public String findId() {
		
		return "front/client/memberIDFind";
	}
	
	@RequestMapping("/findPW")
	public String findPW() {
		
		return "front/client/memberPASSFind";
	}
	
	@RequestMapping("/speedInquiry/{sort}")
	public String speedInquiry(
			@PathVariable String sort,
			Model model
			) {
		model.addAttribute("sort", sort);
		return "front/speed/speedInquiry";
	}
	
	
	@RequestMapping("/FindINFO")
	public String findINFO(
			String code,
			String phone,
			@RequestParam(required = false) String userId,
			Model model
			) {
		StringUtils util = new StringUtils();
		if("id".equals(code)) {
			if(memberRepository.findByPhone(phone).isPresent()) {
				
				smsService.sendMessage("고객님의 아이디는 " + memberRepository.findByPhone(phone).get().getUsername()
						+ System.lineSeparator() + "입니다.", phone);
				model.addAttribute("FindCODE", "SUCCESS");
			}else {
				model.addAttribute("FindCODE", "NONE");
			}
			return "front/client/memberIDFind";
		}else {
			if(memberRepository.findByPhone(phone).isPresent()) {
				
				if(memberRepository.findByPhone(phone).get().getUsername().equals(userId)) {
					String newPassWord = util.createWord();
					smsService.sendMessage("새로운 비밀번호는 " + newPassWord + "입니다." + System.lineSeparator()
					+"비밀번호를 변경 해 주시기 바랍니다.", phone);
					memberRepository.findByPhone(phone).ifPresent(m->{
						String encodedPW = passwordEncoder.encode(newPassWord);
						m.setPassword(encodedPW);
						memberRepository.save(m);
					});
					model.addAttribute("FindCODE", "SUCCESS");
				}else {
					model.addAttribute("FindCODE", "FAIL");
				}
				
			}else {
				model.addAttribute("FindCODE", "NONE");
			}
			
			return "front/client/memberPASSFind";
		}
	}
			
	
	@RequestMapping("/tempContactProc")
	@ResponseBody
	public String tempContactProc(
			String memberInfo,
			TempService tempService
			) {
		String msg = "신청이 완료 되었습니다. 빠른시간내 연락드리도록 하겠습니다.";
		StringBuilder sb = new StringBuilder();
		String username = "";
		Long userId = 0L;
		Boolean sign = smsSettingRepository.findByName("TEMP_CONTACT").get().getSign();
		if(memberInfo!=null) {
//			System.out.println(member);
//			System.out.println(member.indexOf("username"));
//			System.out.println(member.indexOf("password"));
//			System.out.println(member.substring(member.indexOf("username")+9, member.indexOf("password")-2));
//			System.out.println((member.substring(member.indexOf("username")+9, member.indexOf("password")-2).length()));
			username = memberInfo.substring(memberInfo.indexOf("username")+9, memberInfo.indexOf("password")-2);
			userId = memberRepository.findByUsername(username).get().getId();
			tempService.setMember(userId);
			tempService.setSign(true);
			tempService.setMemberid(username);
			if (contactSMSRepository.findAll().size() > 0) {
				for (int a = 0; a < contactSMSRepository.findAll().size(); a++) {
					smsService.sendMessage(
							"[신속문의접수]" + System.lineSeparator() + "문의자  : " + tempService.getName()
									+ System.lineSeparator() + "연락처 : " + tempService.getPhone()
									+ System.lineSeparator() + "회원여부 : 회원"
									+ System.lineSeparator() + "아이디 : " + username,
							contactSMSRepository.findAll().get(a).getSmsnumber());

				}
			}
		}else {
			tempService.setMember(userId);
			tempService.setSign(false);
			tempService.setMemberid("-");
			if (contactSMSRepository.findAll().size() > 0 && sign) {
				for (int a = 0; a < contactSMSRepository.findAll().size(); a++) {
					smsService.sendMessage(
							"[신속문의접수]" + System.lineSeparator() + "문의자  : " + tempService.getName()
									+ System.lineSeparator() + "연락처 : " + tempService.getPhone()
									+ System.lineSeparator() + "기존회원여부 : 비회원",
							contactSMSRepository.findAll().get(a).getSmsnumber());

				}
			}
		}
		
		tempService.setToday(new Date());
		tempService.setContact(false);
		tempServiceRepository.save(tempService);
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/index'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		
		return sb.toString();
	}
}
