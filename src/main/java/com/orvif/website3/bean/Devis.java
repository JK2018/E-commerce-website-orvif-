package com.orvif.website3.bean;

import java.io.Serializable;

/**
 * @author Marijon Etienne
 *
 */
public class Devis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549801233801529927L;
	private String numero;
	private String date;
	private String finValidite;
	private String refClient;
	private String acheteur;
	private float montantTTC;
	private float aRegler;
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the finValidite
	 */
	public String getFinValidite() {
		return finValidite;
	}
	/**
	 * @param finValidite the finValidite to set
	 */
	public void setFinValidite(String finValidite) {
		this.finValidite = finValidite;
	}
	/**
	 * @return the refClient
	 */
	public String getRefClient() {
		return refClient;
	}
	/**
	 * @param refClient the refClient to set
	 */
	public void setRefClient(String refClient) {
		this.refClient = refClient;
	}
	/**
	 * @return the acheteur
	 */
	public String getAcheteur() {
		return acheteur;
	}
	/**
	 * @param acheteur the acheteur to set
	 */
	public void setAcheteur(String acheteur) {
		this.acheteur = acheteur;
	}
	/**
	 * @return the montantTTC
	 */
	public float getMontantTTC() {
		return montantTTC;
	}
	/**
	 * @param montantTTC the montantTTC to set
	 */
	public void setMontantTTC(float montantTTC) {
		this.montantTTC = montantTTC;
	}
	/**
	 * @return the aRegler
	 */
	public float getaRegler() {
		return aRegler;
	}
	/**
	 * @param aRegler the aRegler to set
	 */
	public void setaRegler(float aRegler) {
		this.aRegler = aRegler;
	}
	
	
}
