/**
 *
 */
package com.orvif.website3.form;



import com.orvif.website3.Entity.LignePanier;
import com.orvif.website3.Entity.Panier;
import com.orvif.website3.Entity.Produits;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.PanierHelper;
import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Marijon Etienne
 */
public class GestionPanierForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private ProduitsHelper produitDao;
	@Autowired
	private UtilisateursHelper userDao;
	@Autowired
	private PanierHelper panierDao;

	private Panier panier;
	private Map<String, String> dataToReturn = new HashMap<>();
	private boolean connected;
	private final String champ_produit = "idProduit";
	private final String champ_nombre_produit = "nb";
	private final String champ_choix_panier = "action";
	private String cleClient;
	private String result;
	private Map<String, String> errors = new HashMap<>();

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * @param daoFac
	 */
	public GestionPanierForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}


	/**
	 * @return
	 */
	public Map<String, String> getDataToReturn() {
		return dataToReturn;
	}

	/**
	 * @param dataToReturn
	 */
	public void setDataToReturn(Map<String, String> dataToReturn) {
		this.dataToReturn = dataToReturn;
	}

	/**
	 * Ajouter un produit au panier
	 *
	 * @param request
	 */
	public void ajouterProduit(HttpServletRequest request) {
		try {
			checkConnexion(request);
			HttpSession session = request.getSession();
			if ((this.panier = (Panier) session.getAttribute("panier")) == null) {
				createPanier(request);
			}
			int idProduit = Integer.parseInt(getValeurChamp(request, champ_produit));
			int nb = Integer.parseInt(getValeurChamp(request, champ_nombre_produit));
			Produits p = produitDao.getById(idProduit, cleClient);
			try {
				panier.addProduit(p, nb);
			} catch (Panier.NotEnoughStockException e) {
				errors.put("global", "Le produit n'a pas pu &ecirc;tre ajout&eacute; en raison d'un stock insuffisant.");
				return;
			}
			if (connected) {
				try {
					panierDao.addProduit(this.panier.getId(), p.getIdProduits(), nb);
				} catch (Exception e) {
					errors.put("global", "Une erreur est survenue.");
				}
			}
			dataToReturn.put("product", p.toJson());
			dataToReturn.put("quantity", String.valueOf(nb));
			session.setAttribute("panier", panier);
		} catch (DAOException de) {
			Logger.getLogger(this.getClass().getName()).warning("DAOException when adding product in cart [GestionPanierForm.java:96] : " + de.getMessage());
			errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer.");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).warning(e.getClass().getName() + " when adding product in cart [GestionPanierForm.java:99]");
			errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer.");
		}
	}

	/**
	 * Supprime un produit du panier
	 *
	 * @param request
	 */
	public void supprimerProduit(HttpServletRequest request) {
		checkConnexion(request);
		HttpSession session = request.getSession();
		this.panier = (Panier) session.getAttribute("panier");
		String idProduit = getValeurChamp(request, champ_produit);
		if (checkInteger(idProduit)) {
			int produit = Integer.parseInt(idProduit);
			this.panier.removeProduit(produit);
			if (this.connected) {
				try {
					panierDao.removeLignePanier(this.panier.getId(), produit);
				} catch (Exception e) {
					errors.put("global", "Une erreur est survenue.");
				}
			}
			if (this.panier.getLignePaniersById().isEmpty()) {
				this.supprimerPanier(request);
				dataToReturn.put("nbProductInCart", "");
			} else {
				dataToReturn.put("newGlobalPriceTTC", String.valueOf(panier.getPrixTotalTTC()));
				dataToReturn.put("newGlobalPriceHT", String.valueOf(panier.getPrixTotal()));
				dataToReturn.put("nbProductInCart", String.valueOf(panier.getNbProduit()));
				session.setAttribute("panier", this.panier);
			}
		} else {
			errors.put("global", "Une erreur est survenue, veuillez r&eacute;essayer.");
		}
	}

	/**
	 * Supprime completement le panier
	 *
	 * @param request
	 */
	public void supprimerPanier(HttpServletRequest request) {
		checkConnexion(request);
		HttpSession session = request.getSession();
		if (this.connected) {
			try {
				panierDao.removePanier(((Panier) session.getAttribute("panier")).getId());
			} catch (Exception e) {
				errors.put("global", "Une erreur est survenue.");
			}
		}
		session.removeAttribute("panier");
	}

	/**
	 * Change la quantite d'un produit dans le panier
	 *
	 * @param request
	 */
	public void changerQuantiteProduit(HttpServletRequest request) {
		checkConnexion(request);
		HttpSession session = request.getSession();
		this.panier = (Panier) session.getAttribute("panier");
		String idProduit = getValeurChamp(request, champ_produit);
		String qteProduit = getValeurChamp(request, champ_nombre_produit);
		if (checkInteger(idProduit) && checkInteger(qteProduit)) {
			int produit = Integer.parseInt(idProduit);
			int qte = Integer.parseInt(qteProduit);
			try {
				panier.changerQuantite(produit, qte);
			} catch (Panier.NotEnoughStockException e) {
				errors.put("global", "Quantit&eacute; non modifi&eacutee, le stock est insuffisant.");
			}
			if (this.connected) {
				try {
					//this.panierDao = daoFactory.getPanierDao();
					panierDao.updateQuantityLigne(this.panier.getId(), produit, qte);
				} catch (Exception e) {
					errors.put("global", "Une erreur est survenue.");
				}
			}
			for (LignePanier l : panier.getLignePaniersById()) {
				if (l.getProduitsByIdProduit().getIdProduits() == produit) {
					dataToReturn.put("newPriceHT", String.valueOf(l.getPrix()));
					dataToReturn.put("newPriceTTC", String.valueOf(l.getPrixTTC()));
				}
				dataToReturn.put("newGlobalPriceHT", String.valueOf(panier.getPrixTotal()));
				dataToReturn.put("newGlobalPriceTTC", String.valueOf(panier.getPrixTotalTTC()));
				dataToReturn.put("nbProductInCart", String.valueOf(panier.getNbProduit()));
			}
			session.setAttribute("panier", this.panier);
		} else {
			errors.put("global", "id produit ou quantité non valide");
		}
	}

	/**
	 * Determine le numero de client pour les prix produit
	 *
	 * @param request
	 */
	private void checkConnexion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Utilisateurs client = (Utilisateurs) session.getAttribute("client");
		if (client != null) {
			this.connected = true;
			this.cleClient = String.valueOf(client.getNumCli());
		} else {
			this.connected = false;
			this.cleClient = "00001";
		}
	}

	/**
	 * Cree un panier dans la variable de session "panier"
	 *
	 * @param request
	 */
	private void createPanier(HttpServletRequest request) {
		HttpSession session = request.getSession();
		this.panier = new Panier();
		this.panier.setClient((Utilisateurs) session.getAttribute("client"));
		session.setAttribute("panier", panier);
		if (this.connected) {
			try {
				int idPanier = panierDao.add(this.panier);
				if (idPanier != -1) {
					this.panier.setId(idPanier);
					request.getSession().setAttribute("panier", this.panier);
				} else {
					System.out.println("Probleme lors de la recuperation de l'id du panier insere.");
					errors.put("global", "Un problème est survenu.");
				}
			} catch (Exception e) {
				System.out.println("Probleme lors de la recuperation de l'id du panier insere.");
				errors.put("global", "Un problème est survenu.");
			}
		}
	}

	/**
	 * Ajoute un panier dans la base de donnee
	 *
	 * @param request
	 * @param client
	 */
	public void associerPanierAClient(HttpServletRequest request, Utilisateurs client) {
		try {
			this.panier = (Panier) request.getSession().getAttribute("panier");
			this.panier.setClient(client);
			int idPanier = panierDao.add(this.panier);
			this.panier.setId(idPanier);
			this.panier.resetData(produitDao, client.getNumCli());
			request.getSession().setAttribute("panier", this.panier);
		} catch (DAOException e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			errors.put("global", "Un probl&egrave;me est survenu pendant l'enregistrement du panier.");
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getClass().getName() + " exception occured when trying to save current cart.");
		}
	}

	/**
	 * Recupere l'ancien panier actif d'un client
	 *
	 * @param request
	 * @return un objet panier si il y en a un, null si il y a une erreur ou si il
	 * n'y en a pas
	 */
	public Panier getBackPanier(HttpServletRequest request) {
		try {

			Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
			return panierDao.getPanierByClient(client.getIdUtilisateurs(), String.valueOf(client.getNumCli()));
		} catch (DAOException e) {
			Logger.getLogger(this.getClass().getName()).severe("[ERROR] " + e.getMessage());
			errors.put("global", "Une erreur s'est produite. Veuillez r&eacute;essayer.");
			return null;
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getClass().getName() + " exception occured when trying to getBack cart.");
			errors.put("global", "Une erreur est survenue pendant la r&eacute;cup&eacute;ration de votre panier.");
			return null;
		}
	}

	/**
	 * Choisir entre fusionner deux panier ou recuperer l'ancien panier en ecrasant
	 * le nouveau
	 *
	 * @param request
	 */
	public void choisirUnPanier(HttpServletRequest request) {
		String choix = getValeurChamp(request, champ_choix_panier);
		if (choix != null) {
			if (choix.equals("Récupérer")) {
				// Choix de r�cup�rer l'ancien panier. On remplace alors juste le panier en
				// cours par l'ancien apr�s l'avoir r�cup�rer
				try {
					Utilisateurs user = (Utilisateurs) request.getSession().getAttribute("userTmp");
					if (user != null) {
						request.getSession().setAttribute("client", user);
						request.getSession().removeAttribute("userTmp");
					}
					Panier oldOne = getBackPanier(request);
					if (oldOne == null) {
						errors.put("global", "Une erreur est survenue.");
					} else {
						request.getSession().setAttribute("panier", oldOne);
					}
				} catch (Exception e) {
					errors.put("global", "Une erreur est survenue.");
				}
			} else {
				try {
					Utilisateurs user = (Utilisateurs) request.getSession().getAttribute("userTmp");
					this.result = "Bienvenue " + user.getNom() + " " + user.getPrenom() + ".";
					if (user != null) {
						request.getSession().setAttribute("client", user);
						request.getSession().removeAttribute("userTmp");
					}
					Panier oldOne = getBackPanier(request);
					Panier current = (Panier) request.getSession().getAttribute("panier");
					int i = 0;
					for(LignePanier l : current.getLignePaniersById()){
						oldOne.addProduit(l.getProduitsByIdProduit(), l.getQuantite());
					}
					request.getSession().setAttribute("panier", oldOne);
				} catch (Exception e) {
					errors.put("global", "Une erreur est survenue.");
				}
			}
		} else {
			errors.put("global", "Une erreur est survenue.");
		}
	}
}
