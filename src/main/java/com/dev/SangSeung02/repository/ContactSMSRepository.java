package com.dev.SangSeung02.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.ContactSMS;

@Repository
public interface ContactSMSRepository extends JpaRepository<ContactSMS, Long>{

	Optional<ContactSMS> findBySmsnumber(String smsnumber);
}
