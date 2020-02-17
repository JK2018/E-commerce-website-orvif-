package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "matieres", schema = "t_orvif_web_dev")
public class Matieres {
    private int idMatieres;
    private String libelle;

    @Id
    @Column(name = "id_matieres", nullable = false)
    public int getIdMatieres() {
        return idMatieres;
    }

    public void setIdMatieres(int idMatieres) {
        this.idMatieres = idMatieres;
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

        Matieres matieres = (Matieres) o;

        if (idMatieres != matieres.idMatieres) return false;
        if (libelle != null ? !libelle.equals(matieres.libelle) : matieres.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMatieres;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
