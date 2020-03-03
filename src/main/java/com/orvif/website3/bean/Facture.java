/**
 * 
 */
package com.orvif.website3.bean;

import java.io.Serializable;

/**
 * @author Marijon Etienne
 *
 */
public class Facture implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6787315252955342903L;
	private String numero;
	private String date;
	private String modeReglement;
	private String echeance;
	private String numeroReleve;
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
	 * @return the modeReglement
	 */
	public String getModeReglement() {
		return modeReglement;
	}
	/**
	 * @param modeReglement the modeReglement to set
	 */
	public void setModeReglement(String modeReglement) {
		this.modeReglement = modeReglement;
	}
	/**
	 * @return the echeance
	 */
	public String getEcheance() {
		return echeance;
	}
	/**
	 * @param echeance the echeance to set
	 */
	public void setEcheance(String echeance) {
		this.echeance = echeance;
	}
	/**
	 * @return the numeroReleve
	 */
	public String getNumeroReleve() {
		return numeroReleve;
	}
	/**
	 * @param numeroReleve the numeroReleve to set
	 */
	public void setNumeroReleve(String numeroReleve) {
		this.numeroReleve = numeroReleve;
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
