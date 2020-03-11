/**
 * 
 */
package com.orvif.website3.form;

import com.orvif.website3.Entity.*;
import com.orvif.website3.Entity.helper.FamillesHelper;
import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Entity.helper.UnitesFactuHelper;
import com.orvif.website3.Entity.helper.UnitesVenteHelper;
import com.orvif.website3.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



/**
 * @author Marijon Etienne
 *
 */
public class AdminAjouterUnProduitForm extends Form {
	private DAOFactory daoFactory;

	@Autowired
	private ProduitsHelper produitDao;
	@Autowired
	private CaracteristiquesRepository caracteristiqueDao;
	@Autowired
	private DocumentRepository docDao;
	@Autowired
	private FamillesHelper familleDao;
	@Autowired
	private FamillesRepository familleDaoRepo;
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

	private final String champ_cle_systeme = "cleSysteme";
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

	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public AdminAjouterUnProduitForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void getDataNecessaireAjoutProduit(HttpServletRequest request) {
		try {
			List<Familles> familleCollection = familleDaoRepo.findAll();
			List<SsFamilles> sousFamilleCollection = new ArrayList<SsFamilles>();
			if (familleCollection != null && !familleCollection.isEmpty()) {
				sousFamilleCollection = sousFamilleDao.getByFamille(familleCollection.get(0).getIdFamilles());
			}

			List<Categories> categorieCollection = new ArrayList<Categories>();
			if (sousFamilleCollection != null && !sousFamilleCollection.isEmpty()) {
				categorieCollection = categorieDao.getBySousFamille(sousFamilleCollection.get(0).getIdSsfamilles());
			}

			List<SsCategories> sousCategorieCollection = new ArrayList<SsCategories>();
			if (categorieCollection != null && !categorieCollection.isEmpty()) {
				sousCategorieCollection = sousCategorieDao.getByCategorie(categorieCollection.get(0).getIdCategories());
			}

			List<Marques> marqueCollection = marqueDao.getAll();
			List<Gammes> gammeCollection = new ArrayList<Gammes>();
			if (marqueCollection != null && !marqueCollection.isEmpty()) {
				gammeCollection = gammeDao.getByMarque(marqueCollection.get(0).getIdMarques());
			}
			List<UnitesVente> uvCollection = uvDao.getAll();
			List<UnitesFacturation> ufCollection = ufDao.getAll();
			request.setAttribute("familleCollection", familleCollection);
			request.setAttribute("sousFamilleCollection", sousFamilleCollection);
			request.setAttribute("categorieCollection", categorieCollection);
			request.setAttribute("sousCategorieCollection", sousCategorieCollection);
			request.setAttribute("marqueCollection", marqueCollection);
			request.setAttribute("gammeCollection", gammeCollection);
			request.setAttribute("uvCollection", uvCollection);
			request.setAttribute("ufCollection", ufCollection);
		} catch (Exception e) {
			errors.put("global", "Une erreur s'est produite. Veuillez réessayer.");
		}
	}

	public void addProduct(HttpServletRequest request) {
		// v�rfication des champs et creation de l'objet produit
					String cleSystem = getValeurChamp(request, champ_cle_systeme);
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
					if (!checkInteger(cleSystem)) {
						errors.put(champ_cle_systeme, "La clé système n'est pas valide.");
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
						if (!produitDao.add(newProduit)) {
							errors.put("global", "Une erreur est survenue. Veuillez réessayer.");
							request.setAttribute("produit", newProduit);
							errors.put("global", "Une erreur est survenue lors de l'ajout du produit, veuillez réessayer.");
							request.setAttribute("errors", errors);
						} else {
							request.setAttribute("urlRedirect",
									"adminUpdateProduct?action=byCle&" + champ_cle_systeme + "=" + newProduit.getCleSystem());
						}
					} else {
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
						request.setAttribute("errors", errors);
						request.setAttribute("produit", newProduit);
					}
	}
}
