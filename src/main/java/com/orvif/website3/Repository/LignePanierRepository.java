package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.LignePanierPK;
import com.orvif.website3.Entity.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.DELETE;

public interface LignePanierRepository extends JpaRepository<LignePanier, LignePanierPK> {


    // for method add in PanierHelper
    @Query(value = "INSERT INTO LIGNE_PANIER(id_panier,id_produit,quantite) VALUES(:id_panier,:id_produit,:quantite)" , nativeQuery = true)
    void add2(@Param("id_panier") int id_panier, @Param("id_produit") int id_produit, @Param("quantite") int quantite);


    // for method getLigne in PanierHelper
    @Query(value = " SELECT * FROM LIGNE_PANIER WHERE id_panier = :id_panier AND id_produit = :id_produit" , nativeQuery = true)
    LignePanier getLigne(@Param("id_panier") int id_panier, @Param("id_produit") int id_produit);



    // for method removeLignePanier in PanierHelper
    @Query(value = "DELETE FROM LIGNE_PANIER WHERE id_panier = :id_panier AND id_produit = :id_produit" , nativeQuery = true)
    LignePanier removeLignePanier(@Param("id_panier") int id_panier, @Param("id_produit") int id_produit);












}
