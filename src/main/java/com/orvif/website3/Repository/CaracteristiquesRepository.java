package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;
import com.orvif.website3.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface CaracteristiquesRepository extends JpaRepository<Caracteristiques, CaracteristiquesProduitsPK> {



    // for method  getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM CARACTERISTIQUES C, CARACTERISTIQUES_PRODUITS CP WHERE C.id_caracteristiques = CP.id_caracteristiques AND CP.id_produits = :id_produits", nativeQuery = true)
    ArrayList<Caracteristiques> getByProduit(@Param("id_produits") int id_produits);


    // statement in method returns RESULTSET , errorprone!
    // for method  getBySousFamille in CaracteristiquesHelper
    @Query(value = ":stmtGetBySousFamille", nativeQuery = true)
    ResultSet getBySousFamille(@Param("stmtGetBySousFamille") String stmtGetBySousFamille);


    // statement in method returns RESULTSET , errorprone!
    // for method  getByCategorie in CaracteristiquesHelper
    @Query(value = ":stmtGetByCategorie", nativeQuery = true)
    ResultSet getByCategorie(@Param("stmtGetByCategorie") String stmtGetByCategorie);


    @Query(value = "SELECT * FROM caracteristiques WHERE libelle = :libelle AND recherche = :recherche", nativeQuery = true)
    Caracteristiques findWithoutId(@Param("libelle") String libelle, @Param("recherche") int recherche );


    @Query(value = "SELECT * FROM CARACTERISTIQUES WHERE id_caracteristiques = :id_caracteristiques", nativeQuery = true)
    Caracteristiques findByIdCustom(@Param("id_caracteristiques") int id_caracteristiques );







}
