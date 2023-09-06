package com.teamtwo.model.service;

import com.teamtwo.dto.entity.BuyCoinRequest;
import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.exception.InsufficientBalanceException;

public interface WalletService {

	Wallet createWallet(Wallet wallet);
	Wallet getWalletByWalletId(int walletId);
	void saveWallet(Wallet wallet);
	Double getWalletBalance(int walletId);
	Wallet createUserWallet(User user);
	Wallet getWalletByUserId (int userId);
	Wallet addMoney(int userId, int walletId, double amount) throws InsufficientBalanceException;
	Wallet removeMoney(int userId, int walletId, double amount) throws InsufficientBalanceException;
	public Wallet buyCoin(BuyCoinRequest request)  throws InsufficientBalanceException;
	public Wallet sellCoin(BuyCoinRequest request) throws InsufficientBalanceException;
	boolean deleteWallet(int walletId); 
}