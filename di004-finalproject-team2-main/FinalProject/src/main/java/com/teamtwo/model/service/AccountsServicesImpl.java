package com.teamtwo.model.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.teamtwo.dto.entity.Accounts;
import com.teamtwo.model.persistence.AccountsDao;


@Service
public class AccountsServicesImpl implements AccountsServices {
	
	@Autowired
	private AccountsDao accountsDao;

	@Override
	public Accounts updateAccountsByUserName(Accounts accounts) {
		
		Accounts existingAccount = accountsDao.findByUserName(accounts.getUserName());
        if (existingAccount != null) {
       
            existingAccount.setUserPassword(accounts.getUserPassword());
         
          
            return accountsDao.save(existingAccount);
        }
        return null;
    }

	@Override
	public Accounts findByUserName(String userName) {
		 return accountsDao.findByUserName(userName);
	}


	  @Override
	    public void deleteAccount(Accounts accounts) {
	        accountsDao.delete(accounts);
	    }

	  @Override
	    public Accounts createAccount(Accounts accounts) {
	       
	        Accounts existingAccount = accountsDao.findByUserName(accounts.getUserName());
	        if (existingAccount == null) {
	        	Accounts newAccount= new Accounts();
	        	  newAccount.setUserName(accounts.getUserName());
	              newAccount.setUserPassword(accounts.getUserPassword());
	        	newAccount.setUserId(accounts.getUserId());
	        	  
	        	  return accountsDao.save(newAccount);
	        }
	            
	        
	        return null;
	    }

	


	
}
	


