package com.integrativeproyect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrativeproyect.entity.DocumentType;
import com.integrativeproyect.repository.DocumentTypeRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v0.1/document")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentTypeController {
	
	private DocumentTypeRepository documentTypeRepository;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<DocumentType>> listDocuments() {
		List<DocumentType> lst = documentTypeRepository.findAll();
		
		return ResponseEntity.ok(lst);
	}
}
