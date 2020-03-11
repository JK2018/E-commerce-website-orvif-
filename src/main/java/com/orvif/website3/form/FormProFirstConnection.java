package com.orvif.website3.form;



import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.CodeConfirmRepository;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FormProFirstConnection extends Form {
	private final String MAIL_FIELD = "mail";
	private final String FIRSTNAME_FIELD = "prenom";
	private final String LASTNAME_FIELD = "nom";
	private final String PHONE_FIELD = "telephone";
	private final String PASSWORD_FIELD = "password";
	private final String CHECKPASSWORD_FIELD = "password2";

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursHelper uh;
	@Autowired
	private CodeConfirmRepository ccr;
	@Autowired
	private CodeConfirmHelper cch;

	private Utilisateurs user = new Utilisateurs();

	private Map<String, String> errors = new HashMap<>();

	private String finalMessage;

	public FormProFirstConnection(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void registerUser(HttpServletRequest request) {
		user = (Utilisateurs) request.getSession().getAttribute("profileRecovering");
		checkFields(request);
		if (errors.isEmpty()) {
			try {
				//TODO change "inscrireUtilisateur" and "inscrireUtilisateurPro" to insert adresse at the same time (transaction)
				int customerId = uh.inscrireUtilisateurPro(user);
				CodeConfirm codeConfirmation = genererCode("confirmmail", customerId, cch);
				finalMessage = "Compte cr&eacute;&eacute; avec succ&egrave;s. Veuillez consulter vos mail afin de confirmer votre adresse mail.";
				if (codeConfirmation != null) {
					//TODO send mail
					Logger.getLogger(this.getClass().getName()).info("Lien de confirmation : mailConfirm?c=" + codeConfirmation.getCode());
				} else {
					Logger.getLogger(this.getClass().getName()).severe("Could not generate mail confirmation code.");
				}
			} catch (DAOException e) {
				Logger.getLogger(this.getClass().getName()).severe("DAO error while registering user : " + e.getMessage());
				errors.put("global", "Une erreur s'est produite. Veuillez r&eacute;essayer.");
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getClass().getName() + " error while registering user.");
				errors.put("global", "Une erreur s'est produite. Veuillez r&eacute;essayer.");
			}
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	private void checkFields(HttpServletRequest request) {
		//Mail check
		String mail = getValeurChamp(request, MAIL_FIELD);
		if (!checkMail(mail)) {
			errors.put(MAIL_FIELD, "L'adresse mail n'est pas correcte.");
		}
		user.setMail(mail);
		//Firstname check
		String firstname = getValeurChamp(request, FIRSTNAME_FIELD);
		if (firstname == null) {
			errors.put(FIRSTNAME_FIELD, "Le pr&eacute;nom n'est pas renseign&eacute;.");
		}
		user.setPrenom(firstname);
		//Lastname check
		String lastname = getValeurChamp(request, LASTNAME_FIELD);
		if (lastname == null) {
			errors.put(LASTNAME_FIELD, "Le nom n'est pas renseign&eacute;.");
		}
		user.setNom(lastname);
		//Phone check
		String phone = getValeurChamp(request, PHONE_FIELD);
		if (phone == null) {
			errors.put(PHONE_FIELD, "Le t&eacute;l&eacute;phone n'est pas renseign&eacute;.");
		}
		user.setTelephone(phone);
		//Password check
		String password = getValeurChamp(request, PASSWORD_FIELD);
		String passwordCheck = getValeurChamp(request, CHECKPASSWORD_FIELD);
		if (checkpsw(password, passwordCheck)) {
			if (!checkpswvalid(password)) {
				errors.put(PASSWORD_FIELD, "Le mot de passe n'est pas valide.");
			} else {
				//No error, password generation
				String salt1 = generateSalt(10);
				String salt2 = generateSalt(10);
				try {
					user.setSalt1(salt1);
					user.setSalt2(salt2);
					user.setMdp(cryptPassw(salt1 + password + salt2));
				} catch (NoSuchAlgorithmException e) {
					errors.put("global", "Une erreur est survenue, veuillez r&eacute;essayer.");
				}
			}
		} else {
			errors.put(PASSWORD_FIELD, "Les deux mots de passes ne sont pas &eacute;gaux.");
		}
	}

	public String getFinalMessage() {
		return finalMessage;
	}
}
