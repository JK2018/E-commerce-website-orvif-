package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Entity.CaracteristiquesProduits;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;
import com.orvif.website3.Entity.HistoriqueModification;
import com.orvif.website3.Repository.CaracteristiquesProduitsRepository;
import com.orvif.website3.Repository.CaracteristiquesRepository;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.HistoriqueModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Component
public class CaracteristiquesHelper {


    private final String[] caracteristiqueNoFilter = {"poids", "descriptif"};

    @Autowired
    HistoriqueModificationRepository histoDao;

    @Autowired
    CaracteristiquesRepository cr;

    @Autowired
    CaracteristiquesProduitsRepository cpr;





    public ArrayList<Caracteristiques> getByProduit(int p) {
        ArrayList<Caracteristiques> ret = new ArrayList<>();
        try {
            ret = cr.getByProduit(p);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }








    public ArrayList<Caracteristiques> getBySousFamille(int id) {

        ArrayList<Caracteristiques> ret = new ArrayList<>();
        try {
            String stmtGetBySousFamille = "SELECT * FROM CARACTERISTIQUES C, CARACTERISTIQUES_SSFAMILLES CS WHERE C.id_caracteristiques = CS.id_caracteristiques AND CS.id_ssfamilles = \"" + id + "\"";
            for (String name : caracteristiqueNoFilter) {
                stmtGetBySousFamille += " AND C.libelle NOT LIKE \"" + name + "\"";
            }
            stmtGetBySousFamille += " ORDER BY C.id_caracteristiques";

            ResultSet ret2 = cr.getBySousFamille(stmtGetBySousFamille);
            Caracteristiques current = null;

            while (ret2.next()){
                int idLine = ret2.getInt("id_caracteristiques");
                if (current == null || current.getIdCaracteristiques() != idLine) {
                    if (current != null) {
                        ret.add(current);
                    }
                    current = new Caracteristiques();
                    current.setIdCaracteristiques(ret2.getInt("id_caracteristiques"));
                    current.setLibelle(ret2.getString("libelle"));
                    current.setRecherche(ret2.getInt("recherche"));
                }
                current.getValeurCollection().add(ret2.getString("valeur"));
            }

            if (current != null) {
                ret.add(current);
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public ArrayList<Caracteristiques> getByCategorie(int id) {
        ArrayList<Caracteristiques> ret = new ArrayList<>();
        try {
            Logger.getLogger(this.getClass().getName()).info("JUSTE AVANT");

            String stmtGetByCategorie = "SELECT * FROM CARACTERISTIQUES C, CARACTERISTIQUES_CATEGORIES CC WHERE C.id_caracteristiques = CC.id_caracteristiques AND id_categories = \"" + id + "\"";
            for (String name : caracteristiqueNoFilter) {
                stmtGetByCategorie += " AND C.libelle NOT LIKE \"" + name + "\"";
            }
            stmtGetByCategorie += " ORDER BY C.id_caracteristiques";

            ResultSet rSet = cr.getByCategorie(stmtGetByCategorie);

            Logger.getLogger(this.getClass().getName()).info("requete : " + stmtGetByCategorie);

            Caracteristiques current = null;
            Logger.getLogger(this.getClass().getName()).info("ID DE CATEGORIE : " + id);
            while (rSet.next()) {
                int idLine = rSet.getInt("id_caracteristiques");
                if (current == null || current.getIdCaracteristiques() != idLine) {
                    if (current != null) {
                        ret.add(current);
                    }
                    current = new Caracteristiques();
                    current.setIdCaracteristiques(rSet.getInt("id_caracteristiques"));
                    current.setLibelle(rSet.getString("libelle"));
                    current.setRecherche(rSet.getInt("recherche"));
                }
                current.getValeurCollection().add(rSet.getString("valeur"));
            }

            if (current != null) {
                ret.add(current);
            }

            Logger.getLogger(this.getClass().getName()).info("JUSTE APRES");
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public void add(Caracteristiques caracteristique) throws DAOException {
        try{
        cr.save(caracteristique);
        cr.flush();
        } catch (DAOException e) {
            throw e;
        }  catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " Exception occured when trying to add caracteristique.");
        }
    }






    public void fillValuesWithProductList(ArrayList<Caracteristiques> caracteristiqueCollection,
                                          ArrayList<Integer> produitCollection) {
        Iterator<Caracteristiques> caracteristiqueIterate = caracteristiqueCollection.iterator();
        int index = 0;
        while (caracteristiqueIterate.hasNext()) {
            Caracteristiques c = caracteristiqueIterate.next();
            try {
                ArrayList<String> array = cpr.fillValuesWithProductList(c.getIdCaracteristiques());
                caracteristiqueCollection.get(index).setValeurCollection(array);
            } catch (Exception e) {
                throw new DAOException("Une erreur est survenue : " + e.getMessage());
            }
            index++;
        }
    }



    public Caracteristiques getById(int id) {
        Caracteristiques ret = null;
        try {
            Caracteristiques c = cr.findByIdCustom(id);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public Caracteristiques findByIdCustom(int idFeature){
        Caracteristiques c = cr.findByIdCustom(idFeature);
        return c;
    }




    public ArrayList<Caracteristiques> getAll() {
        ArrayList<Caracteristiques> ret = new ArrayList<>();
        try {
            ret = (ArrayList)cr.findAll();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public void fillWithExistingValues(ArrayList<Caracteristiques> caracteristiqueCollection) {

        for (Caracteristiques c : caracteristiqueCollection) {
            try {
                ArrayList<String> values = cpr.fillWithExistingValues(c.getIdCaracteristiques());
                c.setValeurCollection(values);
            } catch (Exception e) {
                throw new DAOException("Une erreur est survenue : " + e.getMessage());
            }
        }
    }



    //ALWAYS TRUE ! secured by @Transactional?
    @Transactional
    public boolean updateCaracteristiqueProduit(int produit, List<Caracteristiques> newValeurs, String modificateur) {

        boolean ret = true;
        try {
            List<Caracteristiques> lastListC = this.getByProduit(produit);
            int nbModif = 0;

            HistoriqueModification h = new HistoriqueModification();
            for (Caracteristiques newValeur : newValeurs) {
                boolean found = false;
                for (Caracteristiques aLastListC : lastListC) {
                    if (newValeur.getIdCaracteristiques() == aLastListC.getIdCaracteristiques()) {
                        found = true;
                        if (!newValeur.getValeurProduit().equals(aLastListC.getValeurProduit())) {
                            // Modification d'une caracteristique
                            if (nbModif == 0) {
                                h.setChampModif("Caracteristiques");
                                h.setValInitial(
                                        aLastListC.getLibelle() + " : " + aLastListC.getValeurProduit());
                                h.setValModif(
                                        newValeur.getLibelle() + " : " + newValeur.getValeurProduit());
                                nbModif++;
                            } else {
                                h.setValInitial(h.getValInitial() + ", " + aLastListC.getLibelle() + " : "
                                        + aLastListC.getValeurProduit());
                                h.setValModif(h.getValModif() + ", " + newValeur.getLibelle()
                                        + " : " + newValeur.getValeurProduit());
                            }
                            cpr.updateCaracteristiqueProduitUPDATE(newValeur.getValeurProduit(), produit, newValeur.getIdCaracteristiques());
                            cpr.flush();
                           /** if (stmtMaj.executeUpdate() <= 0) {
                                ret = false;
                            }**/
                        }
                        break;
                    }
                }
                if (!found) {
                    // Ajout d'une nouvelle caracteristique
                    if (nbModif == 0) {
                        h.setChampModif("Caracteristiques");
                        h.setValInitial(newValeur.getLibelle() + " : aucune");
                        h.setValModif(
                                newValeur.getLibelle() + " : " + newValeur.getValeurProduit());
                        nbModif++;
                    } else {
                        h.setValInitial(h.getValInitial() + ", " + newValeur.getLibelle() + " : aucune");
                        h.setValModif(h.getValModif() + ", " + newValeur.getLibelle() + " : "
                                + newValeur.getValeurProduit());
                    }
                    cpr.updateCaracteristiqueProduitADD(produit, newValeur.getIdCaracteristiques(), newValeur.getValeurProduit());
                    cpr.flush();
                    /**if (stmtAjout.executeUpdate() <= 0) {
                        ret = false;
                    }**/
                }
            }
            // Execution d'un for dans l'autre sens pour verifier si une caracteristique a
            // ete supprimee
            for (Caracteristiques aLastListC : lastListC) {
                boolean found = false;
                for (Caracteristiques newValeur : newValeurs) {
                    if (newValeur.getIdCaracteristiques() == aLastListC.getIdCaracteristiques()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // Suppression d'une carcateristique
                    if (nbModif == 0) {
                        h.setChampModif("Caracteristiques");
                        h.setValInitial(aLastListC.getLibelle() + " : " + aLastListC.getValeurProduit());
                        h.setValModif(aLastListC.getLibelle() + " : aucune");
                        nbModif++;
                    } else {
                        h.setValInitial(h.getValInitial() + ", " + aLastListC.getLibelle() + " : "
                                + aLastListC.getValeurProduit());
                        h.setValModif(h.getValModif() + ", " + aLastListC.getLibelle() + " : aucune");
                    }

                    cpr.updateCaracteristiqueProduitDEL(produit, aLastListC.getIdCaracteristiques());
                    cpr.flush();
                    /**if (stmtDelete.executeUpdate() <= 0) {
                        ret = false;
                    }**/
                }
            }
            if (nbModif > 0) {
                if (ret) {
                    h.setModificateur(modificateur);
                    h.setIdProduit(produit);
                   /** if (histoDao.add(h, connexion1) <= 0) {
                        connexion1.rollback();
                        ret = false;
                    } else {
                        connexion1.commit();
                    }**/
                } /**else {
                    connexion1.rollback();
                }**/
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




















}
