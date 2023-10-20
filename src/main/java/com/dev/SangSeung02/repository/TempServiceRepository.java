package com.dev.SangSeung02.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.service.TempService;

@Repository
public interface TempServiceRepository extends JpaRepository<TempService, Long>{
	
	Page<TempService> findAllByOrderByIdDesc(Pageable pageable);
	
	Page<TempService> findAllByNameOrderByIdDesc(Pageable pageable, String name);
	
	Page<TempService> findAllByPhoneOrderByIdDesc(Pageable pageable, String phone);
	
	Page<TempService> findAllBySubjectOrderByIdDesc(Pageable pageable, String subject);
	
	Page<TempService> findAllBySignOrderByIdDesc(Pageable pageable, Boolean sign);
	
	Page<TempService> findAllByContactOrderByIdDesc(Pageable pageable, Boolean contact);
	
	Page<TempService> findAllByTodayLessThan(Pageable pageable, Date date);
	
	Page<TempService> findAllByTodayGreaterThan(Pageable pageable, Date date);
	
	Page<TempService> findAllByTodayBetween(Pageable pageable, Date startDate, Date endDate);
}
