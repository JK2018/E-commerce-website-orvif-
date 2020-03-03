package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "familles", schema = "t_orvif_web_dev")
public class Familles {
    //private static final long serialVersionUID = 3645857340410959882L;
    private int idFamilles;
    private String libelle;
    private String libelleCapitalized;
    private String url;
    private Collection<Produits> produitsByIdFamilles;
    private Collection<SsFamilles> ssFamillesByIdFamilles;

    @Id
    @Column(name = "id_familles", nullable = false)
    public int getIdFamilles() {
        return idFamilles;
    }

    public void setIdFamilles(int idFamilles) {
        this.idFamilles = idFamilles;
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

        Familles familles = (Familles) o;

        if (idFamilles != familles.idFamilles) return false;
        if (libelle != null ? !libelle.equals(familles.libelle) : familles.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFamilles;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "famillesByIdFamilles")
    public Collection<Produits> getProduitsByIdFamilles() {
        return produitsByIdFamilles;
    }

    public void setProduitsByIdFamilles(Collection<Produits> produitsByIdFamilles) {
        this.produitsByIdFamilles = produitsByIdFamilles;
    }

    @OneToMany(mappedBy = "famillesByParentFamille")
    public Collection<SsFamilles> getSsFamillesByIdFamilles() {
        return ssFamillesByIdFamilles;
    }

    public void setSsFamillesByIdFamilles(Collection<SsFamilles> ssFamillesByIdFamilles) {
        this.ssFamillesByIdFamilles = ssFamillesByIdFamilles;
    }


    public String getLibelleCapitalized() {
        return libelleCapitalized;
    }

    public void setLibelleCapitalized(String libelleCapitalized) {
        this.libelleCapitalized = libelleCapitalized;
        this.libelleCapitalized = libelle.substring(0, 1).toUpperCase() + libelle.substring(1).toLowerCase();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
