package com.integrativeproyect.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.integrativeproyect.entity.RequestLoan;

public interface RequestLoanRepository extends CrudRepository<RequestLoan, Long>{
	
	@Query("SELECT COUNT(rl) FROM RequestLoan rl WHERE DATE(registrationDate)=DATE(now()) AND idBorrower.id=?1")
	int maximunRequestToday(long id);
	
}
