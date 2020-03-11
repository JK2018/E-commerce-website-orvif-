package com.orvif.website3.Entity;

import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DAOFactory;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "panier", schema = "t_orvif_web_dev")
public class Panier {
    private int id;
    private int nbProduit;//
    private String dateCreation;
    private Integer idClient;
    private int valide;//0->panier en attente de validation par un superieur,//0 sinon
    private int etat;//0 -> refuse, 1-> accepte, 2 -> En attente
    private Collection<LignePanier> lignePaniersById;
    private Utilisateurs client;//
    private float prixTotal;//
    private float TVA;//
    private float prixTotalTTC;//
    private Map<Integer, String> removalDates;//

    private LignePanier ligne;
    //private ArrayList<LignePanier> ligneCollection = new ArrayList<LignePanier>();

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_creation", nullable = false, length = 45)
    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Basic
    @Column(name = "id_client", nullable = true)
    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "valide", nullable = true)
    public int getValide() {
        return valide;
    }

    public void setValide(int valide) {
        this.valide = valide;
    }

    @Basic
    @Column(name = "etat", nullable = true)
    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id == panier.id &&
                valide == panier.valide &&
                etat == panier.etat &&
                Objects.equals(dateCreation, panier.dateCreation) &&
                Objects.equals(idClient, panier.idClient) &&
                Objects.equals(lignePaniersById, panier.lignePaniersById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreation, idClient, valide, etat, lignePaniersById);
    }

    @OneToMany(mappedBy = "panierByIdPanier")
    public Collection<LignePanier> getLignePaniersById() {
        return lignePaniersById;
    }

    public void setLignePaniersById(Collection<LignePanier> lignePaniersById) {
        this.lignePaniersById = lignePaniersById;
    }


    public int getNbProduit() {
        return nbProduit;
    }

    public void setNbProduit(int nbProduit) {
        this.nbProduit = nbProduit;
    }

    @Transient
    public Utilisateurs getClient() {
        return client;
    }


    public void setClient(Utilisateurs client) {
        this.client = client;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public float getTVA() {
        return TVA;
    }

    public void setTVA(float TVA) {
        this.TVA = TVA;
    }

    public float getPrixTotalTTC() {
        return prixTotalTTC;
    }

    public void setPrixTotalTTC(float prixTotalTTC) {
        this.prixTotalTTC = prixTotalTTC;
    }

    @Transient
    public Map<Integer, String> getRemovalDates() {
        return removalDates;
    }

    public void setRemovalDates(Map<Integer, String> removalDates) {
        this.removalDates = removalDates;
    }




    public void addProduit(Produits p, int nb) throws NotEnoughStockException {
        LignePanier ligne = ligneDuProduit(p.getIdProduits());
        if (ligne == null) {
            ligne = new LignePanier();
            ligne.setProduitsByIdProduit(p);
            if (ligne.getQuantiteMax() < nb) {
                throw new NotEnoughStockException();
            }
            ligne.setQuantite(nb);
            ligne.setPrix(p.getPpht() * nb);
            ligne.setTVA((float) (ligne.getPrix() * 0.2));
            ligne.setPrixTTC(ligne.getPrix() + ligne.getTVA());
            lignePaniersById.add(ligne);
        } else {
            if (ligne.getQuantiteMax() < ligne.getQuantite() + nb) {
                throw new NotEnoughStockException();
            }
            ligne.setQuantite(ligne.getQuantite() + nb);
            ligne.setPrix(p.getPpht() * ligne.getQuantite());
            ligne.setTVA((float) (ligne.getPrix() * 0.2));
            ligne.setPrixTTC(ligne.getPrix() + ligne.getTVA());
        }
        this.nbProduit = this.nbProduit + nb;
        this.majPrixPanier();
    }





    /**
     * Ajoute autant de produit possible pour correspondre à la quantité max
     *
     * @param p
     * @return Le nombre de produit ajoutés
     */
    public int addMaxQuantityProduct(Produits p) {
        LignePanier ligne = ligneDuProduit(p.getIdProduits());
        int nb = 0;
        if (ligne == null) {
            ligne = new LignePanier();
            ligne.setProduitsByIdProduit(p);
            ligne.setQuantite(ligne.getQuantiteMax());
            nb = ligne.getQuantiteMax();
            ligne.setPrix(p.getPpht() * ligne.getQuantite());
            ligne.setTVA((float) (ligne.getPrix() * 0.2));
            ligne.setPrixTTC(ligne.getPrix() + ligne.getTVA());
            lignePaniersById.add(ligne);
        } else {
            nb = ligne.getQuantiteMax() - ligne.getQuantite();
            ligne.setQuantite(ligne.getQuantiteMax());
            ligne.setPrix(p.getPpht() * ligne.getQuantite());
            ligne.setTVA((float) (ligne.getPrix() * 0.2));
            ligne.setPrixTTC(ligne.getPrix() + ligne.getTVA());
        }
        this.nbProduit = this.nbProduit + nb;
        this.majPrixPanier();
        return nb;
    }




    /**
     * Cherche si un produit est deja dans le panier et renvoie la ligne
     * correspondante si c'est le cas, nul sinon
     *
     * @param id
     * @return
     */
    private LignePanier ligneDuProduit(int id) {
        for(LignePanier l : lignePaniersById){
            if(l.getIdProduit()==id){
                return l;
            }
        }
        return null;
    }




    /**
     * Enleve un produit du panier
     *
     * @param produit
     */
    public void removeProduit(int produit) {
        LignePanier lp = ligneDuProduit(produit);
        if (lp != null) {
            this.nbProduit = this.nbProduit - lp.getQuantite();
            this.lignePaniersById.remove(lp);
            majPrixPanier();
        }
    }




    /**
     * Change la quantite d'un produit dans le panier
     *
     * @param produit
     * @param qte
     */
    public void changerQuantite(int produit, int qte) throws NotEnoughStockException {
        LignePanier lp = ligneDuProduit(produit);
        if (qte == 0) {
            this.removeProduit(produit);
        } else {
            if (lp.getQuantiteMax() < qte) {
                throw new NotEnoughStockException();
            }
            int difference = qte - lp.getQuantite();
            lp.setQuantite(qte);
            lp.setPrix(qte * lp.getProduitsByIdProduit().getPpht());
            lp.setTVA((float) (lp.getPrix() * 0.2));
            lp.setPrixTTC(lp.getPrix() + lp.getTVA());
            this.nbProduit = this.nbProduit + difference;
            this.majPrixPanier();
        }
    }




    /**
     * Met a jour le prix total du panier
     */
    private void majPrixPanier() {
        this.setPrixTotal(0);
        this.setPrixTotalTTC(0);
        this.setTVA(0);
        this.setPrixTotal(lignePaniersById.stream().reduce(0.0f, (sum, lignePanier) -> sum + lignePanier.getPrix(), Float::sum));
        this.setTVA(lignePaniersById.stream().reduce(0.0f, (sum, lignePanier) -> sum + lignePanier.getTVA(), Float::sum));
        this.setPrixTotalTTC(this.prixTotal + this.TVA);
    }




    private float round2decimal(float number) {
        return (float) ((Math.floor(number * 100)) / 100);
    }





    public class NotEnoughStockException extends Exception {
        public NotEnoughStockException() {
        }

        public NotEnoughStockException(String message) {
            super(message);
        }
    }





    /**
     * Method to reset data especially for when user log in / log out
     *
     * @param daoFactory
     * @param cleClient
     * @throws CloneNotSupportedException
     * @throws DAOException
     */
    public void resetData(ProduitsHelper daoFactory, int cleClient) throws CloneNotSupportedException, DAOException {
        List<LignePanier> copyListPanier = new ArrayList<>();
        for (LignePanier lignePanier : lignePaniersById) {
            copyListPanier.add(lignePanier.clone());
        }
        this.reset();
        for (LignePanier lignePanier : copyListPanier) {
            try {
                Produits p = daoFactory.getById(lignePanier.getProduitsByIdProduit().getIdProduits(), String.valueOf(cleClient));
                try {
                    this.addProduit(p, lignePanier.getQuantite());
                } catch (Panier.NotEnoughStockException e) {
                    //Do nothing
                }
            } catch (Exception e) {
                //Do nothing
            }
        }
    }

    private void reset() {
        this.nbProduit = 0;
        this.lignePaniersById = new ArrayList<>();
        this.prixTotal = 0;
        this.prixTotalTTC = 0;
        this.TVA = 0;
    }

    public void setNotFoundRemovalDateValues() {
        Map<Integer, String> newMap = new HashMap<>();
        for (int i = 0; i < 11; i++) {
            newMap.put(i, "[non disponible]");
        }
        this.setRemovalDates(newMap);
    }


}
