package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamillesRepository extends JpaRepository<Familles, Integer> {


    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_familles FROM familles WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);


    // for method getByProduit in FamillesHelper
    @Query(value = "SELECT * FROM FAMILLES F, PRODUITS P WHERE F.id_familles = P.id_familles AND P.id_produits = :id_produits" , nativeQuery = true)
    Familles getByProduit(@Param("id_produits") int id_produits);


}
