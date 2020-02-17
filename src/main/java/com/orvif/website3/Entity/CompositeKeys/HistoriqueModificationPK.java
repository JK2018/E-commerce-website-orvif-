package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class HistoriqueModificationPK implements Serializable {
    private int id;
    private int idProduit;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "id_produit", nullable = false)
    @Id
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoriqueModificationPK that = (HistoriqueModificationPK) o;

        if (id != that.id) return false;
        if (idProduit != that.idProduit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProduit;
        return result;
    }
}
