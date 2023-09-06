package com.teamtwo.model.service;

import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;
import com.teamtwo.model.persistence.UserDao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    UserDao userDao;
	
	 @Autowired
	    private WalletService walletService;
	
	private String hashPassword(String password) {
		    return BCrypt.hashpw(password, BCrypt.gensalt());
		}

	
	@Override
	 public Wallet getUserWallet(int userId) {
	        Optional<User> userOptional = userDao.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            return user.getWallet();
	        }
	        return null;
	 }

    @Override
    public User createUser(User user) {
        user.setUserPassword(hashPassword(user.getUserPassword()));
        User savedUser = userDao.save(user);
        Wallet wallet = walletService.createUserWallet(savedUser);
        savedUser.setWallet(wallet);
        return userDao.save(savedUser);
    }

    @Override
    public User updateUser(User user) {
        user.setUserPassword(hashPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    @Override
    public User getUserById(int userId) {
        // Fetch the user from the database.
        return userDao.findById(userId).orElse(null);
    }

	@Override
	public List<User> getAllUsers() {
        return userDao.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email).orElse(null);	
	}


	@Override
    public User updateUser(int userId, User updatedUser) {
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setBankDetails(updatedUser.getBankDetails());
            return userDao.save(user);
        } else {
            return null;
        }
    }


	@Override
	public boolean deleteUser(int userId) {
		 if (userDao.existsById(userId)) {
	            userDao.deleteById(userId);
	            return true;
	        } else {
	            return false;
	        }
	    }
	 
}