package com.teamtwo.model.service;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamtwo.dto.entity.BuyCoinRequest;
import com.teamtwo.dto.entity.TransactionType;
import com.teamtwo.dto.entity.Transactions;
import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.exception.InsufficientBalanceException;
import com.teamtwo.model.persistence.TransactionsDao;
import com.teamtwo.model.persistence.WalletDao;

import jakarta.transaction.Transactional;
import java.util.Optional;


@Service
public class WalletServiceImpl implements WalletService {
	
	@Autowired
	private WalletDao walletDao;
	
	 @Autowired
	private TransactionsDao transactionDao;
	 
	 @Autowired
	 private CoinService coinService;
	 
	 @Autowired
	 private UserCoinsService userCoinsService;
	 
	 
	 

	 @Override
	 public Double getWalletBalance(int walletId) {
	        Optional<Wallet> walletOptional = walletDao.findById(walletId);
	        if (walletOptional.isPresent()) {
	            Wallet wallet = walletOptional.get();
	            return wallet.getBalance();
	        }
	        return 0.0;
	        
	 }
	 
	 @Override
	    @Transactional
	    public Wallet addMoney(int userId, int walletId, double amount) throws InsufficientBalanceException {
	        
		 Wallet wallet = getWalletByWalletId(walletId);

	        if (wallet != null) {
	            double currentBalance = wallet.getBalance();

	            if (currentBalance >= 0) {
	            	wallet.setBalance(currentBalance + amount);

	                Transactions transaction = new Transactions();
	                transaction.setUserId(userId); // Set the userId for the transaction
	                transaction.setWalletId(walletId);
	                transaction.setAmount(amount);
	                transaction.setTransactionType(TransactionType.ADD);
	                transaction.setTransactionDate(LocalDateTime.now());

	                transactionDao.save(transaction);

	                return walletDao.save(wallet);
	            } else {
	                throw new InsufficientBalanceException("Insufficient balance. Please enter a valid amount.");
	            }
	        }

	        return null;
	    }
	


	    @Override
	    @Transactional
	    public Wallet removeMoney(int userId, int walletId, double amount) throws InsufficientBalanceException {
	        Wallet wallet = getWalletByWalletId(walletId);
		    
	        if (wallet != null) {
	            double currentBalance = wallet.getBalance();

	            if (currentBalance >= amount) {
	                wallet.setBalance(currentBalance - amount);

	                Transactions transaction = new Transactions();
	                transaction.setUserId(userId); 
	                transaction.setWalletId(walletId);
	                transaction.setAmount(amount);
	                transaction.setTransactionType(TransactionType.REMOVE);
	                transaction.setTransactionDate(LocalDateTime.now());

	                transactionDao.save(transaction); 

	                return walletDao.save(wallet);
	            } else {
	                throw new InsufficientBalanceException("Insufficient balance. Please enter a valid amount.");
	            }
	        }

	        return null;
	    }


	    @Override
	    public Wallet createWallet(Wallet wallet) {
	    	wallet.setBalance(0);
	    	return walletDao.save(wallet);
	        }
	    


	    @Override
	    public Wallet createUserWallet(User user) {
	        // Check if a wallet already exists for the user
	        Wallet existingWallet = walletDao.findByUserId(user.getUserId());

	        if (existingWallet != null) {
	            return existingWallet;
	        } else {
	            Wallet wallet = new Wallet();
	            wallet.setUserId(user.getUserId());
	            return walletDao.save(wallet);
	        }
	    }

	    
	    @Override
	    public Wallet getWalletByWalletId(int walletId) {
	    	Optional<Wallet> walletOptional = walletDao.findById(walletId);
	        return walletOptional.orElse(null);
	        
	        
	    }

		@Override
		public void saveWallet(Wallet wallet) {
			walletDao.save(wallet);
			
		}
		@Override
		public Wallet getWalletByUserId (int userId) {
			return walletDao.findByUserId(userId);
		}
		
		
		
		@Override
		@Transactional
		public Wallet buyCoin(BuyCoinRequest request) throws InsufficientBalanceException {
		    Wallet wallet = getWalletByWalletId(request.getWalletId());

		    if (wallet != null) {
		        double coinPrice = coinService.getCoinPrice(request.getCoinSymbol());
		        double totalAmount = coinPrice * request.getAmount();

		        if (wallet.getBalance() >= totalAmount) {
		            wallet.setBalance(wallet.getBalance() - totalAmount);

		            Transactions transaction = new Transactions();
		            transaction.setUserId(request.getUserId());
		            transaction.setWalletId(request.getWalletId());
		            transaction.setAmount(totalAmount);
		            transaction.setTransactionType(TransactionType.BUY);
		            transaction.setTransactionDate(LocalDateTime.now());

		            transactionDao.save(transaction);
		            userCoinsService.addUserCoins(request.getUserId(), request.getCoinSymbol(), request.getAmount());


		            return walletDao.save(wallet);
		        } else {
		            throw new InsufficientBalanceException("Insufficient balance. Please enter a valid amount.");
		        }
		    }

		    return null;
		}
		
		
		@Override
		@Transactional
		public Wallet sellCoin(BuyCoinRequest request) throws InsufficientBalanceException {
		    Wallet wallet = getWalletByWalletId(request.getWalletId());

		    if (wallet != null) {
		        double userCoins = userCoinsService.getUserCoinsQuantity(request.getUserId(), request.getCoinSymbol());

		        if (userCoins >= request.getAmount()) {
		            double coinPrice = coinService.getCoinAskPrice(request.getCoinSymbol()); // note we are using getCoinAskPrice here
		            double totalAmount = coinPrice * request.getAmount();

		            wallet.setBalance(wallet.getBalance() + totalAmount);

		            Transactions transaction = new Transactions();
		            transaction.setUserId(request.getUserId());
		            transaction.setWalletId(request.getWalletId());
		            transaction.setAmount(totalAmount);
		            transaction.setTransactionType(TransactionType.SELL);
		            transaction.setTransactionDate(LocalDateTime.now());

		            transactionDao.save(transaction);

		            // assuming userCoinsService has a method updateUserCoins that will decrease the quantity of user's coins
		            userCoinsService.updateUserCoins(request.getUserId(), request.getCoinSymbol(), userCoins - request.getAmount());

		            return walletDao.save(wallet);
		        } else {
		            throw new InsufficientBalanceException("Not enough coins to sell. Please enter a valid amount.");
		        }
		    }

		    return null;
		}

		@Override
		public boolean deleteWallet(int walletId) {
		    if (walletDao.existsById(walletId)) {
	            walletDao.deleteById(walletId);
	            return true;
	        } else {
	            return false;
	        }
	    }
	
		

		
	
}