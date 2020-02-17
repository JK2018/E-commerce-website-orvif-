package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.MotsClesProduitsPK;

import javax.persistence.*;

@Entity
@Table(name = "mots_cles_produits", schema = "t_orvif_web_dev")
@IdClass(MotsClesProduitsPK.class)
public class MotsClesProduits {
    private int idMotscles;
    private int idProduits;

    @Id
    @Column(name = "id_motscles", nullable = false)
    public int getIdMotscles() {
        return idMotscles;
    }

    public void setIdMotscles(int idMotscles) {
        this.idMotscles = idMotscles;
    }

    @Id
    @Column(name = "id_produits", nullable = false)
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

        MotsClesProduits that = (MotsClesProduits) o;

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
