package com.teamtwo.model.service;

import java.util.List;

import com.teamtwo.dto.entity.UserCoins;

public interface UserCoinsService {

	public void addUserCoins(int userId, String coinSymbol, double quantity);
       
    public void updateUserCoins(UserCoins userCoins);
      
    double getUserCoinsQuantity(int userId, String coinSymbol);
    void updateUserCoins(int userId, String coinSymbol, double newQuantity);
    List<UserCoins> getUserCoins(int userId);
    void deleteUserCoins(int userId, String coinSymbol);
	
}
