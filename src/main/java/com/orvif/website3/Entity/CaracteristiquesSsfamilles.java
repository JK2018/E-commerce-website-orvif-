package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesSsfamillesPK;

import javax.persistence.*;

@Entity
@Table(name = "caracteristiques_ssfamilles", schema = "t_orvif_web_dev")
@IdClass(CaracteristiquesSsfamillesPK.class)
public class CaracteristiquesSsfamilles {
    private int idSsfamilles;
    private int idCaracteristiques;
    private String valeur;

    @Id
    @Column(name = "id_ssfamilles", nullable = false)
    public int getIdSsfamilles() {
        return idSsfamilles;
    }

    public void setIdSsfamilles(int idSsfamilles) {
        this.idSsfamilles = idSsfamilles;
    }

    @Id
    @Column(name = "id_caracteristiques", nullable = false)
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
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

        CaracteristiquesSsfamilles that = (CaracteristiquesSsfamilles) o;

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
