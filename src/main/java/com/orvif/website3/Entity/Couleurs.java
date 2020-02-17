package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "couleurs", schema = "t_orvif_web_dev")
public class Couleurs {
    private int idCouleurs;
    private String libelle;

    @Id
    @Column(name = "id_couleurs", nullable = false)
    public int getIdCouleurs() {
        return idCouleurs;
    }

    public void setIdCouleurs(int idCouleurs) {
        this.idCouleurs = idCouleurs;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 50)
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Couleurs couleurs = (Couleurs) o;

        if (idCouleurs != couleurs.idCouleurs) return false;
        if (libelle != null ? !libelle.equals(couleurs.libelle) : couleurs.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCouleurs;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
