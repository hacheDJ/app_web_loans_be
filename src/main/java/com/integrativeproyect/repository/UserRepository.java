package com.integrativeproyect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.integrativeproyect.entity.User;


public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findById(Long id);
	Optional<User> findByUserLogin(String username);
	Optional<User> findByPasswordLogin(String pass);
	Optional<User> findByNumberDoc(String numDoc);
	
	@Query("SELECT u FROM User u WHERE u.role='LENDER_BOSS' OR u.role='LENDER'")
	List<User> findByRoleLenderBossAndLender();
	
	@Query("SELECT count(u) AS total FROM User u INNER JOIN Group g ON u.idGroup.id=g.id WHERE g.id=:id and u.role='LENDER_BOSS'")
	int findQuantityLenderBossInGroup(@Param("id") int id);
	
	@Query("SELECT u FROM User u WHERE u.role='LENDER' AND u.idGroup.id=:idGroup")
	List<User> findByRoleLenderAndByGroup(@Param("idGroup") int idGroup);
}

