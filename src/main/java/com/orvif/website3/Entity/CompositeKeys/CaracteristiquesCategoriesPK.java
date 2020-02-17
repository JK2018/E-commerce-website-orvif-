package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CaracteristiquesCategoriesPK implements Serializable {
    private int idCaracteristiques;
    private int idCategories;
    private String valeur;

    @Column(name = "id_caracteristiques", nullable = false)
    @Id
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
    }

    @Column(name = "id_categories", nullable = false)
    @Id
    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    @Column(name = "valeur", nullable = false, length = 100)
    @Id
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

        CaracteristiquesCategoriesPK that = (CaracteristiquesCategoriesPK) o;

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
