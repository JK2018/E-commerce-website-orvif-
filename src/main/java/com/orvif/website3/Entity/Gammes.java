package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "gammes", schema = "t_orvif_web_dev")
public class Gammes {
    private int idGammes;
    private String libelle;
    private int idMarques;
    private Marques marquesByIdMarques;//
    private Collection<Produits> produitsByIdGammes;//

    @Id
    @Column(name = "id_gammes", nullable = false)
    public int getIdGammes() {
        return idGammes;
    }

    public void setIdGammes(int idGammes) {
        this.idGammes = idGammes;
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

        Gammes gammes = (Gammes) o;

        if (idGammes != gammes.idGammes) return false;
        if (libelle != null ? !libelle.equals(gammes.libelle) : gammes.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idGammes;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "id_marques", nullable = false, insertable = false, updatable = false)
    public int getIdMarques() {
        return idMarques;
    }

    public void setIdMarques(int idMarques) {
        this.idMarques = idMarques;
    }

    @ManyToOne
    @JoinColumn(name = "id_marques", referencedColumnName = "id_marques", nullable = false)
    public Marques getMarquesByIdMarques() {
        return marquesByIdMarques;
    }

    public void setMarquesByIdMarques(Marques marquesByIdMarques) {
        this.marquesByIdMarques = marquesByIdMarques;
    }

    @OneToMany(mappedBy = "gammesByIdGammes")
    public Collection<Produits> getProduitsByIdGammes() {
        return produitsByIdGammes;
    }

    public void setProduitsByIdGammes(Collection<Produits> produitsByIdGammes) {
        this.produitsByIdGammes = produitsByIdGammes;
    }
}
