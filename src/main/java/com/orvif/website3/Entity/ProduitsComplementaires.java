package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.ProduitsComplementairesPK;

import javax.persistence.*;

@Entity
@Table(name = "produits_complementaires", schema = "t_orvif_web_dev")
@IdClass(ProduitsComplementairesPK.class)
public class ProduitsComplementaires {
    private int idProduits;
    private int idProduitsComp;
    private byte obligatoire;

    @Id
    @Column(name = "id_produits", nullable = false)
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    @Id
    @Column(name = "id_produits_comp", nullable = false)
    public int getIdProduitsComp() {
        return idProduitsComp;
    }

    public void setIdProduitsComp(int idProduitsComp) {
        this.idProduitsComp = idProduitsComp;
    }

    @Basic
    @Column(name = "obligatoire", nullable = false)
    public byte getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(byte obligatoire) {
        this.obligatoire = obligatoire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProduitsComplementaires that = (ProduitsComplementaires) o;

        if (idProduits != that.idProduits) return false;
        if (idProduitsComp != that.idProduitsComp) return false;
        if (obligatoire != that.obligatoire) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduits;
        result = 31 * result + idProduitsComp;
        result = 31 * result + (int) obligatoire;
        return result;
    }
}
