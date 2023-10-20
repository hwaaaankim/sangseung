package com.dev.SangSeung02.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.SangSeung02.model.ContactSMS;
import com.dev.SangSeung02.repository.ContactSMSRepository;
import com.dev.SangSeung02.repository.ManagerFileRepository;
import com.dev.SangSeung02.repository.SMSSettingRepository;
import com.dev.SangSeung02.service.ContactSMSService;
import com.dev.SangSeung02.service.FileService;

@Controller
@RequestMapping("/admin")
public class SiteController {

	@Autowired
	ContactSMSRepository contactSMSRepository;
	
	@Autowired
	ContactSMSService contactSMSService;
	
	@Autowired
	SMSSettingRepository smsSettingRepository;
	
	@Autowired
	ManagerFileRepository managerFileRepository;
	
	@Autowired
	FileService fileService;
	
	@RequestMapping("/siteManager")
	public String siteManager(
			Model model
			) {
		model.addAttribute("insert", smsSettingRepository.findAll().get(0));
		model.addAttribute("member", smsSettingRepository.findAll().get(1));
		model.addAttribute("temp", smsSettingRepository.findAll().get(2));
		model.addAttribute("number", contactSMSRepository.findAll());
		return "admin/siteManager";
	}
	
	@RequestMapping("/setting")
	@ResponseBody
	public String smsSetting(
			String name,
			Boolean sign
			) {
		smsSettingRepository.findByName(name).ifPresent(m->{
			m.setSign(sign);
			smsSettingRepository.save(m);
		});
//		System.out.println(name);
//		System.out.println(sign);
		return "success";
	}
	
	
	@RequestMapping("/insertSMS")
	@ResponseBody
	public String insertSMS(
			ContactSMS contactSMS
			) {
		
		String msg = "";
		StringBuilder sb = new StringBuilder();
		if(contactSMSRepository.findBySmsnumber(contactSMS.getSmsnumber()).isPresent()) {
			
			msg = "동일한 연락처는 등록할 수 없습니다.";
			sb.append("alert('"+msg+"');");
			sb.append("location.href='/admin/siteManager'");
			sb.insert(0, "<script>");
			sb.append("</script>");
		}else {

			msg = "등록이 완료 되었습니다.";
			contactSMSRepository.save(contactSMS);
			sb.append("alert('"+msg+"');");
			sb.append("location.href='/admin/siteManager'");
			sb.insert(0, "<script>");
			sb.append("</script>");
		}
		
		return sb.toString();
		
	}
	
	@RequestMapping("/deleteSMS")
	@ResponseBody
	public String deleteSMS(
			@RequestParam(value = "id[]") Long[] id
			) {
		
		contactSMSService.delete(id);
		return "SUCCESS";
	}
	
	@RequestMapping("/siteFileManager")
	public String siteFileManager(
			Model model,
			Pageable pageable
			) {
		
		model.addAttribute("files", managerFileRepository.findAll(pageable));
		return "admin/siteFileManager";
	}
	
	@RequestMapping("/siteFileUpload")
	@ResponseBody
	public String contactProc(
			List<MultipartFile> files
			) throws IllegalStateException, IOException {
		
		
		fileService.fileUpload(files);
		String msg = "업로드 완료.";
		StringBuilder sb = new StringBuilder();
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/admin/siteFileManager'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		
		return sb.toString();
	}
	
	@RequestMapping("/managerFiledownload/{id}")
	public ResponseEntity<Object> managerFiledownload(
			@PathVariable Long id
			) throws UnsupportedEncodingException{
		String path = "";
		String fileName = "";
		if(managerFileRepository.findById(id).isPresent()) {
			path = managerFileRepository.findById(id).get().getFilepath();
			fileName = managerFileRepository.findById(id).get().getFilename();
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		
		try {
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
//			File file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
}
