package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CaracteristiquesSsfamillesPK implements Serializable {
    private int idSsfamilles;
    private int idCaracteristiques;
    private String valeur;

    @Column(name = "id_ssfamilles", nullable = false)
    @Id
    public int getIdSsfamilles() {
        return idSsfamilles;
    }

    public void setIdSsfamilles(int idSsfamilles) {
        this.idSsfamilles = idSsfamilles;
    }

    @Column(name = "id_caracteristiques", nullable = false)
    @Id
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
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

        CaracteristiquesSsfamillesPK that = (CaracteristiquesSsfamillesPK) o;

        if (idSsfamilles != that.idSsfamilles) return false;
        if (idCaracteristiques != that.idCaracteristiques) return false;
        if (valeur != null ? !valeur.equals(that.valeur) : that.valeur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSsfamilles;
        result = 31 * result + idCaracteristiques;
        result = 31 * result + (valeur != null ? valeur.hashCode() : 0);
        return result;
    }
}
