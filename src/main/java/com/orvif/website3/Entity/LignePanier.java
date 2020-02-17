package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.LignePanierPK;

import javax.persistence.*;

@Entity
@Table(name = "ligne_panier", schema = "t_orvif_web_dev")
@IdClass(LignePanierPK.class)
public class LignePanier {
    private int idPanier;
    private int idProduit;
    private int quantite;
    private Panier panierByIdPanier;
    private Produits produitsByIdProduit;

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
}
