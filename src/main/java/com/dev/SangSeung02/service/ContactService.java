package com.dev.SangSeung02.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.service.SangSeungService;
import com.dev.SangSeung02.repository.ContactRepository;
import com.dev.SangSeung02.repository.ContactSMSRepository;
import com.dev.SangSeung02.repository.SMSSettingRepository;
import com.dev.SangSeung02.utils.StringUtils;

@Service
public class ContactService {

	@Autowired
	FileService fileService;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	SMSService smsService;

	@Autowired
	ContactSMSRepository contactSMSRepository;
	
	@Autowired
	SMSSettingRepository smsSettingRepository;

	public void contactInsert(
			SangSeungService service, 
			List<MultipartFile> files, 
			String[] keywords, 
			Member member
			)
			throws IllegalStateException, IOException {
		Boolean sign = smsSettingRepository.findByName("MEMBER_CONTACT").get().getSign();
		String keyword = "";
		for (int a = 0; a < keywords.length; a++) {
			if (a == keywords.length - 1) {
				keyword += "#" + keywords[a];
			} else {
				keyword += "#" + keywords[a] + " ";
			}
		}
		StringUtils utils = new StringUtils();
		if (!"".equals(service.getInquirydate())) {
			service.setInquirydate(utils.changeDate(service.getInquirydate()));
		} else {
			service.setInquirydate("선택없음");
		}
		if("".equals(service.getInquirytime())) {
			service.setInquirytime("선택없음");
		}
		Date today = new Date();
		service.setInsertdate(today);
		service.setCorrectdate(today);
		service.setContact(false);
		service.setKeyword(keyword);
		service.setMember(member);
		SangSeungService s = contactRepository.save(service);
		if (files.size() > 0 && files != null) {
			fileService.fileUpload(files, member, s);
		}
		if (contactSMSRepository.findAll().size() > 0 && sign) {
			for (int a = 0; a < contactSMSRepository.findAll().size(); a++) {
				smsService.sendMessage(
						"문의 : " + service.getCode() + System.lineSeparator() + "문의자 : " + member.getName()
								+ System.lineSeparator() + "연락처 : " + member.getPhone(),
						contactSMSRepository.findAll().get(a).getSmsnumber());

			}
		}
	}

	public Page<SangSeungService> findByDate(Pageable pageable, String startDate, String endDate)
			throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("".equals(startDate) && "".equals(startDate)) {

			Date today = new Date();
			String day = bf.format(today);

			String start = day.substring(0, 10) + " 00:00:00";
			String end = day.substring(0, 10) + " 23:59:00";

			Date first = bf.parse(start);
			Date second = bf.parse(end);
			return contactRepository.findAllByInsertdateBetween(pageable, first, second);

		} else if (!"".equals(startDate) && !"".equals(startDate) && startDate.equals(endDate)) {
			String start = startDate.substring(0, 10) + " 00:00:00";
			Date first = f.parse(start);
			Date second = f.parse(start);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return contactRepository.findAllByInsertdateBetween(pageable, first, second);

		} else if ("".equals(startDate) && !"".equals(endDate)) {

			Date second = f.parse(endDate);
			return contactRepository.findAllByInsertdateLessThan(pageable, second);

		} else if (!"".equals(startDate) && "".equals(endDate)) {
			Date first = f.parse(startDate);
			return contactRepository.findAllByInsertdateGreaterThan(pageable, first);
		} else {
			Date first = f.parse(startDate);
			Date second = f.parse(endDate);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return contactRepository.findAllByInsertdateBetween(pageable, first, second);
		}
	}
}
