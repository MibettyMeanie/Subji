package com.Subji.webservice.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.Subji.webservice.entities.RequestBody;
import com.Subji.webservice.entities.Response;
import com.Subji.webservice.entities.Subscription;
import com.Subji.webservice.entities.IndividualSubscription;
import com.Subji.webservice.entities.PaymentDetails;
import com.Subji.webservice.entities.SubscriptionDetails;
import com.Subji.webservice.entities.User;
import com.Subji.webservice.repository.SubscriptionRepository;
import com.Subji.webservice.repository.UserRepository;

import reactor.core.publisher.Mono;

public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private UserService userService;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Override
	public PaymentDetails addSubscription(SubscriptionDetails subscriptionDetails) throws URISyntaxException {
		
		Plans newSubscription = Plans.getByPlanId(subscriptionDetails.getPlanId());
		User user = userService.getUser(subscriptionDetails.getUserName());
		Integer amount = getAdjustmentForUpgradeOrDowngrade(user,newSubscription,subscriptionDetails.getStartDate());
		RequestBody paymentBody = new RequestBody();
		paymentBody.setUserName(subscriptionDetails.getUserName());
		paymentBody.setAmount(amount);
		if (amount < 0) {
			paymentBody.setPaymentType("DEBIT");
		} else {
			paymentBody.setPaymentType("CREDIT");
		}
		URI uri = new URI("https", "dummy-payment-server.herokuapp.com", "/payment", null);
		String request = uri.toASCIIString();
		Response responseBody = webClientBuilder.build().post().uri(request)
				.body(Mono.just(paymentBody), RequestBody.class).retrieve().bodyToMono(Response.class).block();
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setAmount(amount);
		if (responseBody.getStatus() == "SUCCESS") {
			persistPlanToDb(user,subscriptionDetails);
			paymentDetails.setStatus("SUCCESS");
		} else {
			paymentDetails.setStatus("FAILURE");
		}
		return paymentDetails;
	}

	private void persistPlanToDb(User user,SubscriptionDetails subscriptionDetails) {

	
		Subscription curr_subscription = getCurrentPlan(user, subscriptionDetails.getStartDate());
		if (null != curr_subscription) {
			curr_subscription.setValidTill(subscriptionDetails.getStartDate().minusDays(1));
			subscriptionRepository.save(curr_subscription);
		}
		Subscription subscription = new Subscription();
		subscription.setPlanId(subscriptionDetails.getPlanId());
		subscription.setStartDate(subscriptionDetails.getStartDate());
		Integer validity = Plans.getByPlanId(subscription.getPlanId()).getValidity();
		LocalDate validDate = subscriptionDetails.getStartDate().plusDays(validity);
		subscription.setValidTill(validDate);
		subscriptionRepository.save(subscription);
		user.getSubscriptions().add(subscription);
		userRepository.save(user);
	}

	private Subscription getCurrentPlan(User user, LocalDate startDate) {
		List<Subscription> subscriptions = user.getSubscriptions();
		for (Subscription subscription : subscriptions) {
			if (subscription.getStartDate().isBefore(startDate) && subscription.getValidTill().isAfter(startDate)) {
				return subscription;
			} else if (subscription.getStartDate().isEqual(startDate)) {
				return subscription;
			}
		}
		return null;
	}

	private Integer getAdjustmentForUpgradeOrDowngrade(User user, Plans newSubscription, LocalDate startDate) {
		Subscription current = getCurrentPlan(user,startDate);
		Plans currentSubscription = Plans.getByPlanId(current.getPlanId());
		if(null==currentSubscription||currentSubscription==Plans.FREE||currentSubscription==Plans.TRIAL) {
			return -newSubscription.getCost();
		}else {
			Integer noOfDaysLeftInPlan = current.getValidTill().compareTo(startDate);
			Integer residual = (currentSubscription.getCost()*noOfDaysLeftInPlan)/currentSubscription.getValidity();
			return residual-newSubscription.getCost();
		}
	
	}

	@Override
	public List<Subscription> getAllSubscription(String userName) {
		return userService.getUser(userName).getSubscriptions();
	}

	@Override
	public IndividualSubscription getSubscriptionByDate(String userName, LocalDate date) {
		IndividualSubscription current = new IndividualSubscription();
		User user = userService.getUser(userName);
		Subscription currentSub = getCurrentPlan(user,date);
		current.setPlanId(currentSub.getPlanId());
		current.setDaysLeft(currentSub.getValidTill().compareTo(date));
		return current;
	}

}
