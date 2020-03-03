package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;
import com.orvif.website3.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CaracteristiquesRepository extends JpaRepository<Caracteristiques, CaracteristiquesProduitsPK> {
















    // for method  getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM CARACTERISTIQUES C, CARACTERISTIQUES_PRODUITS CP WHERE C.id_caracteristiques = CP.id_caracteristiques AND CP.id_produits = :id_produits", nativeQuery = true)
    ArrayList<Caracteristiques> getByProduit(@Param("id_produits") int id_produits);
















}
