package com.Subji.webservice.service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import com.Subji.webservice.entities.IndividualSubscription;
import com.Subji.webservice.entities.PaymentDetails;
import com.Subji.webservice.entities.Subscription;
import com.Subji.webservice.entities.SubscriptionDetails;

public interface SubscriptionService {
	
	public PaymentDetails addSubscription(SubscriptionDetails subscriptionDetails) throws URISyntaxException;

	public List<Subscription> getAllSubscription(String userName);

	public IndividualSubscription getSubscriptionByDate(String userName, LocalDate date);

}
