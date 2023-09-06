package com.teamtwo.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.teamtwo.model.service.TransactionsService;
import com.teamtwo.model.service.WalletService;
import com.teamtwo.dto.entity.*;

@SpringBootTest
@ActiveProfiles("test")
public class WalletServiceTest {
	
	@Autowired
	private WalletService walletService;
	
	 @Autowired
	 private TransactionsService transactionsService;
	
	 private Wallet testWallet;
	 private Wallet testWallet1;
	 
	 private User testUser;
	
	@BeforeEach
    public void setUp() {
      

        // Create a test wallet
        testWallet = new Wallet();
        testWallet.setUserId(1);
        testWallet.setBalance(100.0);
       // Wallet createdWallet = walletService.createWallet(testWallet);
        
     // Create a test wallet
        testWallet1 = new Wallet();
        testWallet1.setUserId(2);
        testWallet1.setBalance(0.0);
       // Wallet createdWallet = walletService.createWallet(testWallet1);


       
    }
	
	@AfterEach
    public void tearDown() {
        
        if (walletService.getWalletByWalletId(testWallet.getWalletId()) != null) {
            walletService.deleteWallet(testWallet.getWalletId());
        }
        
        if (walletService.getWalletByWalletId(testWallet1.getWalletId()) != null) {
            walletService.deleteWallet(testWallet1.getWalletId());
        }
        
        
    }

	
    @Test
    public void testCreateWallet() {
    
        Wallet createdWallet = walletService.createWallet(testWallet);
        Assertions.assertNotNull(createdWallet.getWalletId());
        Assertions.assertEquals(testWallet.getUserId(), createdWallet.getUserId());
        Assertions.assertEquals(testWallet.getBalance(), createdWallet.getBalance());
    }
    

    @Test
    public void testGetWalletByUserId() {
        // Test the retrieval of a wallet by user ID

        // Retrieve the test wallet by user ID
        Wallet retrievedWallet = walletService.getWalletByUserId(testWallet.getUserId());

        // Verify the retrieved wallet is not null and has the correct user ID and balance
        Assertions.assertNotNull(retrievedWallet);
        Assertions.assertEquals(testWallet.getUserId(), retrievedWallet.getUserId());
        Assertions.assertEquals(testWallet.getBalance(), retrievedWallet.getBalance());
    }
    
    
    @Test
    void testDeleteWallet() {
    	
    	Wallet createdWallet = walletService.createWallet(testWallet);
    	boolean isDeleted = walletService.deleteWallet(testWallet.getWalletId());
		Assertions.assertTrue(isDeleted);
		Wallet deletedWallet = walletService.getWalletByWalletId(testWallet.getWalletId());
		Assertions.assertNull(deletedWallet);
    }

    @Test
    void testGetWalletByWalletId() {
    	
    	  Wallet createdWallet = walletService.createWallet(testWallet1);
    	
    	 Wallet retrievedWallet = walletService.getWalletByWalletId(createdWallet.getWalletId());
    	 
    	 Assertions.assertNotNull(retrievedWallet);
         Assertions.assertEquals(testWallet1.getUserId(), retrievedWallet.getUserId());
         Assertions.assertEquals(testWallet1.getBalance(), retrievedWallet.getBalance());
    }
    
    @Test
   void testGetWalletBalance() {
        // Test getting the balance of the test wallet
        double balance = walletService.getWalletBalance(testWallet1.getWalletId());
        Assertions.assertEquals(testWallet1.getBalance(), balance);

        // Test getting the balance of a non-existing wallet
        double nonExistingWalletBalance = walletService.getWalletBalance(-1);
        Assertions.assertEquals(0.0, nonExistingWalletBalance);
    }
    
    
    @Test 
    void testCreateUserWallet() {
    	
    	// Create a test user
        testUser = new User();
        testUser.setUserId(1);
        testUser.setFullName("testuser");
        // Test creating a new wallet for a user without an existing wallet
        Wallet wallet = walletService.createUserWallet(testUser);
        Assertions.assertNotNull(wallet);
        Assertions.assertEquals(testUser.getUserId(), wallet.getUserId());

        // Test creating a new wallet for a user who already has an existing wallet
        Wallet existingWallet = walletService.createUserWallet(testUser);
        Assertions.assertNotNull(existingWallet);
        Assertions.assertEquals(wallet.getWalletId(), existingWallet.getWalletId());
    }
    	
    	
    @Test
    public void testSaveWallet() {
        // Save the test wallet
        walletService.saveWallet(testWallet);

        // Retrieve the wallet from the database
        Wallet savedWallet = walletService.getWalletByWalletId(testWallet.getWalletId());
        Assertions.assertNotNull(savedWallet);
        Assertions.assertEquals(testWallet.getWalletId(), savedWallet.getWalletId());
        Assertions.assertEquals(testWallet.getUserId(), savedWallet.getUserId());
        Assertions.assertEquals(testWallet.getBalance(), savedWallet.getBalance());
    }
    
//    // Test for addMoney method
//    @Test
//    public void testAddMoney() throws InsufficientBalanceException {
//    	
//    
//    	
//        int userId = 1;
//        int walletId = testWallet.getWalletId();
//        double amount = 500.0;
//
//        // Call the addMoney method to add money to the wallet
//        Wallet updatedWallet = walletService.addMoney(userId, walletId, amount);
//
//        // Verify that the wallet balance is correctly updated
//        Assertions.assertNotNull(updatedWallet);
//        Assertions.assertEquals(testWallet.getBalance() + amount, updatedWallet.getBalance());
//
//        // Verify that a transaction was created for the deposit
//        Transactions transaction = transactionsService.getTransactionsByWalletId(walletId).get(0);
//        Assertions.assertNotNull(transaction);
//        Assertions.assertEquals(walletId, transaction.getWalletId());
//        Assertions.assertEquals(userId, transaction.getUserId());
//        Assertions.assertEquals(amount, transaction.getAmount());
//        Assertions.assertEquals(TransactionType.ADD, transaction.getTransactionType());
//    }
    
  
    
}
