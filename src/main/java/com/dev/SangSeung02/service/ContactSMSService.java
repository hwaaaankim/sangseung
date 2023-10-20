package com.dev.SangSeung02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.SangSeung02.repository.ContactSMSRepository;

@Service
public class ContactSMSService {

	@Autowired
	ContactSMSRepository contactSMSRepository;

	public void delete(Long[] id) {
		for(Long num:id) {
			contactSMSRepository.deleteById(num);
		}
	}
}
