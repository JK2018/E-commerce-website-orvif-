package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CaracteristiquesProduitsPK implements Serializable {
    private int idProduits;
    private int idCaracteristiques;

    @Column(name = "id_produits", nullable = false)
    @Id
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Column(name = "id_caracteristiques", nullable = false)
    @Id
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaracteristiquesProduitsPK that = (CaracteristiquesProduitsPK) o;

        if (idProduits != that.idProduits) return false;
        if (idCaracteristiques != that.idCaracteristiques) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduits;
        result = 31 * result + idCaracteristiques;
        return result;
    }
}
