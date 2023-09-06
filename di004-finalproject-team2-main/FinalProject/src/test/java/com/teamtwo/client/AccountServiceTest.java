package com.teamtwo.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.teamtwo.model.service.AccountsServices;

import com.teamtwo.dto.entity.Accounts;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
	
	@Autowired 
	private AccountsServices accountsServices;
	
	private Accounts testAccounts;
	private Accounts testAccounts1;
	private Accounts testAccounts2;
//	
//	
	@BeforeEach
    void setUp() {
 
		
		
        
		testAccounts = new Accounts("jane456", "password2", 2);
		testAccounts1 = new Accounts("sam1992", "password4", 4);
		testAccounts2 = new Accounts("john123", "password1", 1);
   
        
    }

   @AfterEach
    void tearDown() {
		if (testAccounts != null) {
		      accountsServices.deleteAccount(testAccounts);
		      }
		 	if (testAccounts1 != null) {
		 	      accountsServices.deleteAccount(testAccounts1);
		 	      }
		 	if (testAccounts2 != null) {
		 	      accountsServices.deleteAccount(testAccounts2);
		 	      }
		   
   }
    
    
    @Test
    void testUpdateAccountsByUserName() {
        // Create the test account
    	testAccounts1 = new Accounts("sam1992", "password1", 1);
       Accounts newAccount =  accountsServices.createAccount(testAccounts1);

        // Search for the account by userName
        String userName = "sam1992";
        Accounts foundAccount = accountsServices.findByUserName(newAccount.getUserName());

        assertNotNull(foundAccount);
        assertEquals(userName, foundAccount.getUserName());
    
    }

    @Test
    void testFindByUserName() {
    	  // Create the test account
        accountsServices.createAccount(testAccounts2);

        // Delete the test account
        accountsServices.deleteAccount(testAccounts2);

        // Try to find the deleted account
        Accounts foundAccount = accountsServices.findByUserName(testAccounts2.getUserName());

        assertNull(foundAccount);
    }
    
    @Test
    void testDeleteAccount() {
    	testAccounts = new Accounts("john123", "password1", 1);
        accountsServices.deleteAccount(testAccounts);

    
        Accounts foundAccount = accountsServices.findByUserName(testAccounts.getUserName());

        assertNull(foundAccount);
    }
   
}
	
	
	


