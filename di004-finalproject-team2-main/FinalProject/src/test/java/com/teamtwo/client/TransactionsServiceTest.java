package com.teamtwo.client;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import com.teamtwo.dto.entity.TransactionType;
import com.teamtwo.dto.entity.Transactions;
import com.teamtwo.model.service.TransactionsService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionsServiceTest {
	
	
	@Autowired 
	private TransactionsService transactionService;
	
	private Transactions testTransactions;
	private Transactions testTransactions1;

	
	
    @BeforeEach
    void setUp() {
    	testTransactions = new Transactions(111, 1111,1, 100.0, TransactionType.ADD, LocalDateTime.now());
        testTransactions1 = new Transactions(112,1112,1, 50.0, TransactionType.REMOVE, LocalDateTime.now());
  
    }

    @AfterEach
    void tearDown() {
        if (testTransactions != null) {
            transactionService.deleteTransactions(testTransactions.getTransactionId());
        }
        if (testTransactions1 != null) {
            transactionService.deleteTransactions(testTransactions1.getTransactionId());
    
    }
    }

    @Test
    void testCreateTransaction() {
        Transactions createdTransaction = transactionService.createTransaction(testTransactions);
        assertNotNull(createdTransaction.getTransactionId());
    }

    @Test
    void testGetTransactionsByWalletId() {
        Transactions createdTransaction = transactionService.createTransaction(testTransactions);
        int walletId = createdTransaction.getWalletId();
        List<Transactions> transactionsList = transactionService.getTransactionsByWalletId(walletId);
        assertEquals(1, transactionsList.size());
    }

    @Test
    void testGetTransactionsByUserId() {
        Transactions createdTransaction = transactionService.createTransaction(testTransactions);
        int userId = createdTransaction.getUserId();
        List<Transactions> transactionsList = transactionService.getTransactionsByUserId(userId);
        assertEquals(1, transactionsList.size());
    }

    @Test
    
    void testDeleteTransactions() {
    	// Save a test transaction
        Transactions savedTransaction = transactionService.createTransaction(testTransactions1);
        int transactionIdToDelete = savedTransaction.getTransactionId();

        // Delete the saved transaction
       transactionService.deleteTransactions(transactionIdToDelete);

        // Attempt to retrieve the deleted transaction
        Transactions retrievedTransaction = transactionService.getTransactionById(transactionIdToDelete);

        // Assert that the retrieved transaction is null (deleted)
        assertNull(retrievedTransaction);
      	
    }
}
	

	


