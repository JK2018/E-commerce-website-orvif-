package com.orvif.website3.bean;

import java.io.Serializable;
import java.util.Map;

public class CommandeShopRemoval extends CommandeProcess implements Serializable {
	private Map<Integer, String> agencyRemoval;
	private String removalDate;

	public Map<Integer, String> getAgencyRemoval() {
		return agencyRemoval;
	}

	public void setAgencyRemoval(Map<Integer, String> agencyRemoval) {
		this.agencyRemoval = agencyRemoval;
	}

	public String getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(String removalDate) {
		this.removalDate = removalDate;
	}
}
