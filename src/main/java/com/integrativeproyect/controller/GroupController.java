package com.integrativeproyect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrativeproyect.entity.Group;
import com.integrativeproyect.repository.GroupRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v0.1/group")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {
	
	private GroupRepository groupRepository;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?> listGroups() {
		List<Group> lst = groupRepository.findAll();
		
		return ResponseEntity.ok(lst);
	}

}
