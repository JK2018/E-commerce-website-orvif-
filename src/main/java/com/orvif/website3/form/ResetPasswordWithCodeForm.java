/**
 *
 */
package com.orvif.website3.form;


import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marijon Etienne
 */
public class ResetPasswordWithCodeForm extends Form {
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursHelper userDao;
	@Autowired
	private CodeConfirmHelper codeDao;

	private final String champ_psw = "psw";
	private final String champ_psw_check = "psw2";
	private String result;

	public String getUserMail() {
		return userMail;
	}

	private String userMail;

	private Map<String, String> errors = new HashMap<String, String>();

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public ResetPasswordWithCodeForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}

	public void resetPassword(HttpServletRequest request) {
		String psw = getValeurChamp(request, champ_psw);
		String psw2 = getValeurChamp(request, champ_psw_check);
		if (psw != null && psw2 != null) {
			if (checkpsw(psw, psw2)) {
				String salt1 = generateSalt(10);
				String salt2 = generateSalt(10);
				String newPsw = "";
				try {
					newPsw = cryptPassw(salt1 + psw + salt2);
					String code = request.getParameter("c");
					if (code != null) {
						CodeConfirm codeConf = codeDao.getByCode(code);
						if (codeConf != null && codeConf.getTypeCode().equals("passwordforgot")) {
							Date today = new Date();
							if (today.before(codeConf.getDate())) {
								// Tout est bon, on peut changer le mdp
								Utilisateurs user = userDao.getById(codeConf.getClient());

								if (userDao.resetPassword(codeConf.getClient(), newPsw, salt1, salt2)) {
									codeDao.removeByCode(codeConf.getCode());
									this.userMail = user.getMail();
									result = "Mot de passe chang&eacute; avec succès.";
								} else {
									result = "Une erreur est survenue, veuillez r&eacute;essayer.";
									errors.put("global", "Une erreur est survenue, veuillez r&eacute;essayer.");
								}
							}
						}
					}

				} catch (NoSuchAlgorithmException e) {
					errors.put("global", "Une erreur est survenue, veuillez r&eacute;essayer.");
				}
			} else {
				result = "Les deux mots de passes sont diff&eacute;rents.";
				errors.put(champ_psw, "Les deux mots de passes sont diff&eacute;rents.");
			}
		} else {
			result = "Veuillez entrer un mot de passe dans les deux champs.";
			errors.put(champ_psw, "Veuillez entrer un mot de passe dans les deux champs.");
		}
	}

}
