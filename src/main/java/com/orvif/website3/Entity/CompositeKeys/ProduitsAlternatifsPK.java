package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ProduitsAlternatifsPK implements Serializable {
    private int idProduits;
    private int idProduitsAlt;

    @Column(name = "id_produits", nullable = false)
    @Id
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Column(name = "id_produits_alt", nullable = false)
    @Id
    public int getIdProduitsAlt() {
        return idProduitsAlt;
    }

    public void setIdProduitsAlt(int idProduitsAlt) {
        this.idProduitsAlt = idProduitsAlt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProduitsAlternatifsPK that = (ProduitsAlternatifsPK) o;

        if (idProduits != that.idProduits) return false;
        if (idProduitsAlt != that.idProduitsAlt) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduits;
        result = 31 * result + idProduitsAlt;
        return result;
    }
}
