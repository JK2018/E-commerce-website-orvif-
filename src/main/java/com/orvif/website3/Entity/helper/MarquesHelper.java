package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DocumentRepository;
import com.orvif.website3.Repository.MarquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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







    public Marques getById(int id) {
        Marques ret = null;
        try {
            ret = mr.getById(id);
            if (ret.getLogo() != dr.getLogoMarques(ret.getIdMarques())) {
                ret.setLogo(dr.getLogoMarques(ret.getIdMarques()));
            }
            System.out.println( "LOGO in mh :" +dh.getLogoMarque(ret.getIdMarques()) + "/   id marques :" +ret.getIdMarques());
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public void add(Marques marque1) throws DAOException {
        try {
            if (marque1.getLogo() != null) {
                int idvis = marque1.getLogo().getId();
                mr.add(idvis,marque1.getNom(), marque1.isDisplay() );
            } else {
                mr.add2(marque1.getNom(), marque1.isDisplay() );

            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception when adding brand.");
        }
    }









}
