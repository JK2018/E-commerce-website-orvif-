package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.DocumentProduitPK;
import com.orvif.website3.Entity.DocumentProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentProduitRepository extends JpaRepository<DocumentProduit, DocumentProduitPK> {




    // for method removeDocument in ProduitsHelper
    @Query(value = "DELETE FROM DOCUMENT_PRODUIT WHERE id_document = :id_document AND id_produit = :id_produit", nativeQuery = true)
    void removeDocument(@Param("id_document") int id_document, @Param("id_produit") int id_produit);


    // for method removeDocument in ProduitsHelper
    @Query(value = "SELECT * FROM DOCUMENT_PRODUIT WHERE id_document = :id_document AND id_produit = :id_produit", nativeQuery = true)
    DocumentProduit selectDocument(@Param("id_document") int id_document, @Param("id_produit") int id_produit);
}
