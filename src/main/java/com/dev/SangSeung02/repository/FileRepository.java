package com.dev.SangSeung02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.SangSeung02.model.service.ClientFile;
import com.dev.SangSeung02.model.service.SangSeungService;

@Repository
public interface FileRepository extends JpaRepository<ClientFile, Long>{

	List<ClientFile> findAllByService(SangSeungService service);
}
