/**
 *
 */
package com.orvif.website3.form;

import com.google.gson.Gson;
import com.orvif.website3.Entity.*;
import com.orvif.website3.Entity.helper.*;
import com.orvif.website3.Repository.CaracteristiquesRepository;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
//import net.java.jna.platform.win32.Netapi32Util.Group;


public class FormProduit extends Form {

	@Autowired
	private FamillesHelper familleDao;
	@Autowired
	private SsFamillesHelper ssFamilleDao;
	@Autowired
	private CategoriesHelper categorieDao;
	@Autowired
	private SsCategoriesHelper ssCategorieDao;
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private ProduitsHelper produitDao;
	@Autowired
	private GroupHelper groupeDao;
	@Autowired
	private DocumentHelper documentDao;
	@Autowired
	private CaracteristiquesHelper caracteristiqueDao;

	private boolean available = true;

	private final String champ_produit = "p";

	private String cleClient;

	private String result;

	private Map<String, String> errors = new HashMap<String, String>();

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public FormProduit(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}


	/**
	 * Methode pour recuperer un groupe
	 *
	 * @param request
	 * @return
	 */
	public Groupe getGroupe(HttpServletRequest request) {
		checkConnexion(request);
		String idProductStr = getValeurChamp(request, champ_produit);
		if (checkInteger(idProductStr)) {
			int idProduct = Integer.parseInt(idProductStr);
			try {
				return produitDao.getGroupeByProduct(idProduct, cleClient);
			} catch (DAOException e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer !");
				return null;
			}
		} else {
			errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer !");
			return null;
		}
	}

	/**
	 * Methode pour recuperer un produit
	 *
	 * @param request
	 * @return
	 */
	public Produits getProduct(HttpServletRequest request) {
		checkConnexion(request);
		String idProductStr = getValeurChamp(request, champ_produit);
		if (checkInteger(idProductStr)) {
			try {
				int idProduct = Integer.parseInt(idProductStr);
				Produits p = produitDao.getById(idProduct, cleClient);
				Map<String, List<Document>> documentCollection = documentDao.getDocumentListByProduit(p.getIdProduits());
				p.setFamillesByIdFamilles(familleDao.getByProduit(p.getIdProduits()));
				p.setSsFamillesByIdSsfamilles(ssFamilleDao.getByIdProduit(p.getIdProduits()));
				p.setCategoriesByIdCategories(categorieDao.getByIdProduit(p.getIdProduits()));
				p.setSsCategoriesByIdSscategories(ssCategorieDao.getByIdProduit(p.getIdProduits()));
				p.setImageCollection(documentCollection.get("imageCollection"));
				p.setOtherDocCollection(documentCollection.get("otherCollection"));
				p.setCaracteristiqueCollection(this.caracteristiqueDao.getByProduit(p.getIdProduits()));
				p.setComplementaryProduct(this.produitDao.getProduitsComplementaire(p.getIdProduits(), cleClient));
				p.setSimilarProduct(this.produitDao.getProduitsSimilaire(p.getIdProduits(), cleClient));
				try {
					p.setGroupe(groupeDao.getByProduct(p));
					p.getGroupe().setProducts(produitDao.getProductsInList(p.getGroupe().getProducts(), cleClient));
					for (Produits prod : p.getGroupe().getProducts()) {
						prod.setCaracteristiqueCollection(caracteristiqueDao.getByProduit(prod.getIdProduits()));
					}
					List<Caracteristiques> cList = new ArrayList<>();
					for (Caracteristiques c : p.getGroupe().getCriteres()) {
						cList.add(caracteristiqueDao.getById(c.getIdCaracteristiques()));
					}
					p.getGroupe().setCriteres(cList);
				} catch (DAOException e1) {
					Logger.getLogger(FormProduit.class.getName()).warning("DAO Exception : " + e1.getMessage());
				}
				return p;
			} catch (Exception e) {
				System.out.println("2");
				available = false;
				errors.put("global", "Une erreur est survenue.");
				return null;
			}
		} else {
			System.out.println("3");

			available = false;
			errors.put("global", "Une erreur est survenue.");
			return null;
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
			this.cleClient = String.valueOf(client.getNumCli());
		} else {
			this.cleClient = "00001";
		}
	}

	/**
	 * Ajoute un document pour un produit (image ou document)
	 *
	 * @param request
	 */
	public String addDocumentProduit(HttpServletRequest request) {
		try {
			String typeDeDoc = getValeurChamp(request, "type");
			String path = getValeurChamp(request, "path");
			String title = getValeurChamp(request, "title");
			String description = getValeurChamp(request, "description");
			int idProd = Integer.valueOf(getValeurChamp(request, "idProd"));
			Document doc = new Document();
			if (title == null) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Vous n'avez pas renseign� le titre du document.");
				return new Gson().toJson(json);
			}
			doc.setTitre(title);
			doc.setUrl(path);
			doc.setDescription(description);
			doc.setType(typeDeDoc);
			int id = -1;
			if ((id = this.produitDao.addDocument(idProd, doc)) == -1) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue lors de l'ajout du document. Veuillez réessayer.");
				return new Gson().toJson(json);
			} else {
				Map<String, String> json = new HashMap<String, String>();
				json.put("idDoc", String.valueOf(id));
				return new Gson().toJson(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue lors de l'ajout du document. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	public String removeDocumentProduit(HttpServletRequest request) {
		try {
			int idProduit = Integer.parseInt(getValeurChamp(request, "idProd"));
			int idDoc = Integer.parseInt(getValeurChamp(request, "idDoc"));
			if (!this.produitDao.removeDocument(idProduit, idDoc)) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Une erreur est survenue lors de la suppression du document. Veuillez réessayer.");
				return new Gson().toJson(json);
			} else {
				Map<String, String> json = new HashMap<String, String>();
				return new Gson().toJson(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue lors de la suppression du document. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	public boolean isAvailable() {
		return available;
	}


}
