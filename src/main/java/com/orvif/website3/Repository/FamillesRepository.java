package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface FamillesRepository extends JpaRepository<Familles, Integer> {


    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_familles FROM familles WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);


    // for method getByProduit in FamillesHelper
    @Query(value = "SELECT * FROM FAMILLES F, PRODUITS P WHERE F.id_familles = P.id_familles AND P.id_produits = :id_produits" , nativeQuery = true)
    Familles getByProduit(@Param("id_produits") int id_produits);



    @Query(value = "SELECT * FROM FAMILLES ORDER BY id_familles" , nativeQuery = true)
    ArrayList<Familles> findAllSorted();



    // for method getBySousFamille in FamillesHelper
    @Query(value = "SELECT * FROM FAMILLES WHERE id_familles = (SELECT parent_famille FROM SS_FAMILLES WHERE id_ssfamilles = :id_ssfamilles) ORDER BY id_familles" , nativeQuery = true)
    Familles getBySousFamille(@Param("id_ssfamilles") int id_ssfamilles);


    // for ProduitsController
    @Query(value = "SELECT * FROM FAMILLES WHERE libelle = :libelle" , nativeQuery = true)
    Familles getByLibelle(@Param("libelle") String libelle);









}
