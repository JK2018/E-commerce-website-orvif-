package com.orvif.website3.bean;

import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.Panier;
import com.orvif.website3.Entity.Utilisateurs;

import java.io.Serializable;

/**
 * Bean construit tout au long du passage de la commande
 */
public abstract class CommandeProcess implements Serializable {
	protected Utilisateurs customer;
	protected Panier cart;
	protected Adresse billAddress;
	protected boolean creditPaymentAvailable;
	protected boolean creditEnough;

	public Utilisateurs getCustomer() {
		return customer;
	}

	public void setCustomer(Utilisateurs customer) {
		this.customer = customer;
	}

	public Panier getCart() {
		return cart;
	}

	public void setCart(Panier cart) {
		this.cart = cart;
	}

	public Adresse getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(Adresse billAddress) {
		this.billAddress = billAddress;
	}

	public boolean isCreditPaymentAvailable() {
		return creditPaymentAvailable;
	}

	public void setCreditPaymentAvailable(boolean creditPaymentAvailable) {
		this.creditPaymentAvailable = creditPaymentAvailable;
	}

	public boolean isCreditEnough() {
		return creditEnough;
	}

	public void setCreditEnough(boolean creditEnough) {
		this.creditEnough = creditEnough;
	}
}
