package com.teamtwo.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.model.service.UserService;
import com.teamtwo.model.service.WalletService;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
	
	@Autowired 
	private UserService userService;
	@Autowired
	private WalletService walletService;
	
	private User testUser;
	private User testUser1;
	private Wallet testWallet;
	
	
	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setFullName("Joanna Doe");
		testUser.setEmail("joanna.doe@example.com");
		testUser.setUserPassword("passwordX567");
		testUser.setBankDetails(" Barclays 1234-5678-9876-5432");
		
		testUser1 = new User();
		testUser1.setFullName("Mia Wilson");
		testUser1.setEmail("mia.wilsonh@example.com");
		testUser1.setUserPassword("password98HJ");
		testUser1.setBankDetails("NatWest 9876-5432-1234-5678");
		
		// Creating a test wallet
		testWallet = new Wallet();
		testWallet.setBalance(100.0);
		testWallet = walletService.createWallet(testWallet);
		testUser.setWallet(testWallet);
	}

	
	@AfterEach
	void tearDown() {
		// Clean up the test data after each test case
		userService.deleteUser(testUser.getUserId());
		userService.deleteUser(testUser1.getUserId());
		walletService.deleteWallet(testWallet.getWalletId());
	}
	
	@Test
	void testGetUserById() {
		User createdUser = userService.createUser(testUser);
		User retrievedUser = userService.getUserById(createdUser.getUserId());
		Assertions.assertNotNull(retrievedUser);
		Assertions.assertEquals(createdUser.getUserId(), retrievedUser.getUserId());
		Assertions.assertEquals(createdUser.getFullName(), retrievedUser.getFullName());
		Assertions.assertEquals(createdUser.getEmail(), retrievedUser.getEmail());
		Assertions.assertEquals(createdUser.getBankDetails(), retrievedUser.getBankDetails());
	}
	
	@Test
	void testUpdateUser() {
		User createdUser = userService.createUser(testUser);
		createdUser.setFullName("Updated Name");
		createdUser.setEmail("updated.email@example.com");
		createdUser.setBankDetails("Updated bank details");
		
		User updatedUser = userService.updateUser(createdUser);
		Assertions.assertNotNull(updatedUser);
		Assertions.assertEquals(createdUser.getFullName(), updatedUser.getFullName());
		Assertions.assertEquals(createdUser.getEmail(), updatedUser.getEmail());
		Assertions.assertEquals(createdUser.getBankDetails(), updatedUser.getBankDetails());
	}
	
	@Test
	void testDeleteUser() {
		User createdUser = userService.createUser(testUser);
		int userId = createdUser.getUserId();
		boolean isDeleted = userService.deleteUser(userId);
		Assertions.assertTrue(isDeleted);
		User deletedUser = userService.getUserById(userId);
		Assertions.assertNull(deletedUser);
	}
	
	@Test
	void testGetAllUsers() {
		userService.createUser(testUser);
		userService.createUser(testUser1);
		
		// Check if the list of all users contains both testUser and testUser1
		Assertions.assertTrue(userService.getAllUsers().contains(testUser));
		Assertions.assertTrue(userService.getAllUsers().contains(testUser1));
	}
	

}
