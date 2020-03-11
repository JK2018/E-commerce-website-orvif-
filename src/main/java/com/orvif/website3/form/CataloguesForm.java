/**
 * 
 */
package com.orvif.website3.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.orvif.website3.Entity.Document;
import com.orvif.website3.Entity.helper.DocumentHelper;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marijon Etienne
 *
 */
public class CataloguesForm extends Form {

	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private DocumentRepository documentDao;
	@Autowired
	private DocumentHelper dh;

	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public CataloguesForm(DAOFactory daoFac) {
		this.daoFactory = daoFac;
	}

	public void getAllCatalogues(HttpServletRequest request) {
		try {
			List<Document> catalogueCollection = dh.getCatalogues();
			request.setAttribute("catalogues", catalogueCollection);
		} catch (Exception e) {
			errors.put("global", "Une erreur est survenue.");
		}
	}

}
