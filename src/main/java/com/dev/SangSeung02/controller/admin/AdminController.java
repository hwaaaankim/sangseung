package com.dev.SangSeung02.controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.SangSeung02.repository.FileRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	FileRepository fileRepository;
	
	@RequestMapping({"/index" , ""})
	public String adminIndex() {
		
		return "admin/index";
	}
	
	@RequestMapping("/download/{id}")
	public ResponseEntity<Object> download(
			@PathVariable Long id
			) throws UnsupportedEncodingException{
		String path = "";
		String fileName = "";
		if(fileRepository.findById(id).isPresent()) {
			path = fileRepository.findById(id).get().getFilepath();
			fileName = fileRepository.findById(id).get().getFilename();
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
