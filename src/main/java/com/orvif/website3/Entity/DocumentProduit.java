package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.DocumentProduitPK;

import javax.persistence.*;

@Entity
@Table(name = "document_produit", schema = "t_orvif_web_dev")
@IdClass(DocumentProduitPK.class)
public class DocumentProduit {
    private int idDocument;
    private int idProduit;

    public DocumentProduit() {
    }



    @Id
    @Column(name = "id_document", nullable = false)
    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    @Id
    @Column(name = "id_produit", nullable = false)
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

        DocumentProduit that = (DocumentProduit) o;

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
