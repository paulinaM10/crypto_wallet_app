package com.teamtwo.model.service;

import com.teamtwo.dto.entity.Accounts;

public interface AccountsServices {

	Accounts updateAccountsByUserName (Accounts accounts);

	Accounts findByUserName(String userName);

	void deleteAccount(Accounts accounts);
	Accounts createAccount(Accounts accounts);
	
	
	
	
	
	
}
