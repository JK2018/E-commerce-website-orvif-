/**
 *
 */
package com.orvif.website3.bean;

import java.io.Serializable;

/**
 * @author Marijon Etienne
 */
public class Commande implements Serializable {

	private static final long serialVersionUID = 223030443494795558L;
	protected String numero;
	protected String date;
	protected String refClient;
	protected String acheteur;
	protected float montantHT;
	protected float montantTTC;
	protected String modeRecuperation;
	protected String numeroDevis;
	protected String status;
	protected float aRegler;

	public String getNumeroDevis() {
		return numeroDevis;
	}

	public void setNumeroDevis(String numeroDevis) {
		this.numeroDevis = numeroDevis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getaRegler() {
		return aRegler;
	}

	public void setaRegler(float aRegler) {
		this.aRegler = aRegler;
	}

	public float getMontantHT() {
		return montantHT;
	}

	public void setMontantHT(float montantHT) {
		this.montantHT = montantHT;
	}

	public String getModeRecuperation() {
		return modeRecuperation;
	}

	public void setModeRecuperation(String modeRecuperation) {
		this.modeRecuperation = modeRecuperation;
	}

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
}
