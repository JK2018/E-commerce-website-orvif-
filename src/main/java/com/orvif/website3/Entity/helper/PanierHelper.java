package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.LignePanier;
import com.orvif.website3.Entity.Panier;
import com.orvif.website3.Entity.Produits;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;
import com.orvif.website3.Repository.LignePanierRepository;
import com.orvif.website3.Repository.PanierRepository;
import com.orvif.website3.exceptions.OrvifDeliveryNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

@Component
public class PanierHelper {


    @Autowired
    private PanierRepository pr;

    @Autowired
    private LignePanierRepository lpr;

    @Autowired
    private DAOFactory dao;


    private ResultSet resultat = null;

    @Autowired
    private ProduitsHelper ph;



    public Map<Integer, String> getDatesRetrait(Panier panier) {
        Map<Integer, String> result = new HashMap<>();
        String webService = "WN_DATE_RETRAIT";
        StringBuilder webSeviceParameterBuilder = new StringBuilder();
        boolean firstloop = true;
        for (LignePanier lignePanier : panier.getLignePaniersById()) {
            if (firstloop) {
                firstloop = false;
            } else {
                webSeviceParameterBuilder.append("-");
            }
            webSeviceParameterBuilder.append(lignePanier.getProduitsByIdProduit().getCleSystem());
        }
        webSeviceParameterBuilder.append("_");
        firstloop = true;
        for (LignePanier lignePanier : panier.getLignePaniersById()) {
            if (firstloop) {
                firstloop = false;
            } else {
                webSeviceParameterBuilder.append("-");
            }
            webSeviceParameterBuilder.append(lignePanier.getQuantite());
        }
        try {
            Document root = dao.execWebService(webService + " " + webSeviceParameterBuilder.toString());
            if (!root.getDocumentElement().getNodeName().equals("RETOUR")) {
                throw new DAOException("Webservice result is invalid (root does not corresponds to \"RETOUR\")");
            }
            NodeList nodeList = root.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element availability = (Element) nodeList.item(i);
                // Get the two nodes (DATE + MAM)
                Node date = availability.getElementsByTagName("DATE").item(0);
                Node mam = availability.getElementsByTagName("MAM").item(0);
                String dateAvailable = date.getTextContent();
                if (mam.getTextContent().equals("M")) {
                    dateAvailable += " (matin)";
                } else {
                    dateAvailable += " (apr&egrave;s-midi)";
                }
                // add the date to the result map
                String agencyName = availability.getNodeName();
                Logger.getLogger(getClass().getName()).info("Agency name = " + agencyName);
                if (agencyName.equals("GENTILLY")) {
                    result.put(1, dateAvailable);
                } else if (agencyName.equals("SAINTDENIS")) {
                    result.put(2, dateAvailable);
                } else if (agencyName.equals("BASTILLE")) {
                    result.put(3, dateAvailable);
                } else if (agencyName.equals("MONTREUIL")) {
                    result.put(5, dateAvailable);
                } else if (agencyName.equals("MONTMARTRE")) {
                    result.put(6, dateAvailable);
                } else if (agencyName.equals("ORLY")) {
                    result.put(10, dateAvailable);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("An error occured when trying to get removal dates. (" + e.getClass().getName() + ")");
        }
    }










    public String getDateLivraison(Adresse adresse, Panier panier) throws DAOException, OrvifDeliveryNotSupportedException {
        String webService = "WN_DATE_LIVRAISON";
        StringBuilder webSeviceParameterBuilder = new StringBuilder();
        webSeviceParameterBuilder.append(adresse.getNomVoie()).append("@").append(adresse.getCodePostal()).append("@");
        boolean firstloop = true;
        for (LignePanier lignePanier : panier.getLignePaniersById()) {
            if (firstloop) {
                firstloop = false;
            } else {
                webSeviceParameterBuilder.append("-");
            }
            webSeviceParameterBuilder.append(lignePanier.getProduitsByIdProduit().getCleSystem());
        }
        webSeviceParameterBuilder.append("_");
        firstloop = true;
        for (LignePanier lignePanier : panier.getLignePaniersById()) {
            if (firstloop) {
                firstloop = false;
            } else {
                webSeviceParameterBuilder.append("-");
            }
            webSeviceParameterBuilder.append(lignePanier.getQuantite());
        }
        try {
            Document root = dao.execWebService(webService + " " + webSeviceParameterBuilder.toString(), true);
            if (!root.getDocumentElement().getNodeName().equals("RETOUR")) {
                throw new DAOException("Webservice result is invalid (root does not corresponds to \"RETOUR\")");
            }
            NodeList nodeList = root.getDocumentElement().getChildNodes();
            // Check if there is an delivery agency for the address
            if (root.getElementsByTagName("ERROR").getLength() > 0) {
                if (root.getElementsByTagName("ERROR").item(0).getTextContent().equals("NO_AGENCE")) {
                    throw new OrvifDeliveryNotSupportedException();
                }
            }
            return root.getElementsByTagName("DATE").item(0).getTextContent() + " (" + (root.getElementsByTagName("MAM").item(0).getTextContent().equals("M") ? "matin)" : "apr&egrave;s midi)");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("An error occured when trying to get delivery date. (" + e.getClass().getName() + ")");
        }
    }











    public Panier getPanierByClient(int id, String cleClient) {
        try {
            Panier p = pr.getPanierByClient(id);
            return convertResultatToPanier((ResultSet)p, cleClient);
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception occured when trying to fetch cart.");
        }
    }











    //possible error check native method
    public int add(Panier p) throws SQLException {
        try {
            p = pr.save(p);
            pr.flush();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Impossible de recuperer l'id du panier insere.");
        }
        return p.getId();
    }










    private Panier convertResultatToPanier(ResultSet resultat, String cleClient) {
        Panier ret = null;
        try {
            int i = 0;
            ArrayList<LignePanier> lpCollection = new ArrayList<>();
            while (resultat.next()) {
                if (i == 0) {
                    //Si c'est la premiere ligne, on ajoute les infos du panier en general (qui se retrouveront sur toutes les lignes)
                    ret = new Panier();
                    ret.setId(resultat.getInt("P.id"));
                    ret.setValide(resultat.getInt("P.valide"));
                    ret.setEtat(resultat.getInt("P.etat"));
                    ret.setNbProduit(0);
                }
                LignePanier lp = new LignePanier();
                lp.setIdPanier(ret.getId());
                lp.setQuantite(resultat.getInt("LP.quantite"));
                lp.setProduitsByIdProduit(ph.getById(resultat.getInt("LP.id_produit"), cleClient));
                lpCollection.add(lp);
                i++;
                ret.setNbProduit(ret.getNbProduit() + lp.getQuantite());
            }
            if (!lpCollection.isEmpty()) {
                ret.setLignePaniersById(lpCollection);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return ret;
    }













    public void addProduit(int idPanier, int idProduit, int nombre) throws DAOException {
        LignePanier lp = getLigne(idPanier, idProduit);
        if (lp == null) {
            /** Ajout d'une nouvelle ligne **/
            try {
                lpr.add2(idPanier, idProduit, nombre);
                lpr.flush();
            } catch (DAOException e) {
                throw e;
            } catch (Exception e) {
                throw new DAOException(e.getClass().getName() + " exception occured when trying to add product to cart in database.");
            }
        } else {
            /** Modification de la quantité **/
            updateQuantityLigne(idPanier, idProduit, lp.getQuantite() + nombre);
        }
    }










    public LignePanier getLigne(int id_panier, int id_produit) {
        LignePanier ret = null;
        try {
            ret = lpr.getLigne(id_panier,id_produit);
                Produits p = new Produits();
                p.setIdProduits(ret.getIdProduit());
                ret.setProduitsByIdProduit(p);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return ret;
    }







    public void updateQuantityLigne(int id_panier, int id_produit, int nombre) throws DAOException {
        try {
            LignePanier l = new LignePanier();
            l.setIdPanier(id_panier);
            l.setIdProduit(id_produit);
            l.setQuantite(nombre);
            lpr.save(l);
            lpr.flush();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }








    public boolean removePanier(int idPanier) {
        boolean ret = true;
        try {
            Panier p = pr.findById(idPanier).get();
            pr.delete(p);
            pr.flush();
        } catch (Exception e) {
            ret = false;
            throw new DAOException(e.getMessage());
        }
        return ret;
    }








    public boolean removeLignePanier(int idPanier, int idProduit) {
        try {
            lpr.removeLignePanier(idPanier, idProduit);
            lpr.flush();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return true;
    }

























}
