package com.orvif.website3.form;


import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.helper.CodeConfirmHelper;
import com.orvif.website3.Repository.CodeConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Form {

	@Autowired
	private CodeConfirmHelper codeDao;

	protected boolean checkpsw(String psw1, String psw2) {
		try{
			return psw1.equals(psw2);
		}catch (NullPointerException e){
			return false;
		}
	}

	/**
	 * Permet de recuperer la valeur d'un champ de formulaire a partir du nom de champ
	 *
	 * @param request
	 * @param nomChamp
	 * @return la valeur du champ
	 */
	protected static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur.trim();
		}
	}

	/**
	 * @param request
	 * @param nomChamp
	 * @return
	 */
	protected static String[] getValeurChampMultiple(HttpServletRequest request, String nomChamp) {
		return request.getParameterValues(nomChamp);
	}

	/**
	 * Permet de crypter un password
	 *
	 * @param passw
	 * @return le password crypte
	 * @throws NoSuchAlgorithmException
	 */
	protected String cryptPassw(String passw) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(passw.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * Permet de verifier a partir d'un string si on a bien un int
	 *
	 * @param str
	 * @return true si on a un int, false sinon
	 */
	protected boolean checkInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Permet de verifier a partir d'un String si on a bien un float
	 *
	 * @param str
	 * @return true si on a un float, false sinon
	 */
	protected boolean checkFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Permet de verifier qu'un String a bien la forme d'un mail
	 *
	 * @param str
	 * @return true il a la forme, false sinon
	 */
	protected boolean checkMail(String str) {
		return str.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
	}

	/**
	 * Method which generate a random String
	 *
	 * @param length the length of the String
	 * @return A random String
	 */
	protected String generateSalt(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String pass = "";
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * 62);
			pass += chars.charAt(i);
		}
		return pass;
	}

	/**
	 * Permet de verifier si un code de confirmation existe ou non
	 *
	 * @param code
	 * @return true s'il n'existe pas, false s'il existe deja
	 */
	private boolean IsCodeUnique(String code) {
		return this.codeDao.isUnique(code);
	}

	/**
	 * Permet de generer soit un code de confirmation de mail (type "confirmmail") ou un code de recuperation de mot de passe (type = "passwordforgot")
	 *
	 * @param type_code
	 * @param client
	 * @param codeDao
	 * @return le code si ca a fonctionne, null sinon
	 */
	protected CodeConfirm genererCode(String type_code, int client, CodeConfirmHelper codeDao) {
		CodeConfirm codeRet = new CodeConfirm();
		this.codeDao = codeDao;
		String code = null;
		do {
			code = generateSalt(25);
		} while (!IsCodeUnique(code));
		codeRet.setCode(code);
		codeRet.setClient(client);
		codeRet.setTypeCode(type_code);
		if (codeDao.add(codeRet)) {
			return codeRet;
		} else {
			return null;
		}
	}

	protected boolean checkpswvalid(String psw) {
		// TODO verifier validite du psw (definir les critere)
		return true;
	}
}
