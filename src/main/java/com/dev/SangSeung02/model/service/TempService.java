package com.dev.SangSeung02.model.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name="temp_service")
public class TempService {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TEMP_ID")
	private Long id;
	
	@Column(name="TEMP_SUBJECT")
	private String subject;
	
	@Column(name="TEMP_NAME")
	private String name;
	
	@Column(name="TEMP_PHONE")
	private String phone;
	
	@Column(name="TEMP_COMMENTS")
	private String comments;
	
	@Column(name="TEMP_SIGN")
	private Boolean sign;
	
	@Column(name="TEMP_CONTACT")
	private Boolean contact;
	
	@Column(name="TEMP_MEMBER")
	@Nullable
	private Long member;

	@Column(name="TEMP_MEMBER_ID")
	@Nullable
	private String memberid;
	
	@Column(name="TEMP_DATE")
	private Date today;
	
}





















