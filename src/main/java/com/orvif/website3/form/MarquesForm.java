/**
 *
 */
package com.orvif.website3.form;



import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Entity.helper.DocumentHelper;
import com.orvif.website3.Entity.helper.MarquesHelper;
import com.orvif.website3.Repository.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.Addressing;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ORVIFSTAGIERE
 */
public class MarquesForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private MarquesHelper marqueDao;
	@Autowired
	private DocumentHelper documentDao;


	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}


	public MarquesForm(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void getAllMarque(HttpServletRequest request) {
		try {
			//Recuperation des marques
			List<Marques> marqueCollection = marqueDao.getAll();
			request.setAttribute("marques", marqueCollection);
		} catch (Exception e) {
			errors.put("global", "Une erreur est survenue.");
		}

	}

}
