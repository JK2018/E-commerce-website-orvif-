package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.UnitesVente;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.UnitesVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitesVenteHelper {



    @Autowired
    private UnitesVenteRepository uvr;






    public UnitesVente getById(int id) {
        UnitesVente ret = null;
        try {
            ret = uvr.findById(id).get();
        }catch (Exception e){
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }







    public List<UnitesVente> getAll() {
        List<UnitesVente> ret = null;
        try {
            ret = uvr.findAll();
        }catch (Exception e){
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }














}
