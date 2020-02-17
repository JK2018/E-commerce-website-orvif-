package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "caracteristiques", schema = "t_orvif_web_dev")
public class Caracteristiques {
    private int idCaracteristiques;
    private String libelle;
    private byte recherche;

    @Id
    @Column(name = "id_caracteristiques", nullable = false)
    public int getIdCaracteristiques() {
        return idCaracteristiques;
    }

    public void setIdCaracteristiques(int idCaracteristiques) {
        this.idCaracteristiques = idCaracteristiques;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 75)
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Basic
    @Column(name = "recherche", nullable = false)
    public byte getRecherche() {
        return recherche;
    }

    public void setRecherche(byte recherche) {
        this.recherche = recherche;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Caracteristiques that = (Caracteristiques) o;

        if (idCaracteristiques != that.idCaracteristiques) return false;
        if (recherche != that.recherche) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCaracteristiques;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (int) recherche;
        return result;
    }
}
