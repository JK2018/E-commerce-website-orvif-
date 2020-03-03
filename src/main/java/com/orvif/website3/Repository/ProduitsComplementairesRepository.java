package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.ProduitsComplementairesPK;
import com.orvif.website3.Entity.ProduitsComplementaires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitsComplementairesRepository extends JpaRepository<ProduitsComplementaires, ProduitsComplementairesPK> {



    // for method removeProdComplementaire in ProduitsHelper
    @Query(value = "DELETE FROM PRODUITS_COMPLEMENTAIRES WHERE id_produits = :id_produits AND id_produits_comp = :id_produits_comp", nativeQuery = true)
    void removeProdComplementaire(@Param("id_produits") int id_produits, @Param("id_produits_comp") int id_produits_comp);




    // for method addProdComplementaire in ProduitsHelper
    @Query(value = "INSERT INTO PRODUITS_COMPLEMENTAIRES(id_produits,id_produits_comp,obligatoire) VALUES(:id_produits,:id_produits_comp,0)", nativeQuery = true)
    void addProdComplementaire(@Param("id_produits") int id_produits, @Param("id_produits_comp") int id_produits_comp);


}



