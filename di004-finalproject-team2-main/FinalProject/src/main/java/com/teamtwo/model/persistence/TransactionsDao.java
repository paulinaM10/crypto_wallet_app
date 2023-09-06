package com.teamtwo.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teamtwo.dto.entity.Transactions;

@Repository 
public interface TransactionsDao extends JpaRepository<Transactions, Integer> {
	
	
	@Query("SELECT t FROM Transactions t WHERE t.walletId = :wId")
	 List<Transactions> findByWalletId(@Param("wId")int walletId);
	
	
	@Query("SELECT t FROM Transactions t WHERE t.userId = :uId")
	 List<Transactions> findByUserId(@Param("uId")int userId);

}