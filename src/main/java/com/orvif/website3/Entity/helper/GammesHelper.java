package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Gammes;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.GammesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class GammesHelper {



    @Autowired
    private GammesRepository gr;





    public Gammes getById(int id) {
        Gammes ret = null;
        try {
            ret = gr.findById(id).get();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public ArrayList<Gammes> getByMarque(int id) {
        ArrayList<Gammes> ret = new ArrayList<>();
        try {
            ret = gr.getByMarque(id);
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when fetching gammes (brand id : " + id + ")");
        }
        return ret;
    }



    public void add(Gammes gamme) {
        try {
            gr.save(gamme);
            gr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when inserting gamme \"" + gamme.getLibelle() + "\".");
        }
    }















}
