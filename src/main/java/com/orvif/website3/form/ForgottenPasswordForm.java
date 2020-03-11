package com.orvif.website3.form;



import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.CodeConfirmRepository;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ForgottenPasswordForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursHelper userDao;
	@Autowired
	private CodeConfirmHelper codeDao;

	private final String champ_mail = "mail";
	private String result;

	public String getSentTo() {
		return sentTo;
	}

	private String sentTo = "";

	private Map<String, String> errors = new HashMap<String, String>();

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public ForgottenPasswordForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}

	public void generateRecoveryCode(HttpServletRequest request) {
		String mail = getValeurChamp(request, champ_mail);
		if (mail != null) {
			// Recuperation de l'utilisateur avec son adresse mail
			try {

				Utilisateurs user = userDao.getByMail(mail);
				if (user == null) {
					System.out.println("1");
					errors.put("global", "Adresse mail inconnue.");
				} else {
					CodeConfirm codeConf = genererCode("passwordforgot", user.getIdUtilisateurs(), codeDao);
					if (codeConf == null) {
						errors.put("global", "Un probl&egrave;me est survenu. Veuillez r&eacute;essayer.");
					} else {
						// TODO envoyer un mail au lieu d'afficher le code
						result = "Nous vous avons envoy&eacute; un mail. Veuillez aller le consulter afin de r&eacute;initialiser votre mot de passe. Code : "
								+ codeConf.getCode();
						this.sentTo = mail;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("2");
				errors.put("global", "Adresse mail inconnue.");
			}
		} else {
			errors.put(champ_mail, "Veuillez entrer une adresse mail.");
		}
	}
}
