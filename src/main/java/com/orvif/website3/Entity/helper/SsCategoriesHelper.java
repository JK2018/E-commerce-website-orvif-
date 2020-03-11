package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Categories;
import com.orvif.website3.Entity.SsCategories;
import com.orvif.website3.Repository.CategoriesRepository;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.SsCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class SsCategoriesHelper {

@Autowired
private SsCategoriesRepository scr;

@Autowired
private CategoriesRepository cr;




    public ArrayList<SsCategories> getByCategorie(int id) {
        ArrayList<SsCategories> ret = new ArrayList<SsCategories>();
        try {
            ret = scr.getByCategorie(id);
                /**if (resultat.getString("libelle").toUpperCase().equals("NA")) {
                    continue;
                }**/
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public SsCategories getById(int id) {
        SsCategories ret = null;
        try {
            ret = scr.findById(id).get();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public SsCategories getByIdProduit(int idProduit) {
        SsCategories ret = null;
        try {
            ret = scr.getByIdProduit(idProduit);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public void add(SsCategories sousCategorie, int id_categorie) throws DAOException {
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            Categories c = cr.findById(id_categorie).get();
            sousCategorie.setCategoriesByParentCategorie(c);
            scr.save(sousCategorie);
            scr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding sub category : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding sub category.");
        }
    }






    public void addAll(Categories categorie) throws DAOException {
        try {
            scr.saveAll(categorie.getSsCategoriesByIdCategories());
            scr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding sub category : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding sub category.");
        }
    }












}
