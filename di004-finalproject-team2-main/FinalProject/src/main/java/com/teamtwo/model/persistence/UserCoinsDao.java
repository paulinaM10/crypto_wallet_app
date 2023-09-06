package com.teamtwo.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamtwo.dto.entity.UserCoins;

public interface UserCoinsDao extends JpaRepository<UserCoins, Integer> {
	List<UserCoins> findByUserId(int userId);
	List<UserCoins> findByUserIdAndCoinSymbol(int userId, String coinSymbol);
}
