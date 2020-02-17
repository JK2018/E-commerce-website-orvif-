package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "unites_vente", schema = "t_orvif_web_dev")
public class UnitesVente {
    private int idUv;
    private String libelle;
    private Collection<Produits> produitsByIdUv;

    @Id
    @Column(name = "id_uv", nullable = false)
    public int getIdUv() {
        return idUv;
    }

    public void setIdUv(int idUv) {
        this.idUv = idUv;
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

        UnitesVente that = (UnitesVente) o;

        if (idUv != that.idUv) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUv;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "unitesVenteByIdUv")
    public Collection<Produits> getProduitsByIdUv() {
        return produitsByIdUv;
    }

    public void setProduitsByIdUv(Collection<Produits> produitsByIdUv) {
        this.produitsByIdUv = produitsByIdUv;
    }
}
