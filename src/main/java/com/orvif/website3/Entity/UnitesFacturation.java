package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "unites_facturation", schema = "t_orvif_web_dev")
public class UnitesFacturation {
    private int idUf;
    private String libelle;
    private Collection<Produits> produitsByIdUf;

    @Id
    @Column(name = "id_uf", nullable = false)
    public int getIdUf() {
        return idUf;
    }

    public void setIdUf(int idUf) {
        this.idUf = idUf;
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

        UnitesFacturation that = (UnitesFacturation) o;

        if (idUf != that.idUf) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUf;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "unitesFacturationByIdUf")
    public Collection<Produits> getProduitsByIdUf() {
        return produitsByIdUf;
    }

    public void setProduitsByIdUf(Collection<Produits> produitsByIdUf) {
        this.produitsByIdUf = produitsByIdUf;
    }
}
