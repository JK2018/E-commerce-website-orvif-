package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.ProduitsAlternatifsPK;

import javax.persistence.*;

@Entity
@Table(name = "produits_alternatifs", schema = "t_orvif_web_dev")
@IdClass(ProduitsAlternatifsPK.class)
public class ProduitsAlternatifs {
    private int idProduits;
    private int idProduitsAlt;

    @Id
    @Column(name = "id_produits", nullable = false)
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Id
    @Column(name = "id_produits_alt", nullable = false)
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

        ProduitsAlternatifs that = (ProduitsAlternatifs) o;

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
