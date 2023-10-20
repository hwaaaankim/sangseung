package com.dev.SangSeung02.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.service.SangSeungService;
import com.dev.SangSeung02.service.ContactService;
import com.dev.SangSeung02.service.FileService;

@Controller
@RequestMapping("/my")
public class ContactController {

	@Autowired
	FileService fileService;
	
	@Autowired
	ContactService contactService;
	
	@RequestMapping("/contactProc")
	@ResponseBody
	public String contactProc(
			SangSeungService service,
			List<MultipartFile> files,
			String[] keywords,
			@AuthenticationPrincipal Member member
			) throws IllegalStateException, IOException {
		
		
		contactService.contactInsert(service, files, keywords, member);
	
		String msg = "신청이 완료 되었습니다. 빠른시간내 연락드리도록 하겠습니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/index'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		
		return sb.toString();
	}
}
