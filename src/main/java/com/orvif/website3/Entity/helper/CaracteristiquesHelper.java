package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Repository.CaracteristiquesRepository;
import com.orvif.website3.Repository.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CaracteristiquesHelper {


    @Autowired
    CaracteristiquesRepository cr;


    public ArrayList<Caracteristiques> getByProduit(int p) {
        ArrayList<Caracteristiques> ret = new ArrayList<>();
        try {
            ret = cr.getByProduit(p);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






}
