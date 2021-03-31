package com.thang.service;

import java.util.List;
import java.util.Optional;

import com.thang.model.User;

public interface IUserService {
	User registerNewUserAccount(User user);
	
	User findByEmail(String email);
	
	List<User> findAll();
	
	Optional<User> findById(int id);
	
	void saveUser(User user);
}
