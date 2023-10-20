package com.dev.SangSeung02.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="sms_setting")
@Data
public class SMSSetting {

	@Id
	@Column(name="SETTING_NAME")
	private String name;
	
	@Column(name="SETTING_SIGN")
	private Boolean sign;
}
