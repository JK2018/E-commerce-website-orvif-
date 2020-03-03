package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "categories", schema = "t_orvif_web_dev")
public class Categories {
    private int idCategories;
    private String libelle;
    private String libelleCapitalized;//
    private int parentSsfamille;
    private SsFamilles ssFamillesByParentSsfamille;//
    private Collection<Produits> produitsByIdCategories;//
    private Collection<SsCategories> ssCategoriesByIdCategories;//
    private int nbProducts;//

    @Id
    @Column(name = "id_categories", nullable = false)
    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
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

        Categories that = (Categories) o;

        if (idCategories != that.idCategories) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCategories;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "parent_ssfamille", nullable = false, insertable = false, updatable = false)
    public int getParentSsfamille() {
        return parentSsfamille;
    }

    public void setParentSsfamille(int parentSsfamille) {
        this.parentSsfamille = parentSsfamille;
    }

    @ManyToOne
    @JoinColumn(name = "parent_ssfamille", referencedColumnName = "id_ssfamilles", nullable = false)
    public SsFamilles getSsFamillesByParentSsfamille() {
        return ssFamillesByParentSsfamille;
    }

    public void setSsFamillesByParentSsfamille(SsFamilles ssFamillesByParentSsfamille) {
        this.ssFamillesByParentSsfamille = ssFamillesByParentSsfamille;
    }

    @OneToMany(mappedBy = "categoriesByIdCategories")
    public Collection<Produits> getProduitsByIdCategories() {
        return produitsByIdCategories;
    }

    public void setProduitsByIdCategories(Collection<Produits> produitsByIdCategories) {
        this.produitsByIdCategories = produitsByIdCategories;
    }

    @OneToMany(mappedBy = "categoriesByParentCategorie")
    public Collection<SsCategories> getSsCategoriesByIdCategories() {
        return ssCategoriesByIdCategories;
    }

    public void setSsCategoriesByIdCategories(Collection<SsCategories> ssCategoriesByIdCategories) {
        this.ssCategoriesByIdCategories = ssCategoriesByIdCategories;
    }


    public String getLibelleCapitalized() {
        return libelleCapitalized;
    }

    public void setLibelleCapitalized(String libelleCapitalized) {
        this.libelleCapitalized = libelleCapitalized;
        this.libelleCapitalized = libelle.substring(0, 1).toUpperCase() + libelle.substring(1).toLowerCase();
    }

    public int getNbProducts() {
        return nbProducts;
    }

    public void setNbProducts(int nbProducts) {
        this.nbProducts = nbProducts;
    }
}
