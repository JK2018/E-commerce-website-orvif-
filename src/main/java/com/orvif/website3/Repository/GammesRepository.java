package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.Gammes;
import com.orvif.website3.Entity.Marques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface GammesRepository extends JpaRepository<Gammes, Integer> {



    // for method getByMarque in GammesHelper
    @Query(value = "SELECT * FROM GAMMES WHERE id_marques = :id_marques" , nativeQuery = true)
    ArrayList<Gammes> getByMarque(@Param("id_marques") int id_marques);
















}
