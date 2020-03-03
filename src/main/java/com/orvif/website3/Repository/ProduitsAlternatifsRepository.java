package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.ProduitsAlternatifsPK;
import com.orvif.website3.Entity.ProduitsAlternatifs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitsAlternatifsRepository extends JpaRepository<ProduitsAlternatifs, ProduitsAlternatifsPK> {






    // for method removeProdAssocie in ProduitsHelper
    @Query(value = "DELETE FROM PRODUITS_ALTERNATIFS WHERE id_produits = :id_produits AND id_produits_alt = :id_produits_alt", nativeQuery = true)
    void removeProdAssocie(@Param("id_produits") int id_produits, @Param("id_produits_alt") int id_produits_alt);


    // for method addProdAssocie in ProduitsHelper
    @Query(value = "INSERT INTO PRODUITS_ALTERNATIFS(id_produits,id_produits_alt) VALUES(:id_produits,:id_produits_alt)", nativeQuery = true)
    void addProdAssocie(@Param("id_produits") int id_produits, @Param("id_produits_alt") int id_produits_alt);














}
