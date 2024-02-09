package com.integrativeproyect.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.integrativeproyect.entity.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
	
	List<BankAccount> findAll();
	
	List<BankAccount> findByNumberAccount(String numberAccount);
	//@Query("SELECT ba FROM BankAccount ba WHERE numberAccount=?1")
	
	
}
