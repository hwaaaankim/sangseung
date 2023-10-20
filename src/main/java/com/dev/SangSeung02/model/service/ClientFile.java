package com.dev.SangSeung02.model.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="file")
public class ClientFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FILE_ID")
	private Long id;
	
	@Column(name="FILE_DATE")
	private String filedate;
	
	@Column(name="FILE_PATH")
	private String filepath;
	
	@Column(name="FILE_NAME")
	private String filename;
	
	@Column(name="FILE_SIZE")
	private Long filesize;

	@Column(name="FILE_ROAD")
	private String fileroad;
	
//	@ManyToOne
//	@JoinColumn(
//			name = "SERVICE_ID", 
//			referencedColumnName = "SERVICE_ID",
//			insertable = false, 
//			updatable = false
//			)
//	@JsonIgnore
//	private SangSeungService service;
	
	@ManyToOne
	@JoinColumn(name="SERVICE_ID")
	private SangSeungService service;
	
}
