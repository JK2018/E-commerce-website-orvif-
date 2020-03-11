package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Marques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface MarquesRepository extends JpaRepository<Marques, Integer> {

    // used in method getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM MARQUES WHERE id_marques = :id_marques", nativeQuery = true)
    Marques getById(@Param("id_marques") int id_marques);


    // used in method getAll in MarquesHelper
    @Query(value = "SELECT * FROM MARQUES", nativeQuery = true)
    ArrayList<Marques> getAll();

    // used in method add in MarquesHelper
    @Query(value = "INSERT INTO MARQUES(id_marques,id_visuels,nom,display) VALUES(DEFAULT,:id_visuels,:nom,:display)", nativeQuery = true)
    void add(@Param("id_visuels") int id_visuels, @Param("nom") String nom, @Param("display") boolean display);


    // used in method add in MarquesHelper
    @Query(value = "INSERT INTO MARQUES(id_marques,nom,display) VALUES(DEFAULT,:nom,:display)", nativeQuery = true)
    void add2(@Param("nom") String nom, @Param("display") boolean display);




}
