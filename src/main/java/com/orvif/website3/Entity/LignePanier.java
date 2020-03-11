package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.LignePanierPK;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "ligne_panier", schema = "t_orvif_web_dev")
@IdClass(LignePanierPK.class)
public class LignePanier implements Cloneable{
    private int idPanier;
    private int idProduit;
    private int quantite;
    private int quantiteMax;//
    private Panier panierByIdPanier;
    private Produits produitsByIdProduit;
    private float prix;//
    private float TVA;//
    private float prixTTC;//

    @Id
    @Column(name = "id_panier", nullable = false)
    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    @Id
    @Column(name = "id_produit", nullable = false)
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Basic
    @Column(name = "quantite", nullable = false)
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LignePanier that = (LignePanier) o;

        if (idPanier != that.idPanier) return false;
        if (idProduit != that.idProduit) return false;
        if (quantite != that.quantite) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPanier;
        result = 31 * result + idProduit;
        result = 31 * result + quantite;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_panier", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Panier getPanierByIdPanier() {
        return panierByIdPanier;
    }

    public void setPanierByIdPanier(Panier panierByIdPanier) {
        this.panierByIdPanier = panierByIdPanier;
    }

    @ManyToOne
    @JoinColumn(name = "id_produit", referencedColumnName = "id_produits", nullable = false, insertable = false, updatable = false)
    public Produits getProduitsByIdProduit() {
        return produitsByIdProduit;
    }

    public void setProduitsByIdProduit(Produits produitsByIdProduit) {
        this.produitsByIdProduit = produitsByIdProduit;
    }


    public int getQuantiteMax() {
        return quantiteMax;
    }

    public void setQuantiteMax(int quantiteMax) {
        this.quantiteMax = quantiteMax;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getTVA() {
        return TVA;
    }

    public void setTVA(float TVA) {
        this.TVA = TVA;
    }

    public float getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(float prixTTC) {
        this.prixTTC = prixTTC;
    }


    @Override
    public LignePanier clone() throws CloneNotSupportedException {
        return (LignePanier) super.clone();
    }




    private float round2decimal(float number) {
        return (float) ((Math.floor(number * 100)) / 100);
    }




/**
    public void setProduit(Produit p) {
        this.produit = p;
        //Get the max number of product with product's and comp products' stocks
        int productStock = 0;
        for (Map.Entry<String, Integer> stock : p.getStocks().entrySet()) {
            productStock += stock.getValue();
        }

        for (Map.Entry<Integer, Produit> set : p.getComplementairesObligatoire().entrySet()) {
            int stockProductComp = 0;
            for (Map.Entry<String, Integer> stock : set.getValue().getStocks().entrySet()) {
                stockProductComp += stock.getValue();
            }
            if (productStock * set.getKey() > stockProductComp) {
                //Pas assez de stock pour le produit complementaire. Calcul du nombre max que l'on peut vendre.
                productStock = stockProductComp / set.getKey();
            }
        }
        nbMax = productStock;
    }**/

}
