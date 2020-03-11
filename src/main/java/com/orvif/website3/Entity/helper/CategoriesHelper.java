package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Categories;
import com.orvif.website3.Entity.SsFamilles;
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
public class CategoriesHelper {

@Autowired
private CategoriesRepository cr;

@Autowired
private SsCategoriesHelper sch;



    public ArrayList<Categories> getBySousFamille(int id) {
        ArrayList<Categories> ret = new ArrayList<Categories>();
        try {
            ret = cr.getBySousFamille(id);
            for (Categories c : ret) {
                /** if(resultat.getString("libelle").toUpperCase().equals("NA")){
                 continue;
                 }**/
                c.setSsCategoriesByIdCategories(sch.getByCategorie(c.getIdCategories()));
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public Categories getById(int id) {
        Categories ret = null;
        try {
            ret = cr.findById(id).get();
            ret.setSsCategoriesByIdCategories(sch.getByCategorie(ret.getIdCategories()));
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public Categories getBySousCategorie(int id) {
        Categories ret = null;
        try {
            ret = cr.getBySousCategorie(id);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public Categories getByIdProduit(int idProduit) {
        Categories ret = null;
        try {
            ret = cr.getByIdProduit(idProduit);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public void add(Categories categorie, int id_sousFamille) throws DAOException {
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            categorie.setParentSsfamille(id_sousFamille);
            cr.save(categorie);
            cr.flush();
            sch.addAll(categorie); // check if it flushes
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding category : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding category.");
        }
    }






    public void addAll(SsFamilles sousFamille) {
        try {
            for (Categories categorie : sousFamille.getCategoriesByIdSsfamilles()) {
                categorie.setParentSsfamille( sousFamille.getIdSsfamilles());
                cr.saveAll(sousFamille.getCategoriesByIdSsfamilles());
            }
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding category : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding category.");
        }
    }














































}
