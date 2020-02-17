package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ss_familles", schema = "t_orvif_web_dev")
public class SsFamilles {
    private int idSsfamilles;
    private String libelle;
    private String urlImg;
    private Familles famillesByParentFamille;//
    private int parentFamille;
    private Collection<Categories> categoriesByIdSsfamilles;//
    private Collection<Produits> produitsByIdSsfamilles;//

    @Id
    @Column(name = "id_ssfamilles", nullable = false)
    public int getIdSsfamilles() {
        return idSsfamilles;
    }

    public void setIdSsfamilles(int idSsfamilles) {
        this.idSsfamilles = idSsfamilles;
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
    @Column(name = "url_img", nullable = true, length = 45)
    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SsFamilles that = (SsFamilles) o;

        if (idSsfamilles != that.idSsfamilles) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;
        if (urlImg != null ? !urlImg.equals(that.urlImg) : that.urlImg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSsfamilles;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (urlImg != null ? urlImg.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "parent_famille", referencedColumnName = "id_familles", nullable = false)
    public Familles getFamillesByParentFamille() {
        return famillesByParentFamille;
    }

    public void setFamillesByParentFamille(Familles famillesByParentFamille) {
        this.famillesByParentFamille = famillesByParentFamille;
    }

    @Basic
    @Column(name = "parent_famille", nullable = false, insertable = false, updatable = false)
    public int getParentFamille() {
        return parentFamille;
    }

    public void setParentFamille(int parentFamille) {
        this.parentFamille = parentFamille;
    }

    @OneToMany(mappedBy = "ssFamillesByParentSsfamille")
    public Collection<Categories> getCategoriesByIdSsfamilles() {
        return categoriesByIdSsfamilles;
    }

    public void setCategoriesByIdSsfamilles(Collection<Categories> categoriesByIdSsfamilles) {
        this.categoriesByIdSsfamilles = categoriesByIdSsfamilles;
    }

    @OneToMany(mappedBy = "ssFamillesByIdSsfamilles")
    public Collection<Produits> getProduitsByIdSsfamilles() {
        return produitsByIdSsfamilles;
    }

    public void setProduitsByIdSsfamilles(Collection<Produits> produitsByIdSsfamilles) {
        this.produitsByIdSsfamilles = produitsByIdSsfamilles;
    }
}
