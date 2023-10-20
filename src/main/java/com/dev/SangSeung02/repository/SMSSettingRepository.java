package com.dev.SangSeung02.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.SMSSetting;

@Repository
public interface SMSSettingRepository extends JpaRepository<SMSSetting, String>{

	Optional<SMSSetting> findByName(String name);
	
}
