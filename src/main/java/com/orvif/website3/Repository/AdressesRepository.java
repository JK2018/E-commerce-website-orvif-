package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Adresse;
import com.orvif.website3.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AdressesRepository extends JpaRepository<Adresse, Integer> {


    // SELECT FROM 2 TABLE, MAYBE ERROR
    // for method getByMail in UtilisateursHelper
    @Query( value = "SELECT * FROM ADRESSE A,ADRESSES_CLIENTS AC WHERE A.id_adresse = AC.id_adresse AND AC.id_client = :id_client", nativeQuery = true)
    ArrayList<Adresse> getByClient(@Param("id_client") int id_client);



    @Query( value = "SELECT * FROM ADRESSE WHERE nom_voie = :nom_voie AND complement_voie = :complement_voie AND code_postal = :code_postal AND ville = :ville AND pays = :pays AND libelle = :libelle", nativeQuery = true)
    Adresse findWithoutId(@Param("nom_voie") String nom_voie, @Param("complement_voie") String complement_voie, @Param("code_postal") int code_postal, @Param("ville") String ville, @Param("pays") String pays, @Param("libelle") String libelle);



























}
