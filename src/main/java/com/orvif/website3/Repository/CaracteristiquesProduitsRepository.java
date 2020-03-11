package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiquesProduits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.DELETE;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface CaracteristiquesProduitsRepository extends JpaRepository<CaracteristiquesProduits, Integer> {



    //for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "SELECT DISTINCT(valeur) FROM CARACTERISTIQUES_PRODUITS WHERE id_caracteristiques = :id_caracteristiques AND id_produits IN (SELECT id_produits FROM PRODUITS WHERE id_categories = :id_categories)", nativeQuery = true)
    List<CaracteristiquesProduits> buildCaracteristiqueArborescence3(@Param("id_caracteristiques") int id_caracteristiques, @Param("id_categories") int id_categories);



    //for method fillValuesWithProductList in CaracteristiquesHelper
    @Query(value = "SELECT DISTINCT valeur FROM CARACTERISTIQUES_PRODUITS WHERE id_caracteristiques = :id_caracteristiques", nativeQuery = true)
    ArrayList<String> fillValuesWithProductList(@Param("id_caracteristiques") int id_caracteristiques);


    // for method  fillWithExistingValues in CaracteristiquesHelper
    @Query(value = "SELECT DISTINCT valeur FROM CARACTERISTIQUES_PRODUITS WHERE id_caracteristiques = :id_caracteristiques", nativeQuery = true)
    ArrayList<String> fillWithExistingValues(@Param("id_caracteristiques") int id_caracteristiques);


    // for method  updateCaracteristiqueProduit && add in CaracteristiquesHelper
    @Query(value = "INSERT INTO CARACTERISTIQUES_PRODUITS(id_produits,id_caracteristiques,valeur) VALUES(:id_produits,:id_caracteristiques,:valeur)", nativeQuery = true)
    void updateCaracteristiqueProduitADD(@Param("id_produits") int id_produits, @Param("id_caracteristiques") int id_caracteristiques, @Param("valeur") String valeur);


    // for method  updateCaracteristiqueProduit in CaracteristiquesHelper
    @Query(value = "UPDATE CARACTERISTIQUES_PRODUITS SET valeur = :valeur WHERE id_produits = :id_produits AND id_caracteristiques = :id_caracteristiques", nativeQuery = true)
    void updateCaracteristiqueProduitUPDATE( @Param("valeur") String valeur, @Param("id_produits") int id_produits, @Param("id_caracteristiques") int id_caracteristiques);


    // for method  updateCaracteristiqueProduit in CaracteristiquesHelper
    @Query(value = "DELETE FROM CARACTERISTIQUES_PRODUITS WHERE id_produits = :id_produits AND id_caracteristiques = :id_caracteristiques", nativeQuery = true)
    void updateCaracteristiqueProduitDEL( @Param("id_produits") int id_produits, @Param("id_caracteristiques") int id_caracteristiques);
















}
