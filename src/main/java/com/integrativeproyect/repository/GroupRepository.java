package com.integrativeproyect.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.integrativeproyect.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
	List<Group> findAll();
}
