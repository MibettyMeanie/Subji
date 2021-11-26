package com.Subji.webservice.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Subji.webservice.entities.IndividualSubscription;
import com.Subji.webservice.entities.PaymentDetails;
import com.Subji.webservice.entities.Subscription;
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
	
	@PostMapping("/subscription")
	public PaymentDetails addSubscription(@RequestBody 
			SubscriptionDetails subscriptionDetails) throws URISyntaxException {
		return subscriptionService.addSubscription(subscriptionDetails);
	}
	
	@GetMapping("/user/{userName}")
	public List<Subscription>  getAllSubscription(@PathVariable String userName) {
		return subscriptionService.getAllSubscription(userName);
	}
	
	@GetMapping("/user/{userName}/{date}")
	public IndividualSubscription  getSubscriptionByDate(@PathVariable String userName,@PathVariable LocalDate date) {
		return subscriptionService.getSubscriptionByDate(userName,date);
	}
	
}
