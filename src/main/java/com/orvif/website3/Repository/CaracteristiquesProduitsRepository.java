package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiquesProduits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaracteristiquesProduitsRepository extends JpaRepository<CaracteristiquesProduits, Integer> {



    //for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "SELECT DISTINCT(valeur) FROM CARACTERISTIQUES_PRODUITS WHERE id_caracteristiques = :id_caracteristiques AND id_produits IN (SELECT id_produits FROM PRODUITS WHERE id_categories = :id_categories)", nativeQuery = true)
    List<CaracteristiquesProduits> buildCaracteristiqueArborescence3(@Param("id_caracteristiques") int id_caracteristiques, @Param("id_categories") int id_categories);
}
