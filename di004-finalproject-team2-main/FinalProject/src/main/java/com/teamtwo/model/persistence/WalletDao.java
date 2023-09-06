package com.teamtwo.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teamtwo.dto.entity.Wallet;

@Repository 
public interface WalletDao extends JpaRepository<Wallet, Integer> {
	Wallet findByUserId(int userId);

}