package com.Subji.webservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Subji.webservice.entities.User;
import com.Subji.webservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository ;

	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public void addUser(String userName) {
		User user = new User();
		user.setUserName(userName);
		user.setCreatedAt(new Date());
		userRepository.save(user);
	}

}
 