package com.orvif.website3.bean;

import com.orvif.website3.Entity.Adresse;

import java.io.Serializable;

public class CommandeDelivery extends CommandeProcess implements Serializable {
	private Adresse deliveryAddress;
	private float deliveryPrice;
	private String deliveryDate;
	private String deliveryWay;

	public Adresse getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Adresse deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public float getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(float deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
}
