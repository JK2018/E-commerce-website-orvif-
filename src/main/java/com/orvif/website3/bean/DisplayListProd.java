package com.orvif.website3.bean;


import com.orvif.website3.Entity.*;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;
import com.orvif.website3.Entity.helper.CaracteristiquesHelper;
import com.orvif.website3.Repository.CaracteristiquesRepository;
import com.orvif.website3.service.Pair;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayListProd implements Serializable, Cloneable {

	public Pair<Integer, Integer> getMinMaxSelectedPrice() {
		return minMaxSelectedPrice;
	}

	public void setMinMaxSelectedPrice(Pair<Integer, Integer> minMaxSelectedPrice) {
		this.minMaxSelectedPrice = minMaxSelectedPrice;
	}

	public boolean isProAsking() {
		return proAsking;
	}

	public void setProAsking(boolean proAsking) {
		this.proAsking = proAsking;
	}

	public class Filter implements Serializable, Cloneable {
		private int idFeature;
		private String title;
		private List<Pair<String, String>> items = new ArrayList<>();

		public int getIdFeature() {
			return idFeature;
		}

		public void setIdFeature(int idFeature) {
			this.idFeature = idFeature;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<Pair<String, String>> getItems() {
			return items;
		}

		public void setItems(List<Pair<String, String>> items) {
			this.items = items;
		}

		@Override
		public Filter clone() throws CloneNotSupportedException {
			Filter obj = (Filter) super.clone();
			obj.setItems(new ArrayList<>(items));
			return obj;
		}
	}


	private int pageNumber;
	private int nbResult;
	private int nbPerPage;
	private int lastPage;
	private String order;
	private int nbCurrentPage;

	private Familles famille;
	private SsFamilles sousFamille;
	private Categories categorie;
	private SsCategories sousCategorie;

	private String linkPreviousPage;
	private String linkNextPage;

	private Pair<Integer, Integer> minMaxPrice;
	private Pair<Integer, Integer> minMaxSelectedPrice;

	private List<Filter> availableFilters = new ArrayList<>();
	private List<Filter> appliedFilters = new ArrayList<>();

	private List<Produits> productList = new ArrayList<>();
	private List<Groupe> groupeList = new ArrayList<>();

	//Permet de savoir si la requete concerne un pro ou non (principalement pour les prix HT/TTC et le filtre de tranche de prix)
	private boolean proAsking;

	public DisplayListProd() {
	}


	public List<Groupe> getGroupeList() {
		return groupeList;
	}

	public void setGroupeList(List<Groupe> groupeList) {
		this.groupeList = groupeList;
	}

	public Pair<Integer, Integer> getMinMaxPrice() {
		return minMaxPrice;
	}

	public int getNbCurrentPage() {
		return nbCurrentPage;
	}

	public void setNbCurrentPage(int nbCurrentPage) {
		this.nbCurrentPage = nbCurrentPage;
	}

	public void setMinMaxPrice(Pair<Integer, Integer> minMaxPrice) {
		this.minMaxPrice = minMaxPrice;
	}

	public List<Produits> getProductList() {
		return productList;
	}

	public void setProductList(List<Produits> productList) {
		this.productList = productList;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getNbResult() {
		return nbResult;
	}

	public void setNbResult(int nbResult) {
		this.nbResult = nbResult;
	}

	public int getNbPerPage() {
		return nbPerPage;
	}

	public void setNbPerPage(int nbPerPage) {
		this.nbPerPage = nbPerPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
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

	public String getLinkPreviousPage() {
		return linkPreviousPage;
	}

	public void setLinkPreviousPage(String linkPreviousPage) {
		this.linkPreviousPage = linkPreviousPage;
	}

	public String getLinkNextPage() {
		return linkNextPage;
	}

	public void setLinkNextPage(String linkNextPage) {
		this.linkNextPage = linkNextPage;
	}

	public List<Filter> getAvailableFilters() {
		return availableFilters;
	}

	public void setAvailableFilters(List<Filter> availableFilters) {
		this.availableFilters = availableFilters;
	}

	public List<Filter> getAppliedFilters() {
		return appliedFilters;
	}

	public void setAppliedFilters(List<Filter> appliedFilters) {
		this.appliedFilters = appliedFilters;
	}

	private int getFilterAvailableIndexByIdFeature(int id) throws ClassNotFoundException {
		for (Filter f : availableFilters) {
			if (f.getIdFeature() == id) {
				availableFilters.indexOf(f);
			}
		}
		throw new ClassNotFoundException();
	}

	private int getFilterAppliedIndexByIdFeature(int id) throws ClassNotFoundException {
		for (Filter f : appliedFilters) {
			if (f.getIdFeature() == id) {
				return appliedFilters.indexOf(f);
			}
		}
		throw new ClassNotFoundException();
	}




	public void addAppliedFilter(int idFeature, String value, CaracteristiquesHelper dao) {
		try {
			int index = getFilterAppliedIndexByIdFeature(idFeature);
			//TODO get number of products available with this value
			appliedFilters.get(index).getItems().add(new Pair<String, String>(value, "X"));
		} catch (ClassNotFoundException nf) {
			Caracteristiques c = dao.findByIdCustom(idFeature);
			Filter f = new Filter();
			f.setIdFeature(idFeature);
			f.setTitle(c.getLibelle());
			f.getItems().add(new Pair<String, String>(value, "X"));
			appliedFilters.add(f);
		}
	}




	public void addAvailableFilter(Caracteristiques c) {
		Filter f = new Filter();
		f.setIdFeature(c.getIdCaracteristiques());
		f.setTitle(c.getLibelle());
		for (String value : c.getValeurCollection()) {
			f.getItems().add(new Pair<>(value, "X"));
		}
		availableFilters.add(f);
	}





	public void removeAppliedFilterFromAvailable() {
		if (!appliedFilters.isEmpty()) {
			for (Filter f : appliedFilters) {
				for (Filter f2 : availableFilters) {
					if (f2.getIdFeature() == f.getIdFeature()) {
						List<Pair> pairToRemove = new ArrayList<>();
						for (Pair<String, String> p : f.getItems()) {
							for (Pair<String, String> p2 : f2.getItems()) {
								if (p.getLeft().equals(p2.getLeft())) {
									pairToRemove.add(p2);
								}
							}
						}
						for (Pair p3 : pairToRemove) {
							f2.getItems().remove(p3);
						}
					}
				}
			}
		}
	}

	@Override
	public DisplayListProd clone() throws CloneNotSupportedException {
		DisplayListProd obj = (DisplayListProd) super.clone();
		List<Filter> newAppliedFilters = new ArrayList<>();
		List<Filter> newAvailableFilters = new ArrayList<>();
		for (Filter f : this.appliedFilters) {
			newAppliedFilters.add(f.clone());
		}
		for (Filter f : this.availableFilters) {
			newAvailableFilters.add(f.clone());
		}
		obj.setAppliedFilters(newAppliedFilters);
		obj.setAvailableFilters(newAvailableFilters);
		return obj;
	}
}
