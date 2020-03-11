/**
 *
 */
package com.orvif.website3.form;



import com.orvif.website3.Entity.Utilisateurs;
import com.orvif.website3.Entity.helper.BloquedAccountException;
import com.orvif.website3.Entity.helper.ClosedAccountException;
import com.orvif.website3.Entity.helper.UtilisateursHelper;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * @author ORVIFSTAGIERE
 */
public class SignInForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private UtilisateursHelper userDao;

	private static final String champ_login = "login";
	private static final String champ_password = "psw";

	private String result;
	private boolean firstConnection = false;
	private boolean mailConfirmed = true;
	private boolean bloquedUser = false;
	private boolean closedUser = false;
	private Map<String, String> errors = new HashMap<>();

	public SignInForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}




	public Utilisateurs logUser(HttpServletRequest request) {
		String login = getValeurChamp(request, champ_login);
		String psw = getValeurChamp(request, champ_password);
		Utilisateurs user = null;
		//Check if login is a mail adress
		//If mail address : look in SQL database and login
		//If NOT : try finding account in NX and call specific method for first connection
		if (checkMail(login)) {
			try {
				user = userDao.getByMail(login);
			} catch (ClosedAccountException e) {
				closedUser = true;
				request.setAttribute("mail", login);
			} catch (BloquedAccountException e) {
				bloquedUser = true;
				request.setAttribute("mail", login);
			} catch (DAOException e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getClass().getName() + " exception.");
			}
		} else {
			try {
				if (!userDao.isLoginRegistered(login)) {
					user = userDao.getByNx(login, psw);
					firstConnection = true;
					return user;
				}
			} catch (DAOException e) {
				user = null;
				Logger.getLogger(this.getClass().getName()).warning(e.getMessage());
			} catch (Exception e) {
				user = null;
				Logger.getLogger(this.getClass().getName()).severe(e.getClass().getName() + " exception.");
			}
		}

		if (user == null) {
			// User not found or first connection
			user = new Utilisateurs();
			user.setMail(login);
			errors.put("global", "Adresse mail ou mot de passe incorrect.");
		} else {
			// User found, now have to check password
			try {
				String pswTest = cryptPassw(user.getSalt1() + psw + user.getSalt2());
				if (!pswTest.equals(user.getMdp())) {
					errors.put("global", "Adresse mail ou mot de passe incorrect.");
					Utilisateurs tempUser = new Utilisateurs();
					tempUser.setLogin(user.getLogin());
					user = tempUser;
				}
			} catch (Exception e) {
				errors.put("global", "La connexion a échouée. Veuillez réessayer.");
			}
			user.setMdp(null);
			user.setSalt1(null);
			user.setSalt2(null);
		}

		if (errors.isEmpty()) {
			result = user.getNom() + " " + user.getPrenom() + " successfully logged in.";
		} else {
			result = errors.get("global");
		}

		return user;
	}

	public Utilisateurs logAdmin(HttpServletRequest request) {

		String login = getValeurChamp(request, champ_login);
		String psw = getValeurChamp(request, champ_password);
		Utilisateurs user = userDao.getAdminByLogin(login);
		if (user == null) {
			errors.put("global", "L'utilisateur n'existe pas.");
		} else {
			// User found, check password
			try {
				String pswTest = cryptPassw(user.getSalt1() + psw + user.getSalt2());
				if (!pswTest.equals(user.getMdp())) {
					errors.put("global", "Identifiant et/ou mot de passe est incorrect.");
				}
			} catch (Exception e) {
				errors.put("global", "Identifiant et/ou mot de passe est incorrect.");
			}
		}
		if (errors.isEmpty()) {
			// Sign in ok
			result = "Bienvenue " + user.getNom() + " " + user.getPrenom() + ".";
		} else {
			result = errors.get("global");
		}
		user.setMdp(null);
		return user;
	}

	public boolean isFirstConnection() {
		return firstConnection;
	}

	public boolean isMailConfirmed() {
		return mailConfirmed;
	}

	public boolean isBloquedUser() {
		return bloquedUser;
	}

	public boolean isClosedUser() {
		return closedUser;
	}
}
