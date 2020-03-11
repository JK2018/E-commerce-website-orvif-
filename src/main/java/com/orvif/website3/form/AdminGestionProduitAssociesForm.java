/**
 * 
 */
package com.orvif.website3.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.orvif.website3.Entity.Produits;
import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.Repository.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Marijon Etienne
 *
 */
public class AdminGestionProduitAssociesForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private ProduitsRepository produitDao;
	@Autowired
	private ProduitsHelper ph;

	private final String champ_action = "action";
	private final String champ_comp_ou_simi = "compOuSimi";
	private final String champ_valeur = "val";
	private final String champ_id_produit_base = "productBase";
	private final String champ_id_produit_to_add = "productsToAdd[]";

	public AdminGestionProduitAssociesForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public String gererRequete(HttpServletRequest request) {
		String action = getValeurChamp(request, champ_action);
		if (action != null) {
			if (action.equals("searchByCle")) {
				String json = this.getProduitByCle(request);
				return json;
			} else if (action.equals("searchByText")) {
				String json = this.getProduitBySearch(request);
				return json;
			} else if (action.equals("addProdComp")) {
				String json = this.ajouterProduitComplementaire(request);
				return json;
			} else if (action.equals("addProdAssocie")) {
				String json = this.ajouterProduitAlternatif(request);
				return json;
			} else if (action.equals("removeSimilar")) {
				String json = this.removeProduitAssocie(request,"similaire");
				return json;
			} else if (action.equals("removeComplementary")) {
				String json = this.removeProduitAssocie(request,"complementaire");
				return json;
			} else if(action.equals("removeProduct")){
				String json = this.removeProduit(request);
				return json;
			}else {
				return "";
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	private String removeProduit(HttpServletRequest request) {
		String idProduit = getValeurChamp(request, champ_valeur);
		if(idProduit != null && checkInteger(idProduit)) {
			try {
				ph.removeProduct(Integer.parseInt(idProduit));
				Map<String, String> json = new HashMap<String, String>();
				return new Gson().toJson(json);
			}catch (Exception e) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue. Veuillez réessayer.");
				return new Gson().toJson(json);
			}
		}else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}
	
	private String removeProduitAssocie(HttpServletRequest request, String typeSuppression) {
		String base = getValeurChamp(request, champ_id_produit_base);
		String toRemove = getValeurChamp(request, champ_valeur);
		
		if(base != null && toRemove != null && checkInteger(base) && checkInteger(toRemove) && typeSuppression != null && (typeSuppression.equals("complementaire") || typeSuppression.equals("similaire") )) {
			boolean result = (typeSuppression.equals("similaire")) ? ph.removeProdAssocie(Integer.parseInt(base), Integer.parseInt(toRemove)) : ph.removeProdComplementaire(Integer.parseInt(base), Integer.parseInt(toRemove));
			Map<String,String> json = new HashMap<String,String>();
			if(!result) {
				json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
			}
			return new Gson().toJson(json);
		}else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}
	
	private String getProduitBySearch(HttpServletRequest request) {
		String search = getValeurChamp(request, champ_valeur);
		String productBase = getValeurChamp(request, champ_id_produit_base);
		String compOuSimi = getValeurChamp(request, champ_comp_ou_simi);
		if (search != null && !search.trim().equals("") && compOuSimi != null && checkInteger(productBase)) {
			List<Produits> produitCollection = ph.getBySearchProduitAssocie(Integer.parseInt(productBase),
					search, compOuSimi,"00001");
			if (produitCollection == null) {
				Map<String, String> json = new HashMap<>();
				json.put("error", "Produit non trouvé.");
				return new Gson().toJson(json);
			} else {
				Map<String, List<Produits>> json = new HashMap<>();
				json.put("produits", produitCollection);
				return new Gson().toJson(json);
			}
		} else {
			Map<String, String> json = new HashMap<>();
			json.put("error", "Veuillez entrer une valeur à rechercher.");
			return new Gson().toJson(json);
		}
	}

	private String getProduitByCle(HttpServletRequest request) {
		String cle = getValeurChamp(request, champ_valeur);
		if (checkInteger(cle)) {
			try {
				Produits p = produitDao.getByCleSysteme(Integer.parseInt(cle));
				if (p == null) {
					Map<String, String> json = new HashMap<String, String>();
					json.put("error", "Produit non trouvé.");
					return new Gson().toJson(json);
				} else {
					Map<String, Produits> json = new HashMap<String, Produits>();
					json.put("produit", p);
					return new Gson().toJson(json);
				}
			} catch (Exception e) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue. Veuillez réessayer.");
				return new Gson().toJson(json);
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	private String ajouterProduitComplementaire(HttpServletRequest request) {
		String produitBase = getValeurChamp(request, champ_id_produit_base);
		String[] produitAAjouter = getValeurChampMultiple(request, champ_id_produit_to_add);
		List<Integer> idProds = new ArrayList<Integer>();
		boolean allInt = true;
		for (int i = 0; i < produitAAjouter.length; i++) {
			if (!checkInteger(produitAAjouter[i])) {
				allInt = false;
				break;
			}
			idProds.add(Integer.parseInt(produitAAjouter[i]));
		}

		if (checkInteger(produitBase) && allInt) {
			try {
				if (!ph.addSeveralProdComplementaire(Integer.parseInt(produitBase), idProds)) {
					Map<String, String> json = new HashMap<String, String>();
					json.put("error", "Une erreur est survenue. Veuillez réessayer.");
					return new Gson().toJson(json);
				}
				return "{}";
			} catch (Exception e) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue. Veuillez réessayer.");
				return new Gson().toJson(json);
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	private String ajouterProduitAlternatif(HttpServletRequest request) {
		String produitBase = getValeurChamp(request, champ_id_produit_base);
		String[] produitAAjouter = getValeurChampMultiple(request, champ_id_produit_to_add);
		List<Integer> idProds = new ArrayList<Integer>();
		boolean allInt = true;
		for (int i = 0; i < produitAAjouter.length; i++) {
			if (!checkInteger(produitAAjouter[i])) {
				allInt = false;
				break;
			}
			idProds.add(Integer.parseInt(produitAAjouter[i]));
		}

		if (checkInteger(produitBase) && allInt) {
			try {
				if (!ph.addSeveralProdSimilaire(Integer.parseInt(produitBase), idProds)) {
					Map<String, String> json = new HashMap<String, String>();
					json.put("error", "Une erreur est survenue. Veuillez réessayer.");
					return new Gson().toJson(json);
				}
				return "{}";
			} catch (Exception e) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue. Veuillez réessayer.");
				return new Gson().toJson(json);
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}
}
