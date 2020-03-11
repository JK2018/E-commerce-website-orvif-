package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.FamillesRepository;
import com.orvif.website3.Repository.SsFamillesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class FamillesHelper {

    @Autowired
    private FamillesRepository fr;

    @Autowired
    private SsFamillesRepository sfr;



    public Familles getByProduit(int idProduit) {
        Familles ret = null;
        try {
            ret = fr.getByProduit(idProduit);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public ArrayList<Familles> getAll() {
        ArrayList<Familles> ret = new ArrayList<>();
        try {
           ArrayList<Familles> af = fr.findAllSorted();
           for(Familles f : af){
               if (f.getLibelle().toUpperCase().equals("NA")) {
                   continue;
               }
               Familles famille = new Familles();
               famille.setIdFamilles(f.getIdFamilles());
               famille.setLibelle(f.getLibelle());
               famille.setSsFamillesByIdFamilles(sfr.getByFamille(famille.getIdFamilles()));
               ret.add(famille);
           }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public Familles getById(int id) {
        Familles ret = null;
        try {
            ret = fr.findById(id).get();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public Familles getBySousFamille(int id) {
        Familles ret = null;
        try {
            ret = fr.getBySousFamille(id);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public void add(Familles famille) throws DAOException {
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            fr.save(famille);
            fr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding family : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding family.");
        }
    }





    public void addAll(ArrayList<Familles> familleList) {
        try{
        fr.saveAll(familleList);
        fr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new DAOException("Null pointer when adding family : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding family.");
        }
    }




}
