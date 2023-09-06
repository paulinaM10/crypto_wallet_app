package com.teamtwo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamtwo.dto.entity.Transactions;
import com.teamtwo.model.persistence.TransactionsDao;


@Service
public class TransactionsServiceImpl implements TransactionsService {

	
	@Autowired
	private TransactionsDao transactionsDao;
	@Override
	public Transactions createTransaction(Transactions transactions) {
		return transactionsDao.save(transactions);
	}

	@Override
	public List<Transactions> getTransactionsByWalletId(int walletId) {
		return transactionsDao.findByWalletId(walletId);
	}

	@Override
	public List<Transactions> getTransactionsByUserId(int userId) {
		 return transactionsDao.findByUserId(userId);
	}

	   @Override
	    public void deleteTransactions(int transactionId) {
	        transactionsDao.deleteById(transactionId);
	    }

	@Override
	public Transactions getTransactionById(int transactionId) {

		 Optional<Transactions> optionalTransaction = transactionsDao.findById(transactionId);
	       return optionalTransaction.orElse(null);
	}
}