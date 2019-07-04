package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.User;

public interface UserService {
	// User getById(Long id);
	// User getByEmailAndPassword(String email, String password);
	User getByEmail(String email);
	List<User> usersByAnswer(Long answerId);
	
	void save(User user);
	void update(User user);
	boolean exist(Long id);
	String getUserNameById(Long id);
}