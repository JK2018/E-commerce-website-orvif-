package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class LignePanierPK implements Serializable {
    private int idPanier;
    private int idProduit;

    @Column(name = "id_panier", nullable = false)
    @Id
    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    @Column(name = "id_produit", nullable = false)
    @Id
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LignePanierPK that = (LignePanierPK) o;

        if (idPanier != that.idPanier) return false;
        if (idProduit != that.idProduit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPanier;
        result = 31 * result + idProduit;
        return result;
    }
}
