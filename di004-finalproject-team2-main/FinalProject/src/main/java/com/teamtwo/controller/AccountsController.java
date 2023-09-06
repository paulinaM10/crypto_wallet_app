package com.teamtwo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.teamtwo.dto.entity.Accounts;
import com.teamtwo.model.service.AccountsServices;

@RestController
public class AccountsController {
	
	@Autowired
    private AccountsServices accountsService;
	
	
	@PostMapping(path = "/accounts/{uN}", produces = MediaType.APPLICATION_JSON_VALUE)
	Accounts updateAccountByUserName(@PathVariable("uN") String userName, @RequestBody Accounts updatedAccount) {
	    Accounts existingAccount = accountsService.findByUserName(userName);
	    if (existingAccount != null) {
	        existingAccount.setUserPassword(updatedAccount.getUserPassword());
	  
	        return accountsService.updateAccountsByUserName(existingAccount);
	    }
	    return null;

}
	
	@GetMapping(path = "/accounts/{uN}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Accounts> getAccountInfo(@PathVariable("uN") String userName) {
	    Accounts account = accountsService.findByUserName(userName);

	    if (account != null) {
	        Accounts accountInfo = new Accounts(account.getUserName(), account.getUserPassword(), account.getUserId());
	        return ResponseEntity.ok(accountInfo);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	  @DeleteMapping(path ="/accounts/{uN}", produces= MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<String> deleteAccount(@PathVariable("uN") String userName) {
	        Accounts existingAccount = accountsService.findByUserName(userName);
	        if (existingAccount != null) {
	            accountsService.deleteAccount(existingAccount);
	            return ResponseEntity.ok("Account deleted successfully.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	  

}

