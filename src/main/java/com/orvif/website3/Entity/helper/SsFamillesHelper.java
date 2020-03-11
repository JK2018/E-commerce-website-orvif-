package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.SsFamilles;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.SsFamillesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class SsFamillesHelper {

@Autowired
private SsFamillesRepository sfr;

@Autowired
private CategoriesHelper ch;




    public ArrayList<SsFamilles> getByFamille(int famille) {
        ArrayList<SsFamilles> ret = new ArrayList<SsFamilles>();
        ArrayList<SsFamilles> array = new ArrayList<SsFamilles>();
        try {
            sfr.getByFamille(famille);
            //connexion = daoFactory.getConnection();
            for(SsFamilles sf : array){
               /** if (sf.getLibelle().toUpperCase().equals("NA")) {
                    continue;
                }**/

                sf.setCategoriesByIdSsfamilles(ch.getBySousFamille(sf.getIdSsfamilles()));

            }

        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }








    public SsFamilles getById(int id) {
        SsFamilles ret = null;
        try {
            ret = sfr.findById(id).get();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }









    public SsFamilles getByCategorie(int id) {
        SsFamilles ret = null;
        try {
            ret = sfr.getByCategorie(id);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }








    public SsFamilles getByIdProduit(int idProduit) {
        SsFamilles ret = null;
        try {
            ret = sfr.getByIdProduit(idProduit);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public void add(SsFamilles sousFamille, int id_famille) throws DAOException {
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            sousFamille.setParentFamille(id_famille);
            ch.addAll(sousFamille);
            sfr.save(sousFamille);
            sfr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding sub family : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding sub family.");
        }
    }







    public void addAll(Familles famille) {
        try{
        sfr.saveAll(famille.getSsFamillesByIdFamilles());
        sfr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding sub family : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding sub family.");
        }
    }

























}
