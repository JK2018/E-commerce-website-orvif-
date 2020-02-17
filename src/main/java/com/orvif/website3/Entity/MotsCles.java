package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "mots_cles", schema = "t_orvif_web_dev")
public class MotsCles {
    private int idMotscles;
    private String libelle;

    @Id
    @Column(name = "id_motscles", nullable = false)
    public int getIdMotscles() {
        return idMotscles;
    }

    public void setIdMotscles(int idMotscles) {
        this.idMotscles = idMotscles;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 75)
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

        MotsCles motsCles = (MotsCles) o;

        if (idMotscles != motsCles.idMotscles) return false;
        if (libelle != null ? !libelle.equals(motsCles.libelle) : motsCles.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMotscles;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
