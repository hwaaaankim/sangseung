package com.dev.SangSeung02.model.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.dev.SangSeung02.model.Member;

import lombok.Data;

@Entity
@Table(name="service")
@Data
public class SangSeungService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SERVICE_ID")
	private Long id;
	
	@Nullable
	@Column(name="INQUIRY_DATE")
	private String inquirydate;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@Column(name="KEYWORDS")
	private String keyword;
	
	@Column(name="INQUIRY_TIME")
	private String inquirytime;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="SORT")
	private String sort;
	
	@Column(name="INSERT_DATE")
	private Date insertdate;
	
	@Column(name="CORRECT_DATE")
	private Date correctdate;
	
	@Column(name="CONTACT")
	private Boolean contact;
	
//	@OneToMany(
//			mappedBy = "service", 
//			cascade = CascadeType.ALL, 
//			orphanRemoval = true,
//			fetch = FetchType.EAGER
//			)
//	@JsonIgnore
//	private List<File> files = new ArrayList<>();
	
//	@ManyToOne
//	@JoinColumn(
//			name = "MEMBER_ID", 
//			referencedColumnName = "MEMBER_ID", 
//			insertable = false, 
//			updatable = false
//			)
//	private Member member;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
}
