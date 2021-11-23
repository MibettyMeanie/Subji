package com.Subji.webservice.service;

import java.util.HashMap;

public enum Plans {
	
	FREE("FREE",Integer.MAX_VALUE,0),TRIAL("TRIAL",7,0),LITE_1M("LITE_1M",30,100),PRO_1M("PRO_1M",30, 200),LITE_6M("LITE_6M",180,500),PRO_6M("PRO_6M",180,900);

	private Plans(String planId, Integer validity, Integer cost) {
		this.setPlanId(planId);
		this.setValidity(validity);
		this.setCost(cost);
	}
	
	private String planId;
	
	private Integer validity;
	
	private Integer cost;
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	private static final HashMap<String,Plans> map = new HashMap<>();
	
	static {
		for (final Plans plan : Plans.values()) {
			map.put(plan.getPlanId(), plan);
		}
	}
	
	public static Plans getByPlanId(String planId) {
		return map.get(planId);
	}

}
