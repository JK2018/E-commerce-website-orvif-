/**
 *
 */
package com.orvif.website3.form;


import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.AdresseHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.AdressesRepository;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Marijon Etienne
 */
public class ActionClientForm extends Form {


	@Autowired
	private UtilisateursRepository userDao;

	@Autowired
	private AdressesRepository adresseDao;

	@Autowired
	private AdresseHelper ah;

	@Autowired
	private UtilisateursHelper uh;

	private final String champ_choix = "choix";
	private final String champ_id_adresse = "idAdresse";

	private Map<String, String> errors = new HashMap<>();

	public Map<String, String> getErrors() {
		return errors;
	}

	/**public ActionClientForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}**/

	public void handleAction(HttpServletRequest request) {
		String choix = getValeurChamp(request, champ_choix);
		if (choix != null) {
			if (choix.equals("removeAdresse")) {
				removeAdresse(request);
			} else if (choix.equals("changePassword")) {
				this.changePassword(request);
			}else{
				errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer.");
			}
		}else{
			errors.put("global", "Une erreur est survenue. Veuillez r&eacute;essayer.");
		}
	}

	private void removeAdresse(HttpServletRequest request) {
		String idAdresse = getValeurChamp(request, champ_id_adresse);
		if (idAdresse != null) {
			if (checkInteger(idAdresse)) {
				//this.adresseDao = this.daoFactory.getAdresseDao();
				int id = Integer.parseInt(idAdresse);
				if (!this.ah.remove(id)) {
					errors.put("global", "La suppression n'a pas fonctionné.");
				}
			} else {
				errors.put("global", "Une erreur est survenue.");
			}
		} else {
			errors.put("global", "Une erreur est survenue");
		}
	}

	private void changePassword(HttpServletRequest request) {
		String newPassword = getValeurChamp(request, "psw");
		String newPasswordCheck = getValeurChamp(request, "pswCheck");
		if (newPassword == null) {
			errors.put("global", "Veuillez entrer un mot de passe.");
		}
		if (newPasswordCheck == null) {
			errors.put("global", "Veuillez confirmer votre nouveau mot de passe.");
		}
		if (errors.isEmpty()) {
			if (checkpsw(newPassword, newPasswordCheck)) {
				if (checkpswvalid(newPassword)) {
					try {

						Utilisateurs client = (Utilisateurs) request.getSession().getAttribute("client");
						String salt1 = generateSalt(10);
						String salt2 = generateSalt(10);
						String newMdp = cryptPassw(salt1 + newPassword + salt2);
						if (!uh.resetPassword(client.getIdUtilisateurs(), newMdp, salt1, salt2)) {
							errors.put("global", "Une erreur est survenue.");
						}
					} catch (DAOException e) {
						Logger.getLogger(this.getClass().getName()).severe("Unable to change password[ActionClientForm.java:90] : " + e.getMessage());
						errors.put("global", "Impossible de changer votre mot de passe. Veuillez r&eacute;essayer ult&eacute;rieurement.");
					} catch (NoSuchAlgorithmException e1) {
						Logger.getLogger(this.getClass().getName()).severe("Unable to crypt password[ActionClientForm.java:93] : " + e1.getMessage());
						errors.put("global", "Impossible de changer votre mot de passe. Veuillez r&eacute;essayer ult&eacute;rieurement.");
					}
				} else {
					errors.put("global", "Le mot de passe n'est pas valide.");
				}
			} else {
				errors.put("global", "Les deux mots de passes ne correspondent pas.");
			}
		}
	}
}
