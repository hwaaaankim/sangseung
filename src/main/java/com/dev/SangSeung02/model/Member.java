package com.dev.SangSeung02.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="member")
@ToString
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MEMBER_ID")
	private Long id;
	
	@Column(name="MEMBER_USERNAME")
	private String username;
	
	@Column(name="MEMBER_PASSWORD")
	private String password;
	
	@Column(name="MEMBER_NAME")
	private String name;
	
	@Column(name="MEMBER_BUSINESS")
	@Nullable
	private String business;
	
	@Column(name="MEMBER_EMAIL")
	@Nullable
	private String email;
	
	@Column(name="MEMBER_PHONE")
	private String phone;
	
	@Column(name="MEMBER_ENABLED")
	private Boolean enabled;
	
	@Column(name="MEMBER_CONTACT")
	private Boolean contact;
	
	@Column(name="MEMBER_JOINDATE")
	private Date joindate;
	
	@Column(name="MEMBER_ROLE")
	private String role;
	
//	@OneToMany(
//			mappedBy = "member", 
//			cascade = CascadeType.ALL, 
//			orphanRemoval = true,
//			fetch = FetchType.EAGER
//			)
//	private List<SangSeungService> services = new ArrayList<>();
	
}























