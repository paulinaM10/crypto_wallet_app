package com.teamtwo.model.service;
import java.util.List;
import com.teamtwo.dto.entity.User;
import com.teamtwo.dto.entity.Wallet;

public interface UserService {

	User createUser(User user);
	User updateUser(User user);
	User getUserById(int userId);
	List<User> getAllUsers();
	public User getUserByEmail(String email);
	Wallet getUserWallet(int userId);
  public User updateUser(int userId, User updatedUser);
	public boolean deleteUser(int userId);

}