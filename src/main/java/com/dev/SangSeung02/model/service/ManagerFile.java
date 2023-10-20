package com.dev.SangSeung02.model.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="manager_file")
public class ManagerFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MFILE_ID")
	private Long id;
	
	@Column(name="MFILE_DATE")
	private String filedate;
	
	@Column(name="MFILE_PATH")
	private String filepath;
	
	@Column(name="MFILE_NAME")
	private String filename;
	
	@Column(name="MFILE_SIZE")
	private Long filesize;

	@Column(name="MFILE_ROAD")
	private String fileroad;
	
	@Column(name="MFILE_DOWNLOAD")
	private int download;
}
