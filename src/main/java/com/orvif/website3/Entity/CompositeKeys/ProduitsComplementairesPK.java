package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ProduitsComplementairesPK implements Serializable {
    private int idProduits;
    private int idProduitsComp;

    @Column(name = "id_produits", nullable = false)
    @Id
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Column(name = "id_produits_comp", nullable = false)
    @Id
    public int getIdProduitsComp() {
        return idProduitsComp;
    }

    public void setIdProduitsComp(int idProduitsComp) {
        this.idProduitsComp = idProduitsComp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProduitsComplementairesPK that = (ProduitsComplementairesPK) o;

        if (idProduits != that.idProduits) return false;
        if (idProduitsComp != that.idProduitsComp) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduits;
        result = 31 * result + idProduitsComp;
        return result;
    }
}
