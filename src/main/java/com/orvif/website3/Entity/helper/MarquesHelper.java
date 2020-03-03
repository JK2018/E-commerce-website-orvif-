package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DocumentRepository;
import com.orvif.website3.Repository.MarquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MarquesHelper {



@Autowired
private MarquesRepository mr;

@Autowired
private DocumentRepository dr;

@Autowired
private DocumentHelper dh;


    public ArrayList<Marques> getAll() {
        ArrayList<Marques> listM2 = new ArrayList<Marques>();
        ArrayList<Marques> listM = new ArrayList<Marques>();
        listM = mr.getAll();
        try {
                for (Marques m : listM) {
                    m.setDisplay(m.isDisplay() == true);
                    m.setLogo(dh.getLogoMarque(m.getIdMarques()));
                }

        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return listM;
    }





    public ArrayList<Marques> getAllDistinctLogos() {
        ArrayList<Marques> listM2 = new ArrayList<Marques>();
        ArrayList<Marques> listM = new ArrayList<Marques>();
        listM = mr.getAll();
        int compteur = 0;
        try {
            for (Marques m : listM) {
                m.setDisplay(m.isDisplay() == true);
                m.setLogo(dh.getLogoMarque(m.getIdMarques()));
                // does not show the variants of CONCEPT company that use the same logo.
                if(m.getNom().equalsIgnoreCase("CONCEPT AFIMO") || m.getNom().equalsIgnoreCase("CONCEPT ECOWATER") || m.getNom().equalsIgnoreCase("CONCEPT EUROTEAM") || m.getNom().equalsIgnoreCase("CONCEPT NEVELT") || m.getNom().equalsIgnoreCase("CONCEPT PAINI") || m.getNom().equalsIgnoreCase("CONCEPT SIAMP") ||  m.getNom().equalsIgnoreCase("CONCEPT VITRA")){
                    compteur ++; //do nithing
                }else{
                    listM2.add(m);
                }
            }

        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return listM2;
    }

















}
