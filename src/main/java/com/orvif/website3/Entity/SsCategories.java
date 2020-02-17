package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ss_categories", schema = "t_orvif_web_dev")
public class SsCategories {
    private int idSscategories;
    private String libelle;
    private Categories categoriesByParentCategorie;
    private int parentCategorie;
    private Collection<Produits> produitsByIdSscategories;

    @Id
    @Column(name = "id_sscategories", nullable = false)
    public int getIdSscategories() {
        return idSscategories;
    }

    public void setIdSscategories(int idSscategories) {
        this.idSscategories = idSscategories;
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

        SsCategories that = (SsCategories) o;

        if (idSscategories != that.idSscategories) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSscategories;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "parent_categorie", referencedColumnName = "id_categories", nullable = false)
    public Categories getCategoriesByParentCategorie() {
        return categoriesByParentCategorie;
    }

    public void setCategoriesByParentCategorie(Categories categoriesByParentCategorie) {
        this.categoriesByParentCategorie = categoriesByParentCategorie;
    }

    @Basic
    @Column(name = "parent_categorie", nullable = false, insertable = false, updatable = false)
    public int getParentCategorie() {
        return parentCategorie;
    }

    public void setParentCategorie(int parentCategorie) {
        this.parentCategorie = parentCategorie;
    }

    @OneToMany(mappedBy = "ssCategoriesByIdSscategories")
    public Collection<Produits> getProduitsByIdSscategories() {
        return produitsByIdSscategories;
    }

    public void setProduitsByIdSscategories(Collection<Produits> produitsByIdSscategories) {
        this.produitsByIdSscategories = produitsByIdSscategories;
    }
}
