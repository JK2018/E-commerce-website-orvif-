package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.AdressesClients;
import com.orvif.website3.Entity.Client;
import com.orvif.website3.Repository.AdresseClientsRepository;
import com.orvif.website3.Repository.AdressesRepository;
import com.orvif.website3.Repository.ClientRepository;
import com.orvif.website3.Repository.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AdresseHelper {

@Autowired
private AdressesRepository ar;

@Autowired
private ClientRepository cr;

@Autowired
private AdresseClientsRepository acr;




    public boolean remove(int idAdresse) {
        try {
            ar.deleteById(idAdresse);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return true;
    }








    public int add(Adresse a, int idClient) throws DAOException {
        int ret=-1;
        try {
            ar.save(a);
            ar.flush();
            Adresse a2 = ar.findWithoutId(a.getNomVoie(), a.getComplementVoie(), a.getCodePostal(), a.getVille(), a.getPays(), a.getLibelle());
            Client c = cr.findById(idClient).get();
            AdressesClients ac = new AdressesClients();
            ac.setClientByIdClient(c);
            ac.setAdresseByIdAdresse(a2);
            acr.save(ac);
            acr.flush();
            ret=a2.getIdAdresse();

        } catch (Exception e) {
            ret =-1;
            throw new DAOException(e.getClass().getName() + " exception occured when trying to add an address.");
        }
        return ret;
    }




    public boolean updateAdresse(Adresse a) {
        try {
            if(a.getIdAdresse()>0){
                ar.save(a);
                ar.flush();
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " error occured while trying to update address.");
        }
    }




    public List<Adresse> getByClient(int idClient) {
        List<Adresse> ret = new ArrayList<>();
        try {
            Client c = cr.findById(idClient).get();
            Collection<AdressesClients> array = c.getAdressesClientsById();
            for ( AdressesClients ac : array ) {
                Adresse a = ac.getAdresseByIdAdresse();
                ret.add(a);
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }










    public Adresse getById(int id) throws DAOException {
        try {
            Adresse a = ar.findById(id).get();
            return a;
        }  catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception while trying to fetch an address.");
        }
    }


}
