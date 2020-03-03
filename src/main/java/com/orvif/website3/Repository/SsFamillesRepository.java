package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.SsFamilles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SsFamillesRepository extends JpaRepository<SsFamilles, Integer> {

    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_ssfamilles FROM ss_familles WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);











    // for method getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM SS_FAMILLES SF, PRODUITS P WHERE SF.id_ssfamilles=P.id_ssfamilles AND P.id_produits = :id_produits" , nativeQuery = true)
    SsFamilles getByIdProduit(@Param("id_produits") int id_produits);






}
