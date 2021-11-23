package com.Subji.webservice.service;

import com.Subji.webservice.entities.User;

public interface UserService {
	
	public User getUser(String userName);

	public void addUser(String userName);

}
