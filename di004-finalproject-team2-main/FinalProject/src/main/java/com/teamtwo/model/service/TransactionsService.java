package com.teamtwo.model.service;

import java.util.List;

import com.teamtwo.dto.entity.Transactions;

public interface TransactionsService {
	 
	Transactions createTransaction(Transactions transactions);
    List<Transactions> getTransactionsByWalletId(int walletId);
    List<Transactions> getTransactionsByUserId(int userId);
    public void deleteTransactions(int transactionId);
    public Transactions getTransactionById(int transactionId);

}