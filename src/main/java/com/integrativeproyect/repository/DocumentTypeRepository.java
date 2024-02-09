package com.integrativeproyect.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.integrativeproyect.entity.DocumentType;

public interface DocumentTypeRepository extends CrudRepository<DocumentType, Integer> {
	List<DocumentType>  findAll();
}
