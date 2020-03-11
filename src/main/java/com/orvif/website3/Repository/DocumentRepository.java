package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Document;
import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {


    @Query(value = "SELECT * FROM DOCUMENT WHERE url = :url AND type = 'image'", nativeQuery = true)
    Document getPictureByLink( @Param("url") String url);


    @Query(value = "SELECT * FROM DOCUMENT WHERE type = 'catalogue'", nativeQuery = true)
    ArrayList<Document> getCatalogues();


    @Query(value = "SELECT * FROM DOCUMENT WHERE id = (SELECT id_document FROM DOCUMENT_PRODUIT WHERE id_produit = :theId LIMIT 1)", nativeQuery = true)
    Document getFirstImageByProduct( @Param("theId") int theId);

    // in method getDocumentListByProduit in DocumentHelper
    @Query(value = "SELECT * FROM DOCUMENT D, DOCUMENT_PRODUIT DP WHERE D.id = DP.id_document AND DP.id_produit = :id_produit", nativeQuery = true)
    List<Document> getDocumentListByProduit(@Param("id_produit") int id_produit);


    /**
     double table :S
    // for method  getDocumentListByProduit in Document
    @Query(value = "SELECT * FROM DOCUMENT D, DOCUMENT_PRODUIT DP WHERE D.id = DP.id_document AND DP.id_produit = :id_produit;", nativeQuery = true)
    Document getDocumentListByProduit1( @Param("id_produit") int id_produit);**/




    // in method getLogoMarques in DocumentHelper
    @Query(value = "SELECT * FROM MARQUES M, DOCUMENT D WHERE M.id_visuels = D.id AND M.id_marques = :id_marques", nativeQuery = true)
    Document getLogoMarques(@Param("id_marques") int id_marques);



}
