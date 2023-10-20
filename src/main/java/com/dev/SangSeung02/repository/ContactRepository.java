package com.dev.SangSeung02.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.service.SangSeungService;

@Repository
public interface ContactRepository extends JpaRepository<SangSeungService, Long>{

	List<SangSeungService> findAllByMember(Member member);
	
	Page<SangSeungService> findAllByOrderByIdDesc(Pageable pageable);
	
	Page<SangSeungService> findAllByCodeOrderByIdDesc(Pageable pageable, String code);
	
	Page<SangSeungService> findAllBySubjectOrderByIdDesc(Pageable pageable, String subect);
	
	Page<SangSeungService> findAllBySortOrderByIdDesc(Pageable pageable, String sort);
	
	Page<SangSeungService> findAllByInsertdateLessThan(Pageable pageable,Date date);
	
	Page<SangSeungService> findAllByInsertdateGreaterThan(Pageable pageable,Date date);
	
	Page<SangSeungService> findAllByInsertdateBetween(Pageable pageable, Date startDate, Date endDate);
}






















