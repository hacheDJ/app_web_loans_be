package com.integrativeproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integrativeproyect.entity.User;

public interface TypeDocRepository extends JpaRepository<User, Integer> {

}
