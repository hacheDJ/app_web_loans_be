package com.integrativeproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integrativeproyect.entity.User;

public interface RoleRepository extends JpaRepository<User, Integer> {

}
