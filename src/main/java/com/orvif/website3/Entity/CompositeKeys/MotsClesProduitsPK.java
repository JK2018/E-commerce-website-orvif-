package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MotsClesProduitsPK implements Serializable {
    private int idMotscles;
    private int idProduits;

    @Column(name = "id_motscles", nullable = false)
    @Id
    public int getIdMotscles() {
        return idMotscles;
    }

    public void setIdMotscles(int idMotscles) {
        this.idMotscles = idMotscles;
    }

    @Column(name = "id_produits", nullable = false)
    @Id
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotsClesProduitsPK that = (MotsClesProduitsPK) o;

        if (idMotscles != that.idMotscles) return false;
        if (idProduits != that.idProduits) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMotscles;
        result = 31 * result + idProduits;
        return result;
    }
}
