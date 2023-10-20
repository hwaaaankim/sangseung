package com.dev.SangSeung02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.service.ManagerFile;

@Repository
public interface ManagerFileRepository extends JpaRepository<ManagerFile, Long>{

}
