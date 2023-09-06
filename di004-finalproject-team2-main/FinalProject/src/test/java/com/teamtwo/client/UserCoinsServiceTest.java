package com.teamtwo.client;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import com.teamtwo.dto.entity.UserCoins;
import com.teamtwo.model.service.UserCoinsService;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
public class UserCoinsServiceTest {
	
	@Autowired
	private UserCoinsService userCoinsService;
	
	private List<UserCoins> userCoinsList;

    @BeforeEach
    public void setUp() {
        userCoinsList = new ArrayList<>();
        // Add some sample data to the userCoinsList
        UserCoins userCoins1 = new UserCoins();
        userCoins1.setId(1);
        userCoins1.setUserId(1);
        userCoins1.setCoinSymbol("BTC");
        userCoins1.setQuantity(0.5);
       

        UserCoins userCoins2 = new UserCoins();
        userCoins2.setId(2);
        userCoins2.setUserId(1);
        userCoins2.setCoinSymbol("ETH");
        userCoins2.setQuantity(2.0);

        userCoinsList.add(userCoins1);
        userCoinsList.add(userCoins2);

    }

    @AfterEach
    public void tearDown() {
    	
        
        // Delete UserCoins entries after each test method
        for (UserCoins userCoins : userCoinsList) {
            userCoinsService.deleteUserCoins(userCoins.getUserId(), userCoins.getCoinSymbol());
        }
     // Clean up the userCoinsList after each test method
        userCoinsList.clear();

    }
    
    @Test
    public void testAddUserCoins() {
        // Arrange
        int userId = 1;
        String coinSymbol = "BTC";
        double quantity = 0.5;

        // Act
        userCoinsService.addUserCoins(userId, coinSymbol, quantity);

        // Assert
        List<UserCoins> userCoinsList = userCoinsService.getUserCoins(userId);
        assertNotNull(userCoinsList);
        assertEquals(1, userCoinsList.size());

        // Check if the added user coins match the expected values
        UserCoins userCoins = userCoinsList.get(0);
        assertEquals(userId, userCoins.getUserId());
        assertEquals(coinSymbol, userCoins.getCoinSymbol());
        assertEquals(quantity, userCoins.getQuantity());
    }
    
    @Test
    public void testDeleteUserCoins() {
        // Retrieve the sample data from the database before deletion
        List<UserCoins> retrievedUserCoinsListBeforeDeletion = userCoinsService.getUserCoins(userCoinsList.get(0).getUserId());
        // Perform deletion of UserCoins for the first user
        userCoinsService.deleteUserCoins(userCoinsList.get(0).getUserId(), userCoinsList.get(0).getCoinSymbol());
        // Retrieve the data again after deletion
        List<UserCoins> retrievedUserCoinsListAfterDeletion = userCoinsService.getUserCoins(userCoinsList.get(0).getUserId());
        // Assert that the deleted UserCoins entry is no longer in the list
        assertFalse(retrievedUserCoinsListAfterDeletion.contains(userCoinsList.get(0)));
        // Assert that the size of the list has reduced by one after deletion
        assertEquals(retrievedUserCoinsListBeforeDeletion.size(), retrievedUserCoinsListAfterDeletion.size());
    }
    
    @Test
    public void testUpdateUserCoins() {
        // Arrange
        int userId = 1;
        String coinSymbol = "BTC";
        double initialQuantity = 0.5;
        double newQuantity = 3.0;

        // Add the userCoins with initial quantity
        userCoinsService.addUserCoins(userId, coinSymbol, initialQuantity);

        // Act
        userCoinsService.updateUserCoins(userId, coinSymbol, newQuantity);

        // Assert
        double updatedQuantity = userCoinsService.getUserCoinsQuantity(userId, coinSymbol);
        assertEquals(newQuantity, updatedQuantity); 
    }
    @Test
    public void testGetUserCoins() {
        // create 2 coins with same used ID
        int userId = 1;
        String coinSymbol1 = "BTC";
        String coinSymbol2 = "ETH";
        double quantity1 = 0.5;
        double quantity2 = 2.0;

        // Add user coins for the given user ID
        userCoinsService.addUserCoins(userId, coinSymbol1, quantity1);
        userCoinsService.addUserCoins(userId, coinSymbol2, quantity2);

      
        List<UserCoins> userCoinsList = userCoinsService.getUserCoins(userId);

        // Assert
        assertNotNull(userCoinsList);
        assertEquals(2, userCoinsList.size());

        // Check if the retrieved user coins match the added ones
        UserCoins userCoins1 = userCoinsList.get(0);
        assertEquals(userId, userCoins1.getUserId());
        assertEquals(coinSymbol1, userCoins1.getCoinSymbol());
        assertEquals(quantity1, userCoins1.getQuantity());

        UserCoins userCoins2 = userCoinsList.get(1);
        assertEquals(userId, userCoins2.getUserId());
        assertEquals(coinSymbol2, userCoins2.getCoinSymbol());
        assertEquals(quantity2, userCoins2.getQuantity());
    }
    
    @Test
    public void testGetUserCoinsQuantity() {
        // Arrange
        int userId = 1;
        String coinSymbol = "BTC";

        // Add some sample user coins to the database for the given user and coin symbol
        userCoinsService.addUserCoins(userId, coinSymbol, 0.5);
        userCoinsService.addUserCoins(userId, coinSymbol, 0.3);

        // Act
        double totalQuantity = userCoinsService.getUserCoinsQuantity(userId, coinSymbol);

        // Assert
        assertEquals(0.5 + 0.3, totalQuantity, 0.0001); // Use delta for double comparison
    }

    @Test
    public void testUpdateUserCoins2() {
        // Arrange
        int userId = 1;
        String coinSymbol = "BTC";
        double initialQuantity = 0.5;
        double newQuantity = 0.8;

        // Add a sample user coin to the database for the given user and coin symbol
        userCoinsService.addUserCoins(userId, coinSymbol, initialQuantity);

        // Act
        userCoinsService.updateUserCoins(userId, coinSymbol, newQuantity);

        // Get the updated user coins from the database
        List<UserCoins> updatedUserCoinsList = userCoinsService.getUserCoins(userId);

        // Assert
        assertEquals(1, updatedUserCoinsList.size()); // There should be only one user coins entry
        assertEquals(newQuantity, updatedUserCoinsList.get(0).getQuantity(), 0.0001); // Use delta for double comparison
    }
    






}
    
	
    
   