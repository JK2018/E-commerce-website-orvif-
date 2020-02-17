package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profils", schema = "t_orvif_web_dev")
public class Profils {
    private int idProfils;
    private String libelle;
    private Collection<Utilisateurs> utilisateursByIdProfils;

    @Id
    @Column(name = "id_profils", nullable = false)
    public int getIdProfils() {
        return idProfils;
    }

    public void setIdProfils(int idProfils) {
        this.idProfils = idProfils;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 45)
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

        Profils profils = (Profils) o;

        if (idProfils != profils.idProfils) return false;
        if (libelle != null ? !libelle.equals(profils.libelle) : profils.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProfils;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "profilsByProfil")
    public Collection<Utilisateurs> getUtilisateursByIdProfils() {
        return utilisateursByIdProfils;
    }

    public void setUtilisateursByIdProfils(Collection<Utilisateurs> utilisateursByIdProfils) {
        this.utilisateursByIdProfils = utilisateursByIdProfils;
    }
}
