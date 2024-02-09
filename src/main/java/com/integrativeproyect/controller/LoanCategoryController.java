package com.integrativeproyect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrativeproyect.entity.LoanCategory;
import com.integrativeproyect.repository.LoanCategoryRepository;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v0.1/loanCategory")
@CrossOrigin(origins = "http://localhost:4200")
public class LoanCategoryController {

	private LoanCategoryRepository loanCategoryRepository;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> listGroups() {
		List<LoanCategory> lst = loanCategoryRepository.findAll();
		
		return ResponseEntity.ok(lst);
	}
	
	
}
