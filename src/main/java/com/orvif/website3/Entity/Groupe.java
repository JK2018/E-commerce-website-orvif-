package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.GroupePK;

import javax.persistence.*;

@Entity
@Table(name = "groupe", schema = "t_orvif_web_dev")
@IdClass(GroupePK.class)
public class Groupe {
    private int id;
    private int idProd;
    private Produits produitsByIdProd;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "idProd", nullable = false)
    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Groupe groupe = (Groupe) o;

        if (id != groupe.id) return false;
        if (idProd != groupe.idProd) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProd;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idProd", referencedColumnName = "id_produits", nullable = false, insertable = false, updatable = false)
    public Produits getProduitsByIdProd() {
        return produitsByIdProd;
    }

    public void setProduitsByIdProd(Produits produitsByIdProd) {
        this.produitsByIdProd = produitsByIdProd;
    }
}
