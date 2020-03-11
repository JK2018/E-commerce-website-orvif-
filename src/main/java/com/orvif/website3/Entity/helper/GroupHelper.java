package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Entity.Groupe;
import com.orvif.website3.Entity.GroupeCritere;
import com.orvif.website3.Entity.Produits;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.GroupeCritereRepository;
import com.orvif.website3.Repository.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupHelper {


    @Autowired
    private GroupeRepository gr;


    @Autowired
    private GroupeCritereRepository gcr;




    //PROBABLE ERROR ,  maybe just one flush at the end?
    public void add(Groupe g) throws DAOException {
        try {
            for (Produits p : g.getProducts()) {
                if (g.getId() == -1) {
                    gr.getAdd1(p.getIdProduits());
                    gr.flush();
                    int i = gr.getfind1(p.getIdProduits()).getId();
                    g.setId(i);

                    //On ajoute tout de suite les criteres
                    for(Caracteristiques c : g.getCriteres()) {
                        gr.getAdd3(g.getId(), c.getIdCaracteristiques());
                        gr.flush();

                    }
                } else {
                    gr.getAdd2(g.getId(), p.getIdProduits());
                    gr.flush();

                }
            }
        }catch(Exception e) {
            throw new DAOException("Un problème de requête est survenue : "+e.getMessage());
        }
    }



    // possible error, groupe may return a list? gcr.getByProduct returns array too?
    public Groupe getByProduct(Produits p) throws DAOException {
        PreparedStatement preparedStmt = null;
        ResultSet resultat = null;
        try {
            Groupe groupe = new Groupe();
            groupe = gr.getByProduct(p.getIdProduits());

            List<Produits> pList = new ArrayList<>();
            // gr.getByProduct returns list or 1 grp?

                Produits produit = new Produits();
                produit.setIdProduits(groupe.getIdProd());
                pList.add(produit);
                groupe.setId(resultat.getInt("id"));

            groupe.setProducts(pList);
            //Recuperation des criteres du groupe
            ArrayList<GroupeCritere> gc = gcr.getByProduct(groupe.getId());

            List<Caracteristiques> cList = new ArrayList<>();
            for(GroupeCritere g : gc){
                Caracteristiques c = new Caracteristiques();
                c.setIdCaracteristiques(resultat.getInt("idCritere"));
                cList.add(c);
            }
            groupe.setCriteres(cList);
            return groupe;
        }catch (SQLException e) {
            throw new DAOException("Impossible de recuperer le groupe : "+e.getMessage());
        }
    }







    public void update(Groupe g) throws DAOException {

    }








    public void removeGroup(Groupe g) throws DAOException {
        try {
            gr.delete(g);
            gr.flush();
        }catch (Exception e) {
            throw new DAOException("Impossible de supprimer le groupe : "+e.getMessage());
        }
    }







    public void removeProductFromGroup(Groupe g, Produits p) throws DAOException {
        try {
            gr.removeProductFromGroup(p.getIdProduits());
        }catch (Exception e) {
            throw new DAOException("Impossible de supprimer le produit du groupe : "+e.getMessage());
        }
    }




    public void removeCritereFromGroup(Groupe g, Caracteristiques c) throws DAOException {
        try {
            gr.removeCritereFromGroup(g.getId(), c.getIdCaracteristiques());
            gr.flush();
        }catch (Exception e) {
            throw new DAOException("Impossible de supprimer le critere du groupe : "+e.getMessage());
        }
    }
















}
