package com.dev.SangSeung02.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="faq")
@Data
public class Faq {

	@Id
	@Column(name="FAQ_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
}
