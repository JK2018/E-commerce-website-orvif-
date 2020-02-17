package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "panier", schema = "t_orvif_web_dev")
public class Panier {
    private int id;
    private String dateCreation;
    private Integer idClient;
    private Byte valide;
    private Byte etat;
    private Collection<LignePanier> lignePaniersById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_creation", nullable = false, length = 45)
    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Basic
    @Column(name = "id_client", nullable = true)
    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "valide", nullable = true)
    public Byte getValide() {
        return valide;
    }

    public void setValide(Byte valide) {
        this.valide = valide;
    }

    @Basic
    @Column(name = "etat", nullable = true)
    public Byte getEtat() {
        return etat;
    }

    public void setEtat(Byte etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Panier panier = (Panier) o;

        if (id != panier.id) return false;
        if (dateCreation != null ? !dateCreation.equals(panier.dateCreation) : panier.dateCreation != null)
            return false;
        if (idClient != null ? !idClient.equals(panier.idClient) : panier.idClient != null) return false;
        if (valide != null ? !valide.equals(panier.valide) : panier.valide != null) return false;
        if (etat != null ? !etat.equals(panier.etat) : panier.etat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (idClient != null ? idClient.hashCode() : 0);
        result = 31 * result + (valide != null ? valide.hashCode() : 0);
        result = 31 * result + (etat != null ? etat.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "panierByIdPanier")
    public Collection<LignePanier> getLignePaniersById() {
        return lignePaniersById;
    }

    public void setLignePaniersById(Collection<LignePanier> lignePaniersById) {
        this.lignePaniersById = lignePaniersById;
    }
}
