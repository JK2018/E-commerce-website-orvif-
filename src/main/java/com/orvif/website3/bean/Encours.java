package com.orvif.website3.bean;

import java.io.Serializable;

public class Encours implements Serializable {
	private float totalEncours;
	private float totalCredit;
	private float totalCommande;
	private float totalRetard;

	public float getTotalEncours() {
		return totalEncours;
	}

	public void setTotalEncours(float totalEncours) {
		this.totalEncours = totalEncours;
	}

	public float getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(float totalCredit) {
		this.totalCredit = totalCredit;
	}

	public float getTotalCommande() {
		return totalCommande;
	}

	public void setTotalCommande(float totalCommande) {
		this.totalCommande = totalCommande;
	}

	public float getTotalRetard() {
		return totalRetard;
	}

	public void setTotalRetard(float totalRetard) {
		this.totalRetard = totalRetard;
	}
}
