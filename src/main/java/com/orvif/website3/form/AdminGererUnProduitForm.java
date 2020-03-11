/**
 *
 */
package com.orvif.website3.form;


import com.orvif.website3.Entity.*;
import com.orvif.website3.Entity.helper.*;
import com.orvif.website3.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Marijon Etienne
 */
public class AdminGererUnProduitForm extends Form {
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private ProduitsHelper produitDao;
	@Autowired
	private CaracteristiquesHelper caracteristiqueDao;
	@Autowired
	private DocumentRepository docDao;
	@Autowired
	private FamillesHelper familleDao;
	@Autowired
	private SsFamillesRepository sousFamilleDao;
	@Autowired
	private CategoriesRepository categorieDao;
	@Autowired
	private SsCategoriesRepository sousCategorieDao;
	@Autowired
	private HistoriqueModificationRepository histoDao;
	@Autowired
	private MarquesRepository marqueDao;
	@Autowired
	private GammesRepository gammeDao;
	@Autowired
	private UnitesFactuHelper ufDao;
	@Autowired
	private UnitesVenteHelper uvDao;

	private final String champ_code_article = "codeArticle";
	private final String champ_search = "search";
	private final String champ_action = "action";
	private final String champ_choix_modif = "c";
	private final String champ_modif_cleSystem = "cs";

	private final String champ_codeOrvif = "codeOrvif";
	private final String champ_refFournisseur = "refFournisseur";
	private final String champ_libelle = "libelle";
	private final String champ_famille = "famille";
	private final String champ_sousFamille = "sousFamille";
	private final String champ_categorie = "categorie";
	private final String champ_sousCategorie = "sousCategorie";
	private final String champ_description = "description";
	private final String champ_avantages = "avantages";
	private final String champ_marque = "marque";
	private final String champ_uniteFacturation = "uf";
	private final String champ_uniteVente = "uv";
	private final String champ_gamme = "gamme";
	private final String champ_visible = "visible";

	private final String champ_cleSystemBase = "cleSystemBase";

	private final String champ_idCaracteristique = "idC";
	private final String champ_valeurCaracteristique = "valeurC";

	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public AdminGererUnProduitForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void updateProduct(HttpServletRequest request) {
		// Recuperation du produit
		String cleSystemBase = getValeurChamp(request, champ_cleSystemBase);

		Produits ancienProd = null;
		if (checkInteger(cleSystemBase)) {
			ancienProd = produitDao.getByCleSysteme(Integer.parseInt(cleSystemBase));
		} else {
			errors.put("global", "Une erreur est survenue. Veuillez réessayer");
		}
		if (ancienProd != null) {
			// verfication des champs et creation de l'objet produit
			String cleSystem = getValeurChamp(request, champ_code_article);
			String codeOrvif = getValeurChamp(request, champ_codeOrvif);
			String refFournisseur = getValeurChamp(request, champ_refFournisseur);
			String libelle = getValeurChamp(request, champ_libelle);
			String famille = getValeurChamp(request, champ_famille);
			String sousFamille = getValeurChamp(request, champ_sousFamille);
			String categorie = getValeurChamp(request, champ_categorie);
			String sousCategorie = getValeurChamp(request, champ_sousCategorie);
			String description = getValeurChamp(request, champ_description);
			String avantages = getValeurChamp(request, champ_avantages);
			String marque = getValeurChamp(request, champ_marque);
			String uf = getValeurChamp(request, champ_uniteFacturation);
			String uv = getValeurChamp(request, champ_uniteVente);
			String gamme = getValeurChamp(request, champ_gamme);
			String visible = getValeurChamp(request, champ_visible);

			Produits newProduit = new Produits();
			newProduit.setIdProduits(ancienProd.getIdProduits());
			if (!checkInteger(cleSystem)) {
				errors.put(champ_code_article, "La clé système n'est pas valide.");
				newProduit.setCleSystem(00000);
			} else {
				newProduit.setCleSystem(Integer.parseInt(cleSystem));
			}

			newProduit.setCodeOrvif(codeOrvif);
			if (codeOrvif == null || codeOrvif.trim().equals("")) {
				errors.put(champ_codeOrvif, "Veuillez entrer un code.");
			}

			newProduit.setRefFournisseur(refFournisseur);
			if (refFournisseur == null || refFournisseur.trim().equals("")) {
				errors.put(champ_refFournisseur, "Veuillez entrer une référence.");
			}

			newProduit.setLibelle(libelle);
			if (libelle == null || libelle.trim().equals("")) {
				errors.put(champ_libelle, "Veuillez entrer un libelle.");
			}

			newProduit.setDescriptif(description);
			if (description == null || description.trim().equals("")) {
				errors.put(champ_description, "Veuillez entrer une description.");
			}

			newProduit.setAvantages(avantages);
			if (avantages == null || avantages.trim().equals("")) {
				errors.put(champ_avantages, "Veuillez entrer les avantages produit.");
			}

			if (!checkInteger(uv)) {
				errors.put(champ_uniteVente,
						"Une erreur est survenue avec le choix de l'unité de vente. Veuillez réessayer");
			} else {
				UnitesVente uvO = new UnitesVente();
				uvO.setIdUv(Integer.parseInt(uv));
				newProduit.setUnitesVenteByIdUv(uvO);
			}

			if (!checkInteger(uf)) {
				errors.put(champ_uniteFacturation,
						"Une erreur est survenue avec le choix de l'unité de facturation. Veuillez réessayer");
			} else {
				UnitesFacturation ufO = new UnitesFacturation();
				ufO.setIdUf(Integer.parseInt(uf));
				newProduit.setUnitesFacturationByIdUf(ufO);
			}

			if (checkInteger(visible)) {
				int v = Integer.parseInt(visible);
				if (v == 1) {
					newProduit.setVisible(true);
				} else if (v == 0) {
					newProduit.setVisible(false);
				} else {
					errors.put(champ_visible,
							"Une erreur est survenue avec le choix de la visibilité. Veuillez réessayer");
				}
			}

			if (!checkInteger(marque)) {
				errors.put(champ_marque, "Une erreur est survenue avec le choix de la marque. Veuillez réessayer");
			} else {
				Marques m = new Marques();
				m.setIdMarques(Integer.parseInt(marque));
				newProduit.setMarquesByIdMarques(m);
				if (!checkInteger(gamme)) {
					errors.put(champ_gamme, "Une erreur est survenue avec le choix de la gamme. Veuillez réessayer");
				} else {
					Gammes g = new Gammes();
					g.setIdGammes(Integer.parseInt(gamme));
					newProduit.setGammesByIdGammes(g);
				}
			}

			if (!checkInteger(famille)) {
				errors.put(champ_famille, "Une erreur est survenue avec le choix de la famille. Veuillez réessayer");
			} else {
				Familles f = new Familles();
				f.setIdFamilles(Integer.parseInt(famille));
				newProduit.setFamillesByIdFamilles(f);
				if (!checkInteger(sousFamille)) {
					errors.put(champ_sousFamille,
							"Une erreur est survenue avec le choix de la sous famille. Veuillez réessayer");
				} else {
					SsFamilles sf = new SsFamilles();
					sf.setIdSsfamilles(Integer.parseInt(sousFamille));
					newProduit.setSsFamillesByIdSsfamilles(sf);
					if (!checkInteger(categorie)) {
						errors.put(champ_categorie,
								"Une erreur est survenue avec le choix de la categorie. Veuillez réessayer");
					} else {
						Categories c = new Categories();
						c.setIdCategories(Integer.parseInt(categorie));
						newProduit.setCategoriesByIdCategories(c);
						if (!checkInteger(sousCategorie)) {
							errors.put(champ_sousCategorie,
									"Une erreur est survenue avec le choix de la sous categorie. Veuillez réessayer");
						} else {
							SsCategories sc = new SsCategories();
							sc.setIdSscategories(Integer.parseInt(sousCategorie));
							newProduit.setSsCategoriesByIdSscategories(sc);
						}
					}
				}
			}
			if (errors.isEmpty()) {
				Utilisateurs user = ((Utilisateurs) request.getSession().getAttribute("admin"));
				String modificateur = user.getNom() + " " + user.getPrenom();
				if (!produitDao.update(newProduit, modificateur)) {
					errors.put("global", "Une erreur est survenue. Veuillez r�essayer.");
					request.setAttribute("choix", "infoGen");
					request.setAttribute("produit", newProduit);
					request.setAttribute("errors", errors);
				} else {
					request.setAttribute("urlRedirect",
							"adminUpdateProduct?action=byCle&" + champ_code_article + "=" + newProduit.getCleSystem());
				}
			} else {
				// Recuperation de toutes les familles, sous famille, categories et sous
				// categories concern�es
				List<Familles> familleCollection = familleDao.getAll();
				List<SsFamilles> sousFamilleCollection = null;
				List<Categories> categorieCollection = null;
				List<SsCategories> sousCategorieCollection = null;
				if (errors.get(champ_famille) == null) {
					if (errors.get(champ_sousFamille) == null) {
						if (errors.get(champ_categorie) == null) {
							if (errors.get(champ_sousCategorie) == null) {
								sousFamilleCollection = sousFamilleDao.getByFamille(newProduit.getFamillesByIdFamilles().getIdFamilles());
								newProduit.setSsFamillesByIdSsfamilles(sousFamilleCollection.get(0));
								categorieCollection = categorieDao
										.getBySousFamille(newProduit.getSsFamillesByIdSsfamilles().getIdSsfamilles());
								newProduit.setCategoriesByIdCategories(categorieCollection.get(0));
								sousCategorieCollection = sousCategorieDao
										.getByCategorie(newProduit.getCategoriesByIdCategories().getIdCategories());
							} else {
								sousFamilleCollection = sousFamilleDao.getByFamille(newProduit.getFamillesByIdFamilles().getIdFamilles());
								categorieCollection = categorieDao
										.getBySousFamille(newProduit.getSsFamillesByIdSsfamilles().getIdSsfamilles());
								sousCategorieCollection = sousCategorieDao
										.getByCategorie(newProduit.getCategoriesByIdCategories().getIdCategories());
								newProduit.setSsCategoriesByIdSscategories(sousCategorieCollection.get(0));
							}
						} else {
							sousFamilleCollection = sousFamilleDao.getByFamille(newProduit.getFamillesByIdFamilles().getIdFamilles());
							categorieCollection = categorieDao.getBySousFamille(newProduit.getSsFamillesByIdSsfamilles().getIdSsfamilles());
							newProduit.setCategoriesByIdCategories(categorieCollection.get(0));
							sousCategorieCollection = sousCategorieDao
									.getByCategorie(newProduit.getCategoriesByIdCategories().getIdCategories());
							newProduit.setSsCategoriesByIdSscategories(sousCategorieCollection.get(0));
						}
					} else {
						sousFamilleCollection = sousFamilleDao.getByFamille(newProduit.getFamillesByIdFamilles().getIdFamilles());
						newProduit.setSsFamillesByIdSsfamilles(sousFamilleCollection.get(0));
						categorieCollection = categorieDao.getBySousFamille(newProduit.getSsFamillesByIdSsfamilles().getIdSsfamilles());
						newProduit.setCategoriesByIdCategories(categorieCollection.get(0));
						sousCategorieCollection = sousCategorieDao.getByCategorie(newProduit.getCategoriesByIdCategories().getIdCategories());
						newProduit.setSsCategoriesByIdSscategories(sousCategorieCollection.get(0));
					}
				} else {
					newProduit.setFamillesByIdFamilles(familleCollection.get(0));
					sousFamilleCollection = sousFamilleDao.getByFamille(newProduit.getFamillesByIdFamilles().getIdFamilles());
					newProduit.setSsFamillesByIdSsfamilles(sousFamilleCollection.get(0));
					categorieCollection = categorieDao.getBySousFamille(newProduit.getSsFamillesByIdSsfamilles().getIdSsfamilles());
					newProduit.setCategoriesByIdCategories(categorieCollection.get(0));
					sousCategorieCollection = sousCategorieDao.getByCategorie(newProduit.getCategoriesByIdCategories().getIdCategories());
					newProduit.setSsCategoriesByIdSscategories(sousCategorieCollection.get(0));
				}
				// Recuperation marques et gammes
				List<Marques> marqueCollection = null;
				List<Gammes> gammeCollection = null;
				if (errors.get(champ_marque) == null) {
					if (errors.get(champ_gamme) == null) {
						marqueCollection = marqueDao.getAll();
						gammeCollection = gammeDao.getByMarque(newProduit.getMarquesByIdMarques().getIdMarques());
					} else {
						marqueCollection = marqueDao.getAll();
						gammeCollection = gammeDao.getByMarque(newProduit.getMarquesByIdMarques().getIdMarques());
						newProduit.setGammesByIdGammes(gammeCollection.get(0));
					}
				} else {
					marqueCollection = marqueDao.getAll();
					newProduit.setMarquesByIdMarques(marqueCollection.get(0));
					gammeCollection = gammeDao.getByMarque(newProduit.getMarquesByIdMarques().getIdMarques());
					newProduit.setGammesByIdGammes(gammeCollection.get(0));
				}
				// Recuperation uv et uf
				List<UnitesVente> uvCollection = uvDao.getAll();
				List<UnitesFacturation> ufCollection = ufDao.getAll();

				request.setAttribute("uvCollection", uvCollection);
				request.setAttribute("ufCollection", ufCollection);
				request.setAttribute("marqueCollection", marqueCollection);
				request.setAttribute("gammeCollection", gammeCollection);
				request.setAttribute("familleCollection", familleCollection);
				request.setAttribute("sousFamilleCollection", sousFamilleCollection);
				request.setAttribute("categorieCollection", categorieCollection);
				request.setAttribute("sousCategorieCollection", sousCategorieCollection);
				request.setAttribute("choix", "infoGen");
				request.setAttribute("errors", errors);
				request.setAttribute("produit", newProduit);
				request.setAttribute("cleSystemBase", Integer.parseInt(cleSystemBase));
			}
		}
	}

	public void getProductToUpdate(HttpServletRequest request) {
		String choix = getValeurChamp(request, champ_choix_modif);
		String cleSystem = getValeurChamp(request, champ_modif_cleSystem);
		if (choix.equals("infoGen")) {
			if (cleSystem != null && checkInteger(cleSystem)) {
				Produits p = produitDao.getByCleSysteme(Integer.parseInt(cleSystem));
				if (p != null) {
					try {
						p.setFamillesByIdFamilles(familleDao.getByProduit(p.getIdProduits()));
						p.setSsFamillesByIdSsfamilles(sousFamilleDao.getByIdProduit(p.getIdProduits()));
						p.setCategoriesByIdCategories(categorieDao.getByIdProduit(p.getIdProduits()));
						p.setSsCategoriesByIdSscategories(this.sousCategorieDao.getByIdProduit(p.getIdProduits()));
						List<Familles> familleCollection = familleDao.getAll();
						List<SsFamilles> sousFamilleCollection = sousFamilleDao.getByFamille(p.getFamillesByIdFamilles().getIdFamilles());
						List<Categories> categorieCollection = categorieDao.getBySousFamille(p.getSsFamillesByIdSsfamilles().getIdSsfamilles());
						List<SsCategories> sousCategorieCollection = sousCategorieDao
								.getByCategorie(p.getCategoriesByIdCategories().getIdCategories());
						List<Marques> marqueCollection = marqueDao.getAll();
						List<Gammes> gammeCollection = gammeDao.getByMarque(p.getMarquesByIdMarques().getIdMarques());
						List<UnitesVente> uvCollection = uvDao.getAll();
						List<UnitesFacturation> ufCollection = ufDao.getAll();
						request.setAttribute("produit", p);
						request.setAttribute("familleCollection", familleCollection);
						request.setAttribute("sousFamilleCollection", sousFamilleCollection);
						request.setAttribute("categorieCollection", categorieCollection);
						request.setAttribute("sousCategorieCollection", sousCategorieCollection);
						request.setAttribute("marqueCollection", marqueCollection);
						request.setAttribute("gammeCollection", gammeCollection);
						request.setAttribute("uvCollection", uvCollection);
						request.setAttribute("ufCollection", ufCollection);
						request.setAttribute("cleSystemBase", Integer.parseInt(cleSystem));
						request.setAttribute("choix", "infoGen");
					} catch (Exception e) {
						errors.put("global", "Une erreur est survenue.");
					}

				} else {
					errors.put("global", "Une erreur est survenue.");
				}
			} else {
				errors.put("global", "Une erreur est survenue. Veuillez réessayer.");
			}
		} else if (choix.equals("caracteristique")) {
			if (cleSystem != null && checkInteger(cleSystem)) {
				try {
					Produits p = produitDao.getByCleSysteme(Integer.parseInt(cleSystem));

					//Recuperation de toutes les caracteristiques
					ArrayList<Caracteristiques> caracteristiqueCollection = caracteristiqueDao.getAll();
					caracteristiqueDao.fillWithExistingValues(caracteristiqueCollection);

					//Recuperation des caracteristiques existantes du produit
					p.setCaracteristiqueCollection(caracteristiqueDao.getByProduit(p.getIdProduits()));

					request.setAttribute("produit", p);
					request.setAttribute("caracteristiques", caracteristiqueCollection);
					request.setAttribute("choix", "caracteristique");
					request.setAttribute("cleSystemBase", Integer.parseInt(cleSystem));
				} catch (Exception e) {
					errors.put("global", "Une erreur s'est produite. Veuillez réessayer.");
				}
			} else {
				errors.put("global", "Une erreur s'est produite. Veuillez réessayer.");
			}
		}
	}

	public void getProduct(HttpServletRequest request) {
		String action = getValeurChamp(request, champ_action);
		if (action != null) {
			if (action.equals("byCle")) {
				// Rechercher un produit
				String codeArticle = getValeurChamp(request, champ_code_article);
				if (checkInteger(codeArticle)) {
					try {
						int cle = Integer.parseInt(codeArticle);
						//Everything needed is in "groupe"
						Groupe groupe = produitDao.getGroupeByCodeArticle(cle);
						if (!groupe.getProducts().isEmpty()) {
							request.setAttribute("article", groupe);
							return;
						}
						errors.put("global", "L'article n'a pas été trouvé.");
//						Produit p = produitDao.getByCleSysteme(cle);
//						if (p != null) {
//							this.caracteristiqueDao = daoFactory.getCaracteristiqueDao();
//							p.setCaracteristiqueCollection(caracteristiqueDao.getByProduit(p.getId()));
//							this.docDao = daoFactory.getDocumentDao();
//							Map<String, List<Document>> docs = docDao.getDocumentListByProduit(p.getId());
//							p.setImageCollection(docs.get("imageCollection"));
//							p.setOtherDocCollection(docs.get("otherCollection"));
//							// Recuperation de famille, sous famille, categorie et sous categorie
//							this.familleDao = daoFactory.getFamilleDao();
//							this.sousFamilleDao = daoFactory.getSousFamilleDao();
//							this.categorieDao = daoFactory.getCategorieDao();
//							this.sousCategorieDao = daoFactory.getSousCategorieDao();
//							this.marqueDao = daoFactory.getMarqueDao();
//							this.gammeDao = daoFactory.getGammeDao();
//							this.uvDao = daoFactory.getUniteVenteDao();
//							this.ufDao = daoFactory.getUniteFacturationDao();
//							//Recuperation des produits complementaires et associ�s
//							p.setComplementaryProduct(produitDao.getProduitsComplementaire(p.getId(), "00001"));
//							p.setSimilarProduct(produitDao.getProduitsSimilaire(p.getId(), "00001"));
//							p.setFamille(familleDao.getByProduit(p.getId()));
//							p.setSousFamille(sousFamilleDao.getByIdProduit(p.getId()));
//							p.setCategorie(categorieDao.getByIdProduit(p.getId()));
//							p.setSousCategorie(this.sousCategorieDao.getByIdProduit(p.getId()));
//							p.setMarque(marqueDao.getById(p.getMarque().getId()));
//							p.setGamme(gammeDao.getById(p.getGamme().getId()));
//							p.setUniteVente(uvDao.getById(p.getUniteVente().getId()));
//							p.setUniteFacturation(ufDao.getById(p.getUniteFacturation().getId()));
//							// Recuperation de l'historique de modification
//							this.histoDao = daoFactory.getHistoriqueModificationProduitDAO();
//							p.setHistoriqueModification(histoDao.getByProduit(p.getId()));
//							request.setAttribute("produit", p);
//						} else {
//							errors.put("global", "Le produit n'a pas été trouvé.");
//						}
					} catch (DAOException e) {
						Logger.getLogger(getClass().getName()).severe(e.getMessage());
						errors.put("global", "Une erreur est survenue, veuillez réessayer.");
					} catch (Exception e) {
						e.printStackTrace();
						errors.put("global", "Une erreur est survenue, veuillez réessayer.");
					}
				} else {
					errors.put(champ_code_article, "La clé système n'est pas valide.");
				}
			} else if (action.equals("bySearch")) {
				// Recuperer un produit depuis la clé système

				String search = getValeurChamp(request, champ_search);
				if (search != null) {
					try {
						List<Groupe> articleList = produitDao.getBySearchAdmin(search);
//						List<Produit> liste = produitDao.getBySearchAdmin(search);
						if (articleList == null) {
							errors.put(champ_search, "La recherche n'a donné aucun résultat.");
						} else {
							for (Groupe article : articleList) {
								article.getProducts().get(0).setFamillesByIdFamilles(familleDao.getByProduit(article.getProducts().get(0).getIdProduits()));
								article.getProducts().get(0).setSsFamillesByIdSsfamilles(sousFamilleDao.getByIdProduit(article.getProducts().get(0).getIdProduits()));
								article.getProducts().get(0).setCategoriesByIdCategories(categorieDao.getByIdProduit(article.getProducts().get(0).getIdProduits()));
								article.getProducts().get(0).setSsCategoriesByIdSscategories(this.sousCategorieDao.getByIdProduit(article.getProducts().get(0).getIdProduits()));
							}
							request.setAttribute("listeProduit", articleList);
							request.setAttribute("search", search);
						}
					} catch (DAOException e) {
						Logger.getLogger(getClass().getName()).severe(e.getMessage());
						errors.put("global", "Une erreur est survenue");
					}
				} else {
					errors.put(champ_search, "S'il vous plait entrez une valeur de recherche.");
				}
			} else {
				errors.put("global", "Une erreur est survenue, veuillez réessayer.");
			}
		} else {
			//Should never go there, but I just keep it just in case
			errors.put("global", "Une erreur est survenue, veuillez réessayer.");
		}
	}

	public List<String> getValeursCaracteristique(HttpServletRequest request) {
		List<String> ret = null;
		String caractId = getValeurChamp(request, champ_idCaracteristique);
		if (checkInteger(caractId)) {
			try {
				Caracteristiques c = new Caracteristiques();
				c.setIdCaracteristiques(Integer.parseInt(caractId));
				ArrayList<Caracteristiques> cCollection = new ArrayList<Caracteristiques>();
				cCollection.add(c);
				caracteristiqueDao.fillWithExistingValues(cCollection);
				ret = cCollection.get(0).getValeurCollection();
			} catch (Exception e) {
				errors.put("global", "Une erreur s'est produite.");
			}
		} else {
			errors.put("global", "Une erreur s'est produite");
		}
		return ret;
	}

	public void updateCaracteristiqueProduct(HttpServletRequest request) {
		try {
			//Recuperation des caracteristique actuelles du produit en cours
			String cleSystemBase = getValeurChamp(request, champ_cleSystemBase);
			Produits ancienProd = null;
			if (checkInteger(cleSystemBase)) {
				ancienProd = produitDao.getByCleSysteme(Integer.parseInt(cleSystemBase));
				List<Caracteristiques> lastCaracteristique = caracteristiqueDao.getByProduit(ancienProd.getIdProduits());

				//Creation de la nouvelle liste de caracteristique

				String[] idsCaract = getValeurChampMultiple(request, champ_idCaracteristique);
				String[] valeursCaract = getValeurChampMultiple(request, champ_valeurCaracteristique);
				if (idsCaract.length == valeursCaract.length) {
					List<Caracteristiques> nouvellesCaract = new ArrayList<Caracteristiques>();
					for (int i = 0; i < idsCaract.length; i++) {
						boolean error = false;
						Caracteristiques c = new Caracteristiques();
						if (checkInteger(idsCaract[i])) {
							//Recup de la caracteristique en base de donnees
							Caracteristiques cTmp = caracteristiqueDao.getById(Integer.parseInt(idsCaract[i]));
							if (cTmp != null) {
								c.setIdCaracteristiques(cTmp.getIdCaracteristiques());
								c.setLibelle(cTmp.getLibelle());
								c.setValeurProduit(valeursCaract[i]);
								nouvellesCaract.add(c);
							} else {
								error = true;
							}
						} else {
							error = true;
						}
						if (error == true) {
							errors.put("global", "Une erreur est survenue, veuillez réessayer.");
							break;
						}
					}
					if (errors.isEmpty()) {
						//Mise a jour dans la base de donnees
						try {
							Utilisateurs modificateur = (Utilisateurs) request.getSession().getAttribute("admin");
							String modif = modificateur.getNom() + " " + modificateur.getPrenom();
							if (caracteristiqueDao.updateCaracteristiqueProduit(ancienProd.getIdProduits(), nouvellesCaract, modif)) {
								request.setAttribute("urlRedirect", "adminUpdateProduct?c=caracteristique&cs=" + String.valueOf(ancienProd.getCleSystem()));
							} else {
								errors.put("global", "Une erreur est survenue, veuillez réessayer.");
							}
						} catch (Exception e) {
							errors.put("global", "Une erreur est survenue, veuillez réessayer.");
						}
					} else {

					}
				} else {
					errors.put("global", "Une erreur est survenue, veuillez réessayer.");
				}
			} else {
				errors.put("global", "Une erreur est survenue. Veuillez réessayer");
			}
		} catch (Exception e) {
			errors.put("global", "Une erreur est survenue, veuillez réessayer.");
		}
	}
}
