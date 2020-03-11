package com.orvif.website3.form;



import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.CaracteristiquesHelper;
import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.bean.DisplayListProd;
import com.orvif.website3.service.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class RechercheForm extends Form {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DAOFactory daoFactory;

	@Autowired
	private DisplayListProd itemsToDisplay;

	@Autowired
	private ProduitsHelper produitDao;
	@Autowired
	private CaracteristiquesHelper caracteristiqueDao;

	private final String champ_recherche = "search";
	private String cleClient;
	private String result;
	private String champ_num_page = "p";
	private String numPageGet;
	private String order = "libelle";
	private Map<String, String> errors = new HashMap<String, String>();


	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public RechercheForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}

	/**
	 * Recherche les produit par rapport au texte en tenant compte de la pagination et des filtres
	 *
	 * @param request
	 * @return
	 */
	public void rechercher(HttpServletRequest request) {
		String text = getValeurChamp(request, champ_recherche);

		if (text == null || text.equals("")) {
			errors.put("global", "Une erreur est survenue.");
		} else {
			request.setAttribute("search", text);
			try {
				int previousNbPerPage = 8;
				if (itemsToDisplay != null) {
					previousNbPerPage = itemsToDisplay.getNbPerPage();
				}

				itemsToDisplay = new DisplayListProd();
				checkConnexion(request);
				String[] words = text.split(" ");
				numPageGet = getValeurChamp(request, champ_num_page);

				//Check if order changed
				if (request.getParameter("order") != null && !order.equals(request.getParameter("order"))) {
					numPageGet = "1";
				}
				order = request.getParameter("order");
				String nbPerPageGet = request.getParameter("nbPage");

				if (nbPerPageGet != null) {
					try {
						int temp = Integer.valueOf(nbPerPageGet);
						if (previousNbPerPage != temp) {
							numPageGet = "1";
						}
						if (temp % 8 != 0 || temp > 64 || temp == 0) {
							itemsToDisplay.setNbPerPage(8);
						} else {
							itemsToDisplay.setNbPerPage(temp);
						}
					} catch (Exception e) {
						numPageGet = "1";
						itemsToDisplay.setNbPerPage(8);
					}
				} else {
					itemsToDisplay.setNbPerPage(8);
				}

				traitementFiltreApplique(request);
				if (order == null) {
					order = "libelle";
				}

				// Verification du numero de page
				int numPage;
				if (numPageGet != null) {
					try {
						numPage = Integer.parseInt(numPageGet);
						if (numPage == 0) {
							numPage = 1;
						}
					} catch (Exception e) {
						numPage = 1;
					}
				} else {
					numPage = 1;
				}

				//Verification de la tranche de prix
				if (request.getParameter("lPrice") != null && request.getParameter("hPrice") != null) {
					try {
						int minPrice = Integer.parseInt(request.getParameter("lPrice"));
						int maxPrice = Integer.parseInt(request.getParameter("hPrice"));
						itemsToDisplay.setMinMaxSelectedPrice(new Pair<>(minPrice, maxPrice));
					} catch (NumberFormatException e) {
						itemsToDisplay.setMinMaxPrice(null);
					}
				}

				itemsToDisplay.setPageNumber(numPage);
				itemsToDisplay.setOrder(order);
				produitDao.fillDisplayListItemsBySearch(itemsToDisplay, cleClient, words);


				itemsToDisplay.removeAppliedFilterFromAvailable();

				request.setAttribute("objectDisplay", itemsToDisplay);

				request.setAttribute("search", text);
			} catch (Exception e) {
				e.printStackTrace();
				errors.put("global", "Une erreur est survenue.");
			}
		}
	}

	/*
	 * Determine le numero de client pour les prix produit
	 * @param request
	 */
	private void checkConnexion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Utilisateurs client = (Utilisateurs) session.getAttribute("client");
		if (client != null) {
			if (client.getType_client().equals("particulier")) {
				itemsToDisplay.setProAsking(false);
			} else {
				itemsToDisplay.setProAsking(true);
			}
			this.cleClient = String.valueOf(client.getNumCli());
		} else {
			itemsToDisplay.setProAsking(false);
			this.cleClient = "00001";
		}
	}

	private void traitementFiltreApplique(HttpServletRequest request) {
		String[] paramCollection = request.getParameterValues("f");
		if (paramCollection != null) {
			for (String name : paramCollection) {
				String[] tmp = name.split("\\|");
				if (tmp.length == 2) {
					try {
						itemsToDisplay.addAppliedFilter(Integer.valueOf(tmp[0]), tmp[1], caracteristiqueDao);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
