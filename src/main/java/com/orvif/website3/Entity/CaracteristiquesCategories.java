package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesCategoriesPK;

import javax.persistence.*;

@Entity
@Table(name = "caracteristiques_categories", schema = "t_orvif_web_dev")
@IdClass(CaracteristiquesCategoriesPK.class)
public class CaracteristiquesCategories {
    private int idCaracteristiques;
    private int idCategories;
    private String valeur;

    @Id
    @Column(name = "id_caracteristiques", nullable = false)
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
    }

    @Id
    @Column(name = "id_categories", nullable = false)
    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    @Id
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

        CaracteristiquesCategories that = (CaracteristiquesCategories) o;

        if (idCaracteristiques != that.idCaracteristiques) return false;
        if (idCategories != that.idCategories) return false;
        if (valeur != null ? !valeur.equals(that.valeur) : that.valeur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCaracteristiques;
        result = 31 * result + idCategories;
        result = 31 * result + (valeur != null ? valeur.hashCode() : 0);
        return result;
    }
}
