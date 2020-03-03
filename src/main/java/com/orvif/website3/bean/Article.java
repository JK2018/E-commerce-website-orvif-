package com.orvif.website3.bean;

import com.orvif.website3.Entity.*;

import java.io.Serializable;
import java.util.List;

public class Article implements Serializable {
	private int id;
	private String libelle;
	private String libelleUrl;
	private String description;
	private String avantages;
	private Familles famille;
	private SsFamilles sousFamille;
	private Categories categorie;
	private SsCategories sousCategorie;
	private Marques marque;
	private Gammes gamme;
	private boolean visible;
	private List<Reference> referenceList;

	public Article() {
	}

	public String getLibelleUrl() {
		return libelleUrl;
	}

	public void setLibelleUrl(String libelleUrl) {
		this.libelleUrl = libelleUrl;
	}

	public String getAvantages() {
		return avantages;
	}

	public void setAvantages(String avantages) {
		this.avantages = avantages;
	}

	public List<Reference> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Familles getFamille() {
		return famille;
	}

	public void setFamille(Familles famille) {
		this.famille = famille;
	}

	public SsFamilles getSousFamille() {
		return sousFamille;
	}

	public void setSousFamille(SsFamilles sousFamille) {
		this.sousFamille = sousFamille;
	}

	public Categories getCategorie() {
		return categorie;
	}

	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	}

	public SsCategories getSousCategorie() {
		return sousCategorie;
	}

	public void setSousCategorie(SsCategories sousCategorie) {
		this.sousCategorie = sousCategorie;
	}

	public Marques getMarque() {
		return marque;
	}

	public void setMarque(Marques marque) {
		this.marque = marque;
	}

	public Gammes getGamme() {
		return gamme;
	}

	public void setGamme(Gammes gamme) {
		this.gamme = gamme;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
