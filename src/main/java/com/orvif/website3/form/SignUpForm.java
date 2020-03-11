package com.orvif.website3.form;



import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.Client;
import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.AdresseHelper;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.*;
import com.orvif.website3.service.MailgunHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SignUpForm extends Form {

	@Autowired
	private DAOFactory daoFactory;

	@Autowired
	private UtilisateursHelper userDao;

	@Autowired
	private AdresseHelper adresseDao;

	@Autowired
	private CodeConfirmHelper cch;

	@Autowired
	private CodeConfirmRepository ccr;

	private final String champ_login = "login";
	private final String champ_password = "psw";
	private final String champ_password_check = "psw2";
	private final String champ_nom = "nom";
	private final String champ_prenom = "prenom";
	private final String champ_mail = "mail";
	private final String champ_type = "type";
	private final String champ_telephone = "telephone";
	private final String champ_libelle_adresse = "libelle";
	private final String champ_nom_voie = "nomVoie";
	private final String champ_complement_voie = "complementVoie";
	private final String champ_code_postalAdresse = "codePostal";
	private final String champ_ville_adresse = "ville";
	private final String champ_pays_adresse = "pays";
	private final String champ_siren = "numberSiren";
	private final ServletContext context;

	private String result;
	private Map<String, String> errors = new HashMap<>();


	public SignUpForm(DAOFactory daoFactory, ServletContext context) {
		this.daoFactory = daoFactory;
		this.context = context;
	}

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public Utilisateurs inscrireUtilisateur(HttpServletRequest request) {
//		String login = getValeurChamp(request, champ_login);
		String psw = getValeurChamp(request, champ_password);
		String psw2 = getValeurChamp(request, champ_password_check);
		String nom = getValeurChamp(request, champ_nom);
		String prenom = getValeurChamp(request, champ_prenom);
		String mail = getValeurChamp(request, champ_mail);
		String type = getValeurChamp(request, champ_type);
		String telephone = getValeurChamp(request, champ_telephone);

		//Recuperation des adresses
		String[] libellesAdresse = getValeurChampMultiple(request, champ_libelle_adresse);
		String[] nomsVoieAdresse = getValeurChampMultiple(request, champ_nom_voie);
		String[] complementsAdresse = getValeurChampMultiple(request, champ_complement_voie);
		String[] codesPostalAdresse = getValeurChampMultiple(request, champ_code_postalAdresse);
		String[] villesAdresse = getValeurChampMultiple(request, champ_ville_adresse);
		String[] paysAdresse = getValeurChampMultiple(request, champ_pays_adresse);

		Utilisateurs user = new Utilisateurs();
//		if (login != null) {
//			if (checkLoginUnique(login)) {
//				user.setLogin(login);
//			} else {
//				user.setLogin(login);
//				errors.put(champ_login, "Identifiant déjà utilisé.");
//			}
//		} else {
//			errors.put(champ_login, "Veuillez entre un identifiant.");
//		}
		if (psw != null && psw2 != null) {
			if (checkpsw(psw, psw2)) {
				if (checkpswvalid(psw)) {
					String salt1 = generateSalt(10);
					String salt2 = generateSalt(10);
					String newPsw = "";
					try {
						newPsw = cryptPassw(salt1 + psw + salt2);
					} catch (NoSuchAlgorithmException e) {
						errors.put("global", "Une erreur est survenue, veuillez réessayer.");
					}
					user.setSalt1(salt1);
					user.setSalt2(salt2);
					user.setMdp(newPsw);
				} else {
					errors.put(champ_password, "Le mot de passe n'est pas valide.");
				}
			} else {
				errors.put(champ_password, "Les deux mots de passes sont différents.");
			}
		} else {
			errors.put(champ_password, "Veuillez entrer un mot de passe dans les deux champs.");
		}
		if (mail != null) {
			if (checkMail(mail)) {
				user.setMail(mail);
			} else {
				user.setMail(mail);
				errors.put(champ_mail, "Veuillez entrer une adresse mail valide.");
			}
		} else {
			errors.put(champ_mail, "Veuillez entrer une adresse mail.");
		}

		if (checkTelephone(telephone)) {
			user.setTelephone(telephone);
		} else {
			user.setTelephone(telephone);
			errors.put(champ_telephone, "Le numéro de téléphone n'est pas valide.");
		}

		if (type != null) {
			if (type.equals("pro")) {
				String siren = getValeurChamp(request, champ_siren);
				if (checkNumeroSiren(siren)) {
					user.setNumeroSiren(siren);
					user.setNumCli(1);
					user.setEtat("1");
					user.setProfil(3);
				} else {
					errors.put(champ_siren, "Le numéro de siren n'est pas valide");
				}
				user.setType_client("pro comptant orange");
			} else {
				user.setType_client("particulier");
				user.setProfil(0);
				user.setEtat("1");
				user.setNumCli(00001);
			}
		} else {
			errors.put(champ_type, "Veuillez renseigner le type de compte.");
		}
		user.setNom(nom);
		user.setPrenom(prenom);
		if (nom == null) {
			errors.put(champ_nom, "Veuillez renseigner votre nom.");
		}
		if (prenom == null) {
			errors.put(champ_prenom, "Veuillez renseigner votre prénom.");
		}

		List<Adresse> adresseCollection = new ArrayList<Adresse>();
		//Traitement des adresses
		if (libellesAdresse.length > 0) {
			int i;
			for (i = 0; i < libellesAdresse.length; i++) {
				boolean valid = true;
				Adresse a = new Adresse();
				a.setLibelle(libellesAdresse[i]);
				if (nomsVoieAdresse[i] != null && !nomsVoieAdresse[i].trim().equals("")) {
					a.setNomVoie(nomsVoieAdresse[i]);
				} else {
					valid = false;
				}
				a.setComplementVoie(complementsAdresse[i]);
				if (codesPostalAdresse[i] != null && !codesPostalAdresse[i].trim().equals("")) {
					a.setCodePostal(Integer.parseInt(codesPostalAdresse[i]));
				} else {
					valid = false;
				}
				if (villesAdresse[i] != null && !villesAdresse[i].trim().equals("")) {
					a.setVille(villesAdresse[i]);
				} else {
					valid = false;
				}
				if (paysAdresse[i] != null && !paysAdresse[i].trim().equals("")) {
					a.setPays(paysAdresse[i]);
				} else {
					valid = false;
				}
				if (valid) {
					adresseCollection.add(a);
				}
			}
		}


		if (errors.isEmpty()) {
			int clientId;
			if (user.getType_client().equals("particulier")) {
				clientId = userDao.inscrireUtilisateur(user);
			} else {
				try {
					clientId = userDao.inscrireUtilisateurPro(user);
				} catch (DAOException e) {
					clientId = -1;
					Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				}
			}
			if (clientId > 0) {
				CodeConfirm codeConfirmation = genererCode("confirmmail", clientId, cch);
				if (codeConfirmation == null) {
					//Inscription reussi mais code non genere
					if (!adresseCollection.isEmpty()) {
						int j = 0;
						boolean insertionAdresse = true;
						for (j = 0; j < adresseCollection.size(); j++) {
							try {
								int idAdresse = adresseDao.add(adresseCollection.get(j), clientId);
								if (idAdresse < 0)
									insertionAdresse = false;
							} catch (Exception e) {
								insertionAdresse = false;
							}
						}
						if (insertionAdresse) {
							result = "Vous avez été inscrit avec succès. Malheureusement la génération d'un lien de confirmation d'adresse mail a échouée. Veuillez vous connecter et aller dans la rubrique \"mon compte\" afin de redemander un mail de conirmation et profiter de toutes nos fonctionnalités.";
						} else {
							result = "Vous avez été inscrit avec succès. Malheureusement la génération d'un lien de confirmation d'adresse mail a échouée. Veuillez vous connecter et aller dans la rubrique \"mon compte\" afin de redemander un mail de conirmation et profiter de toutes nos fonctionnalités. De plus, une ou plusieurs adresses saisis n'ont pas été enregstrées.";
						}
					} else {
						result = "Vous avez été inscrit avec succès. Malheureusement la génération d'un lien de confirmation d'adresse mail a échouée. Veuillez vous connecter et aller dans la rubrique \"mon compte\" afin de redemander un mail de conirmation et profiter de toutes nos fonctionnalités.";
					}

				} else {
					if (!adresseCollection.isEmpty()) {
						int j = 0;
						boolean insertionAdresse = true;
						for (j = 0; j < adresseCollection.size(); j++) {
							try {
								int idAdresse = adresseDao.add(adresseCollection.get(j), clientId);
								if (idAdresse < 0)
									insertionAdresse = false;
							} catch (Exception e) {
								insertionAdresse = false;
							}
						}
						try {
							//Get domain (should be https://orvif.fr in the future)
							String domain = System.getProperty("website_domain");
							sendConfirmMail(user.getMail(), domain + "/mailConfirm?c=" + codeConfirmation.getCode());
						} catch (Exception e) {
							Logger.getLogger(getClass().getName()).severe("Error when trying to send the confirmation mail : " + e.getClass().getName());
							e.printStackTrace();
						}
						if (insertionAdresse) {
							result = "Un lien de confirmation vous a été envoyé par mail, veuillez confirmer votre compte pour pouvoir profiter de toutes nos fonctionnalités. Code : " + codeConfirmation.getCode() + ".";
						} else {
							result = "Un lien de confirmation vous a été envoyé par mail, veuillez confirmer votre compte pour pouvoir profiter de toutes nos fonctionnalités. Code : " + codeConfirmation.getCode() + " De plus, une ou plusieurs adresses saisis n'ont pas été enregstrées.";
						}
					} else {
						try {
							sendConfirmMail(user.getMail(), "/mailConfirm?c=" + codeConfirmation.getCode());
						} catch (Exception e) {
							Logger.getLogger(getClass().getName()).severe("Error when trying to send the confirmation mail : " + e.getClass().getName());
							e.printStackTrace();
						}
						result = "Un lien de confirmation vous a été envoyé par mail, veuillez confirmer votre compte pour pouvoir profiter de toutes nos fonctionnalités. Code : " + codeConfirmation.getCode();
					}
				}
			} else {
				Logger.getLogger(getClass().getName()).info("Empty error but client id -1");
				errors.put("global", "Un problème est survenu, veuillez réessayer.");
			}
		} else {
			Logger.getLogger(getClass().getName()).info("Error !");
			errors.entrySet().stream().forEach(stringStringEntry -> Logger.getLogger(getClass().getName()).info(stringStringEntry.getKey() + " => " + stringStringEntry.getValue()));
			result = "Un problème est survenu, veuillez réessayer.";
			errors.put("global", "Un problème est survenu, veuillez réessayer.");
		}
		return user;
	}

	private boolean checkLoginUnique(String login) {
		// Verifie si le login n'existe pas déjà
		return this.userDao.uniqueLogin(login);
	}

	private boolean checkTelephone(String tel) {
		if (tel != null) {
			Pattern pattern = Pattern.compile("(0|\\\\+33|0033)[1-9][0-9]{8}");
			Matcher mat = pattern.matcher(tel);
			return mat.matches();
		} else {
			return true;
		}
	}

	private boolean checkNumeroSiren(String siren) {
		return true;
	}

	private void sendConfirmMail(String mail, String link) throws Exception {
		//Get the html mail and replace [CONFIRM_LINK] with actual confirmation link
		InputStream fileStream = context.getResourceAsStream("/WEB-INF/mailing/confirmMailAddress.html");
		BufferedReader br = new BufferedReader(new InputStreamReader(fileStream, "UTF-8"));
		String content = br.lines().collect(Collectors.joining(System.lineSeparator())).replace("[CONFIRM_LINK]", link);
		MailgunHandler.sendHtmlMail("Confirmez votre adresse mail", content, mail);
	}
}
