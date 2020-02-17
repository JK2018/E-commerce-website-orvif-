package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class DocumentProduitPK implements Serializable {
    private int idDocument;
    private int idProduit;

    @Column(name = "id_document", nullable = false)
    @Id
    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
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

        DocumentProduitPK that = (DocumentProduitPK) o;

        if (idDocument != that.idDocument) return false;
        if (idProduit != that.idProduit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDocument;
        result = 31 * result + idProduit;
        return result;
    }
}
