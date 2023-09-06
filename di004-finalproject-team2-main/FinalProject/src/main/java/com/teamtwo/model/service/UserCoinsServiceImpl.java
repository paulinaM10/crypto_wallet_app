package com.teamtwo.model.service;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.teamtwo.dto.entity.UserCoins;
import com.teamtwo.dto.entity.UserCoinsList;
import com.teamtwo.model.persistence.UserCoinsDao;

@Service
public class UserCoinsServiceImpl implements UserCoinsService {
	@Autowired
	private UserCoinsDao userCoinsDao;

	public void addUserCoins(int userId, String coinSymbol, double quantity) {
	    UserCoins userCoins = new UserCoins();
	    userCoins.setUserId(userId);
	    userCoins.setCoinSymbol(coinSymbol);
	    userCoins.setQuantity(quantity);
	    
	    userCoinsDao.save(userCoins);
	}


	

	@Override
	public void updateUserCoins(UserCoins userCoins) {
		userCoinsDao.save(userCoins);
	}
	
	
	@Override
	public double getUserCoinsQuantity(int userId, String coinSymbol) {
	    List<UserCoins> userCoinsList = userCoinsDao.findByUserIdAndCoinSymbol(userId, coinSymbol);
	    double totalQuantity = 0.0;

	    for(UserCoins userCoins : userCoinsList) {
	        totalQuantity += userCoins.getQuantity();
	    }

	    return totalQuantity;
	}

	@Override
	public void updateUserCoins(int userId, String coinSymbol, double newQuantity) {
	    List<UserCoins> userCoinsList = userCoinsDao.findByUserIdAndCoinSymbol(userId, coinSymbol);

	    for(UserCoins userCoins : userCoinsList) {
	        userCoins.setQuantity(newQuantity);
	        userCoinsDao.save(userCoins);
	    }
	}

	@Override
    public List<UserCoins> getUserCoins(int userId) {
        return userCoinsDao.findByUserId(userId);
    }




	@Override
	public void deleteUserCoins(int userId, String coinSymbol) {
		List<UserCoins> userCoinsList = userCoinsDao.findByUserIdAndCoinSymbol(userId, coinSymbol);

        for (UserCoins userCoins : userCoinsList) {
            userCoinsDao.delete(userCoins);
        }
    }
	
}