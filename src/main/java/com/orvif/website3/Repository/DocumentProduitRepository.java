package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.DocumentProduitPK;
import com.orvif.website3.Entity.DocumentProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentProduitRepository extends JpaRepository<DocumentProduit, DocumentProduitPK> {
}
