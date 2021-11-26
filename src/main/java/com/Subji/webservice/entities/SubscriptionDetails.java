package com.Subji.webservice.entities;

import java.time.LocalDate;


public class SubscriptionDetails {
	
	private String userName;
	private String planId;
	private LocalDate startDate;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

}
