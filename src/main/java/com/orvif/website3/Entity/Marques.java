package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "marques", schema = "t_orvif_web_dev")
public class Marques {
    private int idMarques;
    private String nom;
    private String descriptif;
    private Integer idVisuels;
    private boolean display;
    private Document logo; //
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


    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
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


    @Transient
    public Document getLogo() {
        return logo;
    }

    public void setLogo(Document logo) {
        this.logo = logo;
    }



    @Transient
    public ArrayList<Marques> getAll(){
        ArrayList<Marques> ret = new ArrayList<>();
        return ret;
    }


    @Override
    public String toString() {
        return "Marques{" +
                "idMarques=" + idMarques +
                ", nom='" + nom + '\'' +
                ", descriptif='" + descriptif + '\'' +
                ", idVisuels=" + idVisuels +
                ", display=" + display +
                ", logo=" + logo +
                ", gammesByIdMarques=" + gammesByIdMarques +
                ", produitsByIdMarques=" + produitsByIdMarques +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marques marques = (Marques) o;
        return idMarques == marques.idMarques &&
                display == marques.display &&
                Objects.equals(nom, marques.nom) &&
                Objects.equals(descriptif, marques.descriptif) &&
                Objects.equals(idVisuels, marques.idVisuels) &&
                Objects.equals(logo, marques.logo) &&
                Objects.equals(gammesByIdMarques, marques.gammesByIdMarques) &&
                Objects.equals(produitsByIdMarques, marques.produitsByIdMarques);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMarques, nom, descriptif, idVisuels, display, logo, gammesByIdMarques, produitsByIdMarques);
    }
}
