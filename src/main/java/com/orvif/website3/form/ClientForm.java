package com.orvif.website3.form;



import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.AdresseHelper;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.*;
import com.orvif.website3.bean.BonDeLivraison;
import com.orvif.website3.bean.Commande;
import com.orvif.website3.bean.Devis;
import com.orvif.website3.bean.Facture;
import com.orvif.website3.service.MailgunHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientForm extends Form {
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursHelper uh;
	@Autowired
	private UtilisateursRepository userDao;
	@Autowired
	private AdresseHelper adresseDao;
	@Autowired
	private AdresseHelper ah;
	@Autowired
	private CodeConfirmHelper codeDao;

	private final String champ_nom = "nom";
	private final String champ_prenom = "prenom";
	private final String champ_mail = "mail";
	private final String champ_telephone = "telephone";
	private final String champ_libelle_adresse = "libelle";
	private final String champ_nom_voie = "nomVoie";
	private final String champ_complement_voie = "complementVoie";
	private final String champ_code_postalAdresse = "codePostal";
	private final String champ_ville_adresse = "ville";
	private final String champ_pays_adresse = "pays";

	private final String champ_password = "psw";
	private final String champ_password_check = "pswCheck";
	private final ServletContext context;

	private Map<String, String> errors = new HashMap<>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public ClientForm(DAOFactory daoFac, ServletContext context) {
		this.daoFactory = daoFac;
		this.context = context;
	}

	public ClientForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
		this.context = null;
	}

	public void ajouterUneAdesse(HttpServletRequest request) {
		String libelle = getValeurChamp(request, champ_libelle_adresse);
		String nomVoie = getValeurChamp(request, champ_nom_voie);
		String complementVoie = getValeurChamp(request, champ_complement_voie);
		String codePostal = getValeurChamp(request, champ_code_postalAdresse);
		String ville = getValeurChamp(request, champ_ville_adresse);
		String pays = getValeurChamp(request, champ_pays_adresse);

		Adresse a = new Adresse();
		if (libelle == null || libelle.trim().equals("")) {
			libelle = "Sans nom";
		}
		a.setLibelle(libelle);
		a.setNomVoie(nomVoie);
		if (nomVoie == null || nomVoie.trim().equals("")) {
			errors.put(champ_nom_voie, "Veuillez entrer un nom de voie.");
		}
		a.setComplementVoie(complementVoie);
		a.setCodePostal(Integer.valueOf(codePostal));
		if (codePostal == null || codePostal.trim().equals("")) {
			errors.put(champ_code_postalAdresse, "Veuillez entrer un code postal.");
		}
		a.setVille(ville);
		if (ville == null || ville.trim().equals("")) {
			errors.put(champ_ville_adresse, "Veuillez entrer une ville.");
		}
		a.setPays(pays);
		if (pays == null || pays.trim().equals("")) {
			errors.put(champ_pays_adresse, "Veuillez entrer un pays.");
		}
		Utilisateurs user = (Utilisateurs) request.getSession().getAttribute("client");
		if (errors.isEmpty()) {
			try {
				int res = adresseDao.add(a, user.getIdUtilisateurs());
				if (res == -1) {
					errors.put("global", "Une erreur est survenue.");
				} else {
					a.setIdAdresse(res);
					user.getAdresseCollection().add(a);
				}
			} catch (Exception e) {
				errors.put("global", "Une erreur est survenue.");
			}
		} else {
			request.setAttribute("adresse", a);
		}
	}

	public void modifierUneAdresse(HttpServletRequest request, Adresse a) throws DAOException {
		String nomVoie = getValeurChamp(request, champ_nom_voie);
		String complementVoie = getValeurChamp(request, champ_complement_voie);
		String codePostal = getValeurChamp(request, champ_code_postalAdresse);
		String ville = getValeurChamp(request, champ_ville_adresse);
		String pays = getValeurChamp(request, champ_pays_adresse);

		a.setNomVoie(nomVoie);
		if (nomVoie == null || nomVoie.trim().equals("")) {
			errors.put(champ_nom_voie, "Adresse non valide.");
		}
		a.setComplementVoie(complementVoie);
		a.setCodePostal(Integer.valueOf(codePostal));
		if (codePostal == null || codePostal.trim().equals("")) {
			errors.put(champ_code_postalAdresse, "Code postal non valide.");
		}
		a.setVille(ville);
		if (ville == null || ville.trim().equals("")) {
			errors.put(champ_ville_adresse, "Ville non valide.");
		}
		a.setPays(pays);
		if (pays == null || pays.trim().equals("")) {
			errors.put(champ_pays_adresse, "Pays non valide.");
		}
		if (errors.isEmpty()) {
			if (!ah.updateAdresse(a)) {
				errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer.");
				request.setAttribute("adresseToUpdate", a);
			} else {
				Utilisateurs customer = (Utilisateurs) request.getSession().getAttribute("client");
				for (Adresse addressCustomer : customer.getAdresseCollection()) {
					if (addressCustomer.getIdAdresse() == a.getIdAdresse()) {
						customer.getAdresseCollection().remove(addressCustomer);
						customer.getAdresseCollection().add(a);
						request.getSession().setAttribute("client", customer);
						return;
					}
				}
			}
		} else {
			request.setAttribute("adresseToUpdate", a);
		}
	}

	public void getDataClient(HttpServletRequest request) {
		Utilisateurs user = (Utilisateurs) request.getSession().getAttribute("client");
		if (user == null) {
			errors.put("global", "Vous n'etes pas connecté.<a href='login'>Connexion</a>");
		} else {
			try {
				List<Adresse> adresseCollection = adresseDao.getByClient(user.getIdUtilisateurs());
				user.setAdresseCollection(adresseCollection);
			} catch (Exception e) {
				errors.put("global", "Une erreur est survenue, veuillez réessayer.");
			}
		}
	}

	public Utilisateurs updateDataClient(HttpServletRequest request) {
		boolean mailChanged = false;
		Utilisateurs oldUser = (Utilisateurs) request.getSession().getAttribute("client");
		if (oldUser == null) {
			errors.put("global", "Une erreur est survenue.");
		} else {
			String tel = getValeurChamp(request, champ_telephone);
			String nom = getValeurChamp(request, champ_nom);
			String prenom = getValeurChamp(request, champ_prenom);
			String mail = getValeurChamp(request, champ_mail);

			Utilisateurs updatedUser;
			try {
				updatedUser = (Utilisateurs) oldUser.clone();
				updatedUser.setTelephone(tel);
				updatedUser.setNom(nom);
				updatedUser.setPrenom(prenom);
				updatedUser.setMail(mail);
				if (updatedUser.getNom() == null) {
					errors.put(champ_nom, "Vous devez entrer un nom.");
				}
				if (updatedUser.getPrenom() == null) {
					errors.put(champ_prenom, "Vous devez entrer un prénom.");
				}

				if (updatedUser.getMail() != null) {
					if (!updatedUser.getMail().equals(oldUser.getMail())) {
						mailChanged = true;
					}
					if (!checkMail(updatedUser.getMail())) {
						errors.put(champ_mail, "L'adresse mail entrée n'est pas valide.");
					}
				} else {
					errors.put(champ_mail, "Veuillez entrer une adresse mail.");
				}
				if (errors.isEmpty()) {

					if (!uh.updateUser(updatedUser)) {
						errors.put("global", "Une erreur est survenue.");
					} else {
						if (mailChanged) {
							uh.unconfirmAdressMail(updatedUser.getIdUtilisateurs());
							CodeConfirm code = genererCode("confirmmail", updatedUser.getIdUtilisateurs(), codeDao);
							if (code == null) {
								errors.put("global", "Le code de confirmation de mail n'a pas pu être généré.");
							} else {
								//Get domain name, should be http:://orvif.fr in the future
								String domain = System.getProperty("website_domain");
								sendConfirmMail(mail, domain + "/mailConfirm?c=" + code.getCode());
							}
						}
						request.getSession().setAttribute("client", updatedUser);
					}
				}
				return updatedUser;
			} catch (Exception e) {
				errors.put("global", "Une erreur est survenue");
			}
		}
		return null;
	}

	public void changePassword(HttpServletRequest request) {
		String psw = getValeurChamp(request, champ_password);
		String pswCheck = getValeurChamp(request, champ_password_check);
		if (psw == null) {
			errors.put(champ_password, "Veuillez entrer un mot de passe.");
		}
		if (pswCheck == null) {
			errors.put(champ_password, "Veuillez confirmer votre nouveau mot de passe.");
		}
		if (errors.isEmpty()) {
			if (checkpsw(psw, pswCheck)) {
				if (checkpswvalid(pswCheck)) {
					try {
						Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
						String salt1 = generateSalt(10);
						String salt2 = generateSalt(10);
						String newMdp = cryptPassw(salt1 + psw + salt2);
						if (!uh.resetPassword(client.getIdUtilisateurs(), newMdp, salt1, salt2)) {
							errors.put("global", "Une erreur est survenue.");
						}
					} catch (Exception e) {

					}
				} else {
					errors.put(champ_password, "Le mot de passe n'est pas valide.");
				}
			} else {
				errors.put(champ_password, "Les deux mots de passent ne correspondent pas.");
			}
		}
	}

	public void getCommande(HttpServletRequest request) {
		Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");

		//Recuperation de la page et du nombre d'elements par page et du tri
		String pageStr = request.getParameter("page");
		String nbParPageStr = request.getParameter("nbParPage");
		String tri = request.getParameter("tri");
		int page;
		int nbParPage;
		if (pageStr == null) {
			pageStr = "1";
		}

		if (nbParPageStr == null) {
			nbParPageStr = "20";
		}

		if (tri == null || tri.equals("")) {
			tri = "DATEDESC";
		}

		try {
			page = Integer.parseInt(pageStr);
		} catch (NumberFormatException e) {
			page = 1;
		}

		try {
			nbParPage = Integer.parseInt(nbParPageStr);
		} catch (NumberFormatException e) {
			nbParPage = 20;
		}

		if (client != null) {
			if (client.getType_client().equals("particulier")) {
				//Cas particulier
				//TODO faire le cas particulier (voir word)
			} else {
				//Cas pro
				try {
					int ordersNumber = uh.countCommandePro(String.valueOf(client.getNumCli()));
					int maxPage = (int) Math.ceil((double) ordersNumber / (double) nbParPage);
					if (maxPage == 0) maxPage = 1;
					if (page > maxPage) page = maxPage;
					List<Commande> commandeCollection = uh.getCommandePro(String.valueOf(client.getNumCli()), String.valueOf(page), String.valueOf(nbParPage), tri);
					request.setAttribute("commandes", commandeCollection);
					request.setAttribute("currentPage", page);
					request.setAttribute("maxPage", maxPage);
					request.setAttribute("nbParPage", nbParPage);
					request.setAttribute("nbTotal", ordersNumber);
				} catch (DAOException e) {
					Logger.getLogger(this.getClass().getName()).severe("[ERROR] : " + e.getMessage());
					errors.put("global", "Une erreur s'est produite.");
				}
			}
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	public void getFacture(HttpServletRequest request) {
		Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
		if (client != null) {
			//Recuperation de la page et du nombre d'elements par page et du tri
			String pageStr = request.getParameter("page");
			String nbParPageStr = request.getParameter("nbParPage");
			String tri = request.getParameter("tri");
			int page;
			int nbParPage;
			if (pageStr == null) {
				pageStr = "1";
			}

			if (nbParPageStr == null) {
				nbParPageStr = "20";
			}

			if (tri == null || tri.equals("")) {
				tri = "DATEDESC";
			}

			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				page = 1;
			}

			try {
				nbParPage = Integer.parseInt(nbParPageStr);
			} catch (NumberFormatException e) {
				nbParPage = 20;
			}
			try {

				int billsNumber = uh.countFacturePro(String.valueOf(client.getNumCli()));
				int maxPage = (int) Math.ceil((double) billsNumber / (double) nbParPage);
				if (maxPage == 0) maxPage = 1;
				if (page > maxPage) page = maxPage;
				List<Facture> factureCollection = uh.getFacture(String.valueOf(client.getNumCli()), String.valueOf(page), String.valueOf(nbParPage), tri);
				request.setAttribute("factures", factureCollection);
				request.setAttribute("currentPage", page);
				request.setAttribute("maxPage", maxPage);
				request.setAttribute("nbParPage", nbParPage);
				request.setAttribute("nbTotal", billsNumber);
			} catch (DAOException e) {
				Logger.getLogger(this.getClass().getName()).severe("[ERROR] " + e.getMessage());
				errors.put("global", "Une erreur s'est produite.");
			}
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	public void getFactureNonReglee(HttpServletRequest request) {
		Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
		if (client != null) {
			//Recuperation de la page et du nombre d'elements par page et du tri
			String pageStr = request.getParameter("page");
			String nbParPageStr = request.getParameter("nbParPage");
			String tri = request.getParameter("tri");
			int page;
			int nbParPage;
			if (pageStr == null) {
				pageStr = "1";
			}

			if (nbParPageStr == null) {
				nbParPageStr = "20";
			}

			if (tri == null || tri.equals("")) {
				tri = "DATEDESC";
			}

			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				page = 1;
			}

			try {
				nbParPage = Integer.parseInt(nbParPageStr);
			} catch (NumberFormatException e) {
				nbParPage = 20;
			}
			try {
				int billsNumber = uh.countFactureProNonReglees(String.valueOf(client.getNumCli()));
				int maxPage = (int) Math.ceil((double) billsNumber / (double) nbParPage);
				if (maxPage == 0) maxPage = 1;
				if (page > maxPage) page = maxPage;
				List<Facture> factureCollection = uh.getFactureNonReglees(String.valueOf(client.getNumCli()), String.valueOf(page), String.valueOf(nbParPage), tri);
				request.setAttribute("factures", factureCollection);
				request.setAttribute("currentPage", page);
				request.setAttribute("maxPage", maxPage);
				request.setAttribute("nbParPage", nbParPage);
				request.setAttribute("nbTotal", billsNumber);
			} catch (DAOException e) {
				Logger.getLogger(this.getClass().getName()).severe("[ERROR] " + e.getMessage());
				errors.put("global", "Une erreur s'est produite.");
			}
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	public void getDevis(HttpServletRequest request) {
		Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
		if (client != null) {
			if (Integer.toString(client.getProfil())=="particulier") {
				errors.put("global", "Une erreur s'est produite.");
			} else {
				//Recuperation de la page et du nombre d'elements par page et du tri
				String pageStr = request.getParameter("page");
				String nbParPageStr = request.getParameter("nbParPage");
				String tri = request.getParameter("tri");
				int page;
				int nbParPage;
				if (pageStr == null) {
					pageStr = "1";
				}

				if (nbParPageStr == null) {
					nbParPageStr = "20";
				}

				if (tri == null || tri.equals("")) {
					tri = "DATEDESC";
				}

				try {
					page = Integer.parseInt(pageStr);
				} catch (NumberFormatException e) {
					page = 1;
				}

				try {
					nbParPage = Integer.parseInt(nbParPageStr);
				} catch (NumberFormatException e) {
					nbParPage = 20;
				}
				try {

					int billsNumber = uh.countDevisPro(String.valueOf(client.getNumCli()));
					int maxPage = (int) Math.ceil((double) billsNumber / (double) nbParPage);
					if (maxPage == 0) maxPage = 1;
					if (page > maxPage) page = maxPage;
					List<Devis> devisCollection = uh.getDevis(String.valueOf(client.getNumCli()), String.valueOf(page), String.valueOf(nbParPage), tri);
					request.setAttribute("devis", devisCollection);
					request.setAttribute("currentPage", page);
					request.setAttribute("maxPage", maxPage);
					request.setAttribute("nbParPage", nbParPage);
					request.setAttribute("nbTotal", billsNumber);
				} catch (DAOException e) {
					Logger.getLogger(this.getClass().getName()).severe("[ERROR] " + e.getMessage());
					errors.put("global", "Une erreur s'est produite.");
				}
			}
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	public void getBL(HttpServletRequest request) {
		Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
		if (client != null) {
			if (Integer.toString(client.getProfil()).equals("particulier")) {
				errors.put("global", "Une erreur s'est produite.");
			} else {
				//Recuperation de la page et du nombre d'elements par page et du tri
				String pageStr = request.getParameter("page");
				String nbParPageStr = request.getParameter("nbParPage");
				String tri = request.getParameter("tri");
				int page;
				int nbParPage;
				if (pageStr == null) {
					pageStr = "1";
				}

				if (nbParPageStr == null) {
					nbParPageStr = "20";
				}

				if (tri == null || tri.equals("")) {
					tri = "DESC";
				}

				try {
					page = Integer.parseInt(pageStr);
				} catch (NumberFormatException e) {
					page = 1;
				}

				try {
					nbParPage = Integer.parseInt(nbParPageStr);
				} catch (NumberFormatException e) {
					nbParPage = 20;
				}
				try {
					int blNumber = uh.countBLPro(String.valueOf(client.getNumCli()));
					int maxPage = (int) Math.ceil((double) blNumber / (double) nbParPage);
					if (maxPage == 0) maxPage = 1;
					if (page > maxPage) page = maxPage;
					List<BonDeLivraison> BLCollection = uh.getBL(String.valueOf(client.getNumCli()), String.valueOf(page), String.valueOf(nbParPage), tri);
					request.setAttribute("BLS", BLCollection);
					request.setAttribute("currentPage", page);
					request.setAttribute("maxPage", maxPage);
					request.setAttribute("nbParPage", nbParPage);
					request.setAttribute("nbTotal", blNumber);
				} catch (DAOException e) {
					Logger.getLogger(this.getClass().getName()).severe("[ERROR] " + e.getMessage());
					errors.put("global", "Une erreur s'est produite.");
				}
			}
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	public void getEncours(HttpServletRequest request) {
		if (request.getSession().getAttribute("client") != null) {
			// TODO Recuperation de l'encours
			request.setAttribute("encours", null);
		} else {
			errors.put("global", "Vous n'êtes pas connecté.<a href='login' >Connexion</a>");
		}
	}

	private void sendConfirmMail(String mail, String link) throws Exception {
		//Get the html mail and replace [CONFIRM_LINK] with actual confirmation link
		InputStream fileStream = context.getResourceAsStream("/WEB-INF/mailing/confirmMailAddress.html");
		BufferedReader br = new BufferedReader(new InputStreamReader(fileStream, "UTF-8"));
		String content = br.lines().collect(Collectors.joining(System.lineSeparator())).replace("[CONFIRM_LINK]", link);
		MailgunHandler.sendHtmlMail("Confirmez votre adresse mail", content, mail);
	}
}
