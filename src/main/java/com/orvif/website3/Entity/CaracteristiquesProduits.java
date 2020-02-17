package com.orvif.website3.Entity;

import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;

import javax.persistence.*;

@Entity
@Table(name = "caracteristiques_produits", schema = "t_orvif_web_dev")
@IdClass(CaracteristiquesProduitsPK.class)
public class CaracteristiquesProduits {
    private int idProduits;
    private int idCaracteristiques;
    private String valeur;

    @Id
    @Column(name = "id_produits", nullable = false)
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Id
    @Column(name = "id_caracteristiques", nullable = false)
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
    }

    @Basic
    @Column(name = "valeur", nullable = false, length = 100)
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaracteristiquesProduits that = (CaracteristiquesProduits) o;

        if (idProduits != that.idProduits) return false;
        if (idCaracteristiques != that.idCaracteristiques) return false;
        if (valeur != null ? !valeur.equals(that.valeur) : that.valeur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduits;
        result = 31 * result + idCaracteristiques;
        result = 31 * result + (valeur != null ? valeur.hashCode() : 0);
        return result;
    }
}
