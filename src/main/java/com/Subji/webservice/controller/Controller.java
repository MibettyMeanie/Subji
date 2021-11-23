package com.Subji.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Subji.webservice.entities.PaymentDetails;
import com.Subji.webservice.entities.SubscriptionDetails;
import com.Subji.webservice.entities.User;
import com.Subji.webservice.service.SubscriptionService;
import com.Subji.webservice.service.UserService;

@RestController
public class Controller {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubscriptionService subscriptionService;

	@GetMapping("/user/{userName}")
	public User getUser(@PathVariable String userName) {
		return userService.getUser(userName);
	}

	@PutMapping("/user/{userName}")
	public ResponseEntity<?> addUser(@PathVariable String userName) {

		try {
			userService.addUser(userName);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	public PaymentDetails addSubscription(@RequestBody SubscriptionDetails subscriptionDetails) {
		
		return subscriptionService.addSubscription(subscriptionDetails);
	}
}
