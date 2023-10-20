package com.dev.SangSeung02.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="popup")
@Data
public class Popup {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="POPUP_ID")
	private Long id;
}
