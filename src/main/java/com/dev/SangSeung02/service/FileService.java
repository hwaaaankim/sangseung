package com.dev.SangSeung02.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.service.ClientFile;
import com.dev.SangSeung02.model.service.ManagerFile;
import com.dev.SangSeung02.model.service.SangSeungService;
import com.dev.SangSeung02.repository.FileRepository;
import com.dev.SangSeung02.repository.ManagerFileRepository;

@Service
public class FileService {

	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	ManagerFileRepository managerFileRepository;
	
	public String fileUpload(
			List<MultipartFile> files,
			Member member,
			SangSeungService service
			) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
//        String absolutePath = new File("").getAbsolutePath() + "\\";
//        String path = "src/main/resources/static/front/upload/"+current_date;
        String path = "/home/hosting_users/winwinpat/tomcat/webapps/upload/";
        String road = "/front/upload/"+current_date;
        File fileFolder = new File(path);
        
        if(!fileFolder.exists()) {
        	fileFolder.mkdirs();
        }
        
        for(MultipartFile file : files) {
        	if(!file.isEmpty()) {
        		ClientFile f = new ClientFile();
        		
            	String contentType = file.getContentType();
                String originalFileExtension = "";
                    // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    return "NONE";
                }else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    else if(contentType.contains("application/pdf")) {
                    	originalFileExtension = ".pdf";
                    }
                    else if(contentType.contains("application/x-zip-compressed")) {
                    	originalFileExtension = ".zip";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    	originalFileExtension = ".xlsx";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    	originalFileExtension = ".docx";
                    }
                    else if(contentType.contains("text/plain")) {
                    	originalFileExtension = ".txt";
                    }
                    else if(contentType.contains("image/x-icon")) {
                    	originalFileExtension = ".ico";
                    }
                    else if(contentType.contains("application/haansofthwp")) {
                    	originalFileExtension = ".hwp";
                    }
                }
                String new_file_name = member.getName() + "_" + file.getOriginalFilename() + "_" + Long.toString(System.nanoTime()) + originalFileExtension;
                
//                fileFolder = new File(absolutePath + path + "/" + new_file_name);
                fileFolder = new File(path + "/" + new_file_name);
                file.transferTo(fileFolder);
                f.setFiledate(current_date);
//                f.setFilepath(absolutePath + path + "/" + new_file_name);
                f.setFilepath(path + "/" + new_file_name);
                f.setFileroad(road + "/" + new_file_name );
                f.setFilename(new_file_name);
                f.setService(service);
                f.setFilesize(file.getSize());
                fileRepository.save(f);
            }
        }
		return "index";
	}
	
	public String fileUpload(
			List<MultipartFile> files
			) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
//        String absolutePath = new File("").getAbsolutePath() + "\\";
//        String path = "src/main/resources/static/admin/files/"+current_date;
        String path = "/home/hosting_users/winwinpat/tomcat/webapps/files/";
        String road = "/front/files/"+current_date;
        File fileFolder = new File(path);
        
        if(!fileFolder.exists()) {
        	fileFolder.mkdirs();
        }
        
        for(MultipartFile file : files) {
        	if(!file.isEmpty()) {
        		ManagerFile f = new ManagerFile();
        		
            	String contentType = file.getContentType();
                String originalFileExtension = "";
                    // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    return "NONE";
                }else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    else if(contentType.contains("application/pdf")) {
                    	originalFileExtension = ".pdf";
                    }
                    else if(contentType.contains("application/x-zip-compressed")) {
                    	originalFileExtension = ".zip";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    	originalFileExtension = ".xlsx";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    	originalFileExtension = ".docx";
                    }
                    else if(contentType.contains("text/plain")) {
                    	originalFileExtension = ".txt";
                    }
                    else if(contentType.contains("image/x-icon")) {
                    	originalFileExtension = ".ico";
                    }
                    else if(contentType.contains("application/haansofthwp")) {
                    	originalFileExtension = ".hwp";
                    }
                }
                String new_file_name =Long.toString(System.nanoTime()) +  "_" + file.getOriginalFilename();
                
//                fileFolder = new File(absolutePath + path + "/" + new_file_name);
                fileFolder = new File(path + "/" + new_file_name);
                file.transferTo(fileFolder);
                f.setFiledate(current_date);
//                f.setFilepath(absolutePath + path + "/" + new_file_name);
                f.setFilepath(path + "/" + new_file_name);
                f.setFileroad(road + "/" + new_file_name );
                f.setFilename(file.getOriginalFilename());
                f.setDownload(0);
                f.setFilesize(file.getSize());
                managerFileRepository.save(f);
            }
        }
        
        return "success";
	}
}
