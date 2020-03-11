package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.SsFamilles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface SsFamillesRepository extends JpaRepository<SsFamilles, Integer> {

    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_ssfamilles FROM ss_familles WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);


    // for method getGroupeByProduct && getByIdProduit in ProduitsHelper
    @Query(value = "SELECT * FROM SS_FAMILLES SF, PRODUITS P WHERE SF.id_ssfamilles=P.id_ssfamilles AND P.id_produits = :id_produits" , nativeQuery = true)
    SsFamilles getByIdProduit(@Param("id_produits") int id_produits);



    // for method getByFamille in SsFamillesHelper
    @Query(value = "SELECT * FROM SS_FAMILLES WHERE parent_famille = :parent_famille ORDER BY id_ssfamilles" , nativeQuery = true)
    ArrayList<SsFamilles> getByFamille(@Param("parent_famille") int parent_famille);



    // for method getByCategorie in SsFamillesHelper
    @Query(value = "SELECT * FROM SS_FAMILLES WHERE id_ssfamilles = (SELECT parent_ssfamille FROM CATEGORIES WHERE id_categories = :id_categories) ORDER BY id_ssfamilles" , nativeQuery = true)
    SsFamilles getByCategorie(@Param("id_categories") int id_categories);










}
