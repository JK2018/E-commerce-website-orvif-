package com.orvif.website3.form;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.Repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class AdminGestionUsersForm extends Form {
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursRepository userDao;
	@Autowired
	private UtilisateursHelper uh;

	private final String champ_action = "action";
	private final String champ_id_user = "idUser";
	private final String champ_password = "password";
	private final String champ_check_password = "password2";
	private final String champ_nom = "nom";
	private final String champ_prenom = "prenom";
	private final String champ_login = "login";
	private final String champ_profil = "profil";

	public AdminGestionUsersForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}

	public String gererRequete(HttpServletRequest request) {
		String action = getValeurChamp(request, champ_action);
		if (action != null) {
			if (action.equals("removeUser")) {
				String json = removeUser(request);
				return json;
			} else if (action.equals("newPsw")) {
				String json = changePassword(request);
				return json;
			} else if (action.equals("newUser")) {
				String json = newUser(request);
				return json;
			} else {
				return "";
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	private String newUser(HttpServletRequest request) {
		try {
			Map<String, Map<String, String>> json = new HashMap<String, Map<String, String>>();
			Map<String,String> eachErrors = new HashMap<String,String>();
			Utilisateurs newUser = new Utilisateurs();
			newUser.setNom(getValeurChamp(request, champ_nom));
			newUser.setPrenom(getValeurChamp(request, champ_prenom));
			newUser.setLogin(getValeurChamp(request, champ_login));
			//V�rification de chaque donn�es
			if(newUser.getNom() == null) {
				eachErrors.put("Nom", "Veuillez entrer un nom.");
			}
			if(newUser.getPrenom() == null) {
				eachErrors.put("Prénom", "Veuillez entrer un prénom.");
			}
			if(newUser.getLogin() == null) {
				eachErrors.put("Identifiant", "Veuillez entrer run identifiant.");
			}
			int profil = 0;
			try {
				profil = Integer.parseInt(getValeurChamp(request, champ_profil));
			}catch (NumberFormatException e) {
				eachErrors.put("profil", "Veuillez sélectionner un profil valide.");
			}
			
			
			// Traitement du mdp
			String passwd = getValeurChamp(request, champ_password);
			String passwdCheck = getValeurChamp(request, champ_check_password);
			if (passwd != null && passwdCheck != null) {
				if (passwd.equals(passwdCheck)) {
					if(eachErrors.isEmpty()) {
						String salt1 = generateSalt(10);
						String salt2 = generateSalt(10);
						String newPsw = "";
						newPsw = cryptPassw(salt1 + passwd + salt2);
						newUser.setMdp(newPsw);
						newUser.setSalt1(salt1);
						newUser.setSalt2(salt2);
						if(!uh.createAdminAccount(newUser,profil)) {
							eachErrors.put("Requête", "Une erreur est survenu lors de l'ajout en base de donnée. Veuillez réessayer.");
						}
					}
				}else {
					eachErrors.put("Mot de passe", "Le mot de passe et la vérification ne correspondent pas");
				}
			}else {
				eachErrors.put("Mot de passe", "Veuillez entrer une valeur pour le mot de passe ET la vérification.");
			}
			if(!eachErrors.isEmpty()) {
				json.put("error", eachErrors);
			}
			return new Gson().toJson(json);
		} catch (Exception e) {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Une erreur est survenue. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	private String removeUser(HttpServletRequest request) {
		String id = getValeurChamp(request, champ_id_user);
		if (id != null && checkInteger(id)) {
			try {

				if (uh.removeAdminUser(Integer.parseInt(id))) {
					Map<String, String> json = new HashMap<String, String>();
					return new Gson().toJson(json);
				} else {
					Map<String, String> json = new HashMap<String, String>();
					json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
					return new Gson().toJson(json);
				}
			} catch (Exception e) {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
				return new Gson().toJson(json);
			}
		} else {
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "La suppression n'a pas fonctionnée. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

	public void getUsers(HttpServletRequest request) {
		try {

			List<Utilisateurs> userCollection = uh.getUserAdminCollection();
			request.setAttribute("users", userCollection);
		} catch (Exception e) {
			request.setAttribute("global", "Une erreur est survenue, veuillez réessayer.");
		}

	}

	public String changePassword(HttpServletRequest request) {
		try {
			int idUser = Integer.parseInt(getValeurChamp(request, champ_id_user));
			String newPasswd = getValeurChamp(request, champ_password);
			String newPasswdCheck = getValeurChamp(request, champ_check_password);
			if (newPasswd != null && newPasswdCheck != null) {
				if (newPasswd.equals(newPasswdCheck)) {
					String salt1 = generateSalt(10);
					String salt2 = generateSalt(10);
					String newPsw = "";
					newPsw = cryptPassw(salt1 + newPasswd + salt2);
					if (this.uh.updateAdminPassw(idUser, newPsw, salt1, salt2)) {
						Map<String, String> json = new HashMap<String, String>();
						return new Gson().toJson(json);
					} else {
						// Erreur
						Map<String, String> json = new HashMap<String, String>();
						json.put("error", "Une erreur est survenue. Veuillez réessayer.");
						return new Gson().toJson(json);
					}
				} else {
					Map<String, String> json = new HashMap<String, String>();
					json.put("error", "Les deux mots de passes ne correspondent pas.");
					return new Gson().toJson(json);
				}
			} else {
				Map<String, String> json = new HashMap<String, String>();
				json.put("error", "Veuillez entrer une valeur pour le mot de passe ET la vérification.");
				return new Gson().toJson(json);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Un probl�me est survenu. Veuillez réessayer.");
			return new Gson().toJson(json);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Un probl�me est survenu. Veuillez réessayer.");
			return new Gson().toJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> json = new HashMap<String, String>();
			json.put("error", "Un probl�me est survenu. Veuillez réessayer.");
			return new Gson().toJson(json);
		}
	}

}
