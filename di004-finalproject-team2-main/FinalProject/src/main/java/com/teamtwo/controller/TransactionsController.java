package com.teamtwo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.teamtwo.dto.entity.Transactions;
import com.teamtwo.model.service.TransactionsService;


@RestController
public class TransactionsController {
	
	@Autowired
	private TransactionsService transactionsService;

	    //this works
	    @GetMapping(path = "/transactions/wallet/{wId}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Transactions> getTransactionsByWalletId(@PathVariable ("wId") int walletId) {
	        return transactionsService.getTransactionsByWalletId(walletId);
	    }

	    //this works
	    @GetMapping(path = "/transactions/user/{uId}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Transactions> getTransactionsByUserId(@PathVariable ("uId") int userId) {
	        return transactionsService.getTransactionsByUserId(userId);
	    }
	    
	    @GetMapping("/transactions")
	    public List<Transactions> getTransactions(@RequestParam("userId") int userId) {
	        return transactionsService.getTransactionsByUserId(userId);
	    }
    }


