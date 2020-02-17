package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "marques", schema = "t_orvif_web_dev")
public class Marques {
    private int idMarques;
    private String nom;
    private String descriptif;
    private Integer idVisuels;
    private byte display;
    private Collection<Gammes> gammesByIdMarques;
    private Collection<Produits> produitsByIdMarques;

    @Id
    @Column(name = "id_marques", nullable = false)
    public int getIdMarques() {
        return idMarques;
    }

    public void setIdMarques(int idMarques) {
        this.idMarques = idMarques;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 50)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "descriptif", nullable = true, length = -1)
    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    @Basic
    @Column(name = "id_visuels", nullable = true)
    public Integer getIdVisuels() {
        return idVisuels;
    }

    public void setIdVisuels(Integer idVisuels) {
        this.idVisuels = idVisuels;
    }

    @Basic
    @Column(name = "display", nullable = false)
    public byte getDisplay() {
        return display;
    }

    public void setDisplay(byte display) {
        this.display = display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Marques marques = (Marques) o;

        if (idMarques != marques.idMarques) return false;
        if (display != marques.display) return false;
        if (nom != null ? !nom.equals(marques.nom) : marques.nom != null) return false;
        if (descriptif != null ? !descriptif.equals(marques.descriptif) : marques.descriptif != null) return false;
        if (idVisuels != null ? !idVisuels.equals(marques.idVisuels) : marques.idVisuels != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMarques;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (descriptif != null ? descriptif.hashCode() : 0);
        result = 31 * result + (idVisuels != null ? idVisuels.hashCode() : 0);
        result = 31 * result + (int) display;
        return result;
    }

    @OneToMany(mappedBy = "marquesByIdMarques")
    public Collection<Gammes> getGammesByIdMarques() {
        return gammesByIdMarques;
    }

    public void setGammesByIdMarques(Collection<Gammes> gammesByIdMarques) {
        this.gammesByIdMarques = gammesByIdMarques;
    }

    @OneToMany(mappedBy = "marquesByIdMarques")
    public Collection<Produits> getProduitsByIdMarques() {
        return produitsByIdMarques;
    }

    public void setProduitsByIdMarques(Collection<Produits> produitsByIdMarques) {
        this.produitsByIdMarques = produitsByIdMarques;
    }
}
