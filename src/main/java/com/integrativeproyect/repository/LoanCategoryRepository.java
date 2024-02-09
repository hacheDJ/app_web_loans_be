package com.integrativeproyect.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.integrativeproyect.entity.LoanCategory;

public interface LoanCategoryRepository extends CrudRepository<LoanCategory, Integer> {
	List<LoanCategory> findAll();
}
