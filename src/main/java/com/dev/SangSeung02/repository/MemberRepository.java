package com.dev.SangSeung02.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findByPhone(String phone);
	
	Page<Member> findAllByOrderByJoindateDesc(Pageable pageable);
	
	Page<Member> findAllByNameOrderByJoindateDesc(Pageable pageable, String name);
	
	Page<Member> findAllByPhoneOrderByJoindateDesc(Pageable pageable, String phone);
	
	Page<Member> findAllByEmailOrderByJoindateDesc(Pageable pageable, String email);
	
	Page<Member> findAllByBusinessOrderByJoindateDesc(Pageable pageable, String business);
	
	Page<Member> findAllByJoindateLessThan(Pageable pageable,Date date);
	
	Page<Member> findAllByJoindateGreaterThan(Pageable pageable,Date date);
	
	Page<Member> findAllByJoindateBetween(Pageable pageable, Date startDate, Date endDate);
	
}
