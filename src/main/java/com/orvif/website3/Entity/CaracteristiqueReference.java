package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.CaracteristiqueReferencePK;

import javax.persistence.*;

@Entity
@Table(name = "caracteristique_reference", schema = "t_orvif_web_dev")
@IdClass(CaracteristiqueReferencePK.class)
public class CaracteristiqueReference {
    private int idCaract;
    private int idReference;
    private String valeur;

    @Id
    @Column(name = "id_caract", nullable = false)
    public int getIdCaract() {
        return idCaract;
    }

    public void setIdCaract(int idCaract) {
        this.idCaract = idCaract;
    }

    @Id
    @Column(name = "id_reference", nullable = false)
    public int getIdReference() {
        return idReference;
    }

    public void setIdReference(int idReference) {
        this.idReference = idReference;
    }

    @Basic
    @Column(name = "valeur", nullable = true, length = 45)
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

        CaracteristiqueReference that = (CaracteristiqueReference) o;

        if (idCaract != that.idCaract) return false;
        if (idReference != that.idReference) return false;
        if (valeur != null ? !valeur.equals(that.valeur) : that.valeur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCaract;
        result = 31 * result + idReference;
        result = 31 * result + (valeur != null ? valeur.hashCode() : 0);
        return result;
    }
}
