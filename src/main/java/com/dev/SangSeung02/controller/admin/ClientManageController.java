package com.dev.SangSeung02.controller.admin;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.service.ClientFile;
import com.dev.SangSeung02.model.service.SangSeungService;
import com.dev.SangSeung02.model.service.TempService;
import com.dev.SangSeung02.repository.ContactRepository;
import com.dev.SangSeung02.repository.FileRepository;
import com.dev.SangSeung02.repository.MemberRepository;
import com.dev.SangSeung02.repository.TempServiceRepository;
import com.dev.SangSeung02.service.ContactService;
import com.dev.SangSeung02.service.MemberService;
import com.dev.SangSeung02.service.SMSService;
import com.dev.SangSeung02.service.TempServiceService;

@Controller
@RequestMapping("/admin")
public class ClientManageController {

	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	SMSService smsService;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ContactService contactService;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	TempServiceRepository tempServiceRepository;
	
	@Autowired
	TempServiceService tempServiceService;
	
	
	@RequestMapping("/clientManager")
	public String clientManager(
			Model model,
			@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(required = false) String searchType,
			@RequestParam(required = false) String businessWord, 
			@RequestParam(required = false) String searchWord,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate
			) throws ParseException {
		Page<Member> members = null;
		if(searchType==null || "none".equals(searchType)) {
			members = memberRepository.findAllByOrderByJoindateDesc(pageable);
		}else if("name".equals(searchType)) {
			if("".equals(searchWord)) {
				members = memberRepository.findAllByOrderByJoindateDesc(pageable);
			}else {
				members = memberRepository.findAllByNameOrderByJoindateDesc(pageable, searchWord);
			}
		}else if("phone".equals(searchType)) {
			if("".equals(searchWord)) {
				members = memberRepository.findAllByOrderByJoindateDesc(pageable);
			}else {
				members = memberRepository.findAllByPhoneOrderByJoindateDesc(pageable, searchWord);
			}
		}else if("email".equals(searchType)) {
			if("".equals(searchWord)) {
				members = memberRepository.findAllByOrderByJoindateDesc(pageable);
			}else {
				members = memberRepository.findAllByEmailOrderByJoindateDesc(pageable, searchWord);
			}
		}else if("business".equals(searchType)) {
			members = memberRepository.findAllByBusinessOrderByJoindateDesc(pageable, businessWord);
		}else if("period".equals(searchType)) {
			members = memberService.findByDate(pageable, startDate, endDate);
		}else {
			members = memberRepository.findAllByOrderByJoindateDesc(pageable);
		}
		
		int startPage = Math.max(1, members.getPageable().getPageNumber() - 4);
		int endPage = Math.min(members.getTotalPages(), members.getPageable().getPageNumber() + 4);
		model.addAttribute("members", members);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchType", searchType);
		return "admin/clientManager";
	}
	
	@RequestMapping("/clientDetail/{id}")
	public String clientDetail(
			@PathVariable Long id,
			Model model
			) {
		
		model.addAttribute("member", memberRepository.findById(id).get());
		return "admin/clientDetail";
	}
	
	@RequestMapping("/memberUpdate")
	public String memberUpdate(
			Member member,
			Model model
			) {
		memberService.memberUpdate(member);
		model.addAttribute("member", memberRepository.findById(member.getId()).get());
		return "admin/clientDetail";
		
	}
	
	@RequestMapping("/sendMessage")
	public String sendMessage(
			String phone,
			String smsMessage,
			Member member,
			Model model
			) {
		smsService.sendMessage(smsMessage, phone);
		model.addAttribute("member", memberRepository.findById(member.getId()).get());
		return "admin/clientDetail";
		
	}
	
	@RequestMapping("/serviceManager")
	public String serviceManager(
			Model model,
			@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(required = false) String searchType,
			@RequestParam(required = false) String codeSearch, 
			@RequestParam(required = false) String sortSearch,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate
			) throws ParseException {
		
		Page<SangSeungService> services = null;
		
		if(searchType==null || "none".equals(searchType)) {
			services = contactRepository.findAllByOrderByIdDesc(pageable);
		}else if("period".equals(searchType)) {
			services = contactService.findByDate(pageable, startDate, endDate);
		}else if("sort".equals(searchType)) {
			services = contactRepository.findAllBySortOrderByIdDesc(pageable,sortSearch);
		}else if("code".equals(searchType)) {
			services = contactRepository.findAllByCodeOrderByIdDesc(pageable, codeSearch);
		}else {
			services = contactRepository.findAllByOrderByIdDesc(pageable);
		}
		
		int startPage = Math.max(1, services.getPageable().getPageNumber() - 4);
		int endPage = Math.min(services.getTotalPages(), services.getPageable().getPageNumber() + 4);
		model.addAttribute("services", services);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchType", searchType);
		model.addAttribute("codeSearch", codeSearch);
		model.addAttribute("sortSearch", sortSearch);
		return "admin/serviceManager";
	}
	
	
	@RequestMapping("/serviceDetail/{id}")
	public String serviceDetail(
			@PathVariable Long id,
			Model model
			) {
		model.addAttribute("service", contactRepository.findById(id).get());
		List<ClientFile> files = fileRepository.findAllByService(contactRepository.findById(id).get());
		model.addAttribute("files", files);
		return "admin/serviceDetail";
	}
	
	@RequestMapping("/paymentManager")
	public String paymentManager() {
		
		return "admin/paymentManager";
	}
	
	@RequestMapping("/smsManager")
	public String smsManager() {
		
		return "admin/smsManager";
	}
	
	@RequestMapping("/tempServiceManager")
	public String tempServiceManager(
			Model model,
			@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(required = false) String searchType,
			@RequestParam(required = false) Boolean signal, // 확인여부, 회원여부
			@RequestParam(required = false) String searchWord, // 이름, 연락처, 회원아이디
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Boolean contactCheck,
			@RequestParam(required = false) Boolean sign,
			@RequestParam(required = false) String subject
			) throws ParseException{
		
		Page<TempService> tempServices = null;
		
		
		if(searchType==null || "none".equals(searchType)) {
			tempServices = tempServiceRepository.findAllByOrderByIdDesc(pageable);
		}else if("name".equals(searchType)) {
			if("".equals(searchWord)) {
				tempServices = tempServiceRepository.findAllByOrderByIdDesc(pageable);
			}else {
				tempServices = tempServiceRepository.findAllByNameOrderByIdDesc(pageable, searchWord);
			}
		}else if("phone".equals(searchType)) {
			if("".equals(searchWord)) {
				tempServices = tempServiceRepository.findAllByOrderByIdDesc(pageable);
			}else {
				tempServices = tempServiceRepository.findAllByPhoneOrderByIdDesc(pageable, searchWord);
			}
		}else if("subject".equals(searchType)) {
			tempServices = tempServiceRepository.findAllBySubjectOrderByIdDesc(pageable, subject);
		}else if("period".equals(searchType)) {
			tempServices = tempServiceService.findByDate(pageable, startDate, endDate);
		}else if("sign".equals(searchType)){
			tempServices = tempServiceRepository.findAllBySignOrderByIdDesc(pageable, sign);
		}else if("contactCheck".equals(searchType)){
			tempServices = tempServiceRepository.findAllByContactOrderByIdDesc(pageable, contactCheck);
		}else {
			tempServices = tempServiceRepository.findAllByOrderByIdDesc(pageable);
		}
		
		int startPage = Math.max(1, tempServices.getPageable().getPageNumber() - 4);
		int endPage = Math.min(tempServices.getTotalPages(), tempServices.getPageable().getPageNumber() + 4);
		model.addAttribute("services", tempServices);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchType", searchType);
		model.addAttribute("contactCheck", contactCheck);
		model.addAttribute("sign", sign);
		model.addAttribute("subject", subject);
		return "admin/tempServiceManager";
	}
	
	@RequestMapping("/tempServiceDetail/{id}")
	public String tempServiceDetail(
			@PathVariable Long id,
			Model model
			) {
		model.addAttribute("service", tempServiceRepository.findById(id).get());
		return "admin/tempServiceDetail";
	}
}
