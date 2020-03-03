package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.FamillesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamillesHelper {

    @Autowired
    private FamillesRepository fr;



    public Familles getByProduit(int idProduit) {
        Familles ret = null;
        try {
            ret = fr.getByProduit(idProduit);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }








}
