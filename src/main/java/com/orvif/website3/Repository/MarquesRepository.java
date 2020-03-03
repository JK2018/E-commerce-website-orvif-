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








}
