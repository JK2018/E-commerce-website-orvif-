package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.UnitesFacturation;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.UnitesFacturationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitesFactuHelper {


    @Autowired
    private UnitesFacturationRepository ufr;



    public UnitesFacturation getById(int id) {
        UnitesFacturation ret = null;
        try {
            ret = ufr.findById(id).get();
        }catch (Exception e){
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }









    public List<UnitesFacturation> getAll() {
        List<UnitesFacturation> ret = null;
        try {
            ret = ufr.findAll();
        }catch (Exception e){
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }
















}
