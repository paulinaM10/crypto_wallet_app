package com.teamtwo.model.service;
import com.teamtwo.dto.entity.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.teamtwo.dto.entity.CoinApiResponse;
import com.teamtwo.model.persistence.UserCoinsDao;

@Service
public class CoinServiceImpl implements CoinService {
	
	
	
	@Autowired
	private UserCoinsDao userCoinsDao;

	public void addUserCoins(int userId, String coinSymbol, double quantity) {
        List<UserCoins> userCoins = userCoinsDao.findByUserIdAndCoinSymbol(userId, coinSymbol);
        
        if (!userCoins.isEmpty()) {
            // Existing records found - update their quantities
            for (UserCoins userCoin : userCoins) {
                double newQuantity = userCoin.getQuantity() + quantity;
                userCoin.setQuantity(newQuantity);
                userCoinsDao.save(userCoin);
            }
        } else {
            // No existing records found - create a new one
            UserCoins newUserCoin = new UserCoins();
            newUserCoin.setUserId(userId);
            newUserCoin.setCoinSymbol(coinSymbol);
            newUserCoin.setQuantity(quantity);
            userCoinsDao.save(newUserCoin);
        }
	}
    @Override
    public double getCoinPrice(String coinSymbol) {
        String url = "https://api4.binance.com/api/v3/ticker/bookTicker?symbol=" + coinSymbol;
        RestTemplate restTemplate = new RestTemplate();
        CoinApiResponse coinApiResponse = restTemplate.getForObject(url, CoinApiResponse.class);
        // Convert bidPrice (which is a String) to a double
        return Double.parseDouble(coinApiResponse.getBidPrice());
    }
    @Override
    public double getCoinAskPrice(String coinSymbol) {  
        String url = "https://api4.binance.com/api/v3/ticker/bookTicker?symbol=" + coinSymbol;
        RestTemplate restTemplate = new RestTemplate();
        CoinApiResponse coinApiResponse = restTemplate.getForObject(url, CoinApiResponse.class);
        // Convert askPrice (which is a String) to a double
        return Double.parseDouble(coinApiResponse.getAskPrice());
    }
}
