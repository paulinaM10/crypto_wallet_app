package com.teamtwo.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teamtwo.dto.entity.Accounts;

@Repository 
public interface AccountsDao extends JpaRepository<Accounts, String> {

	
	@Query("from Accounts where userName=:uN")
	 Accounts findByUserName(@Param("uN")String userName);
}
