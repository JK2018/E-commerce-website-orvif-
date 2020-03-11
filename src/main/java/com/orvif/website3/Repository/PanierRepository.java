package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.LignePanier;
import com.orvif.website3.Entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PanierRepository extends JpaRepository<Panier, Integer> {







    // for method getPanierByClient in PanierHelper
    @Query(value = "SELECT * FROM PANIER P, LIGNE_PANIER LP WHERE LP.id_panier = P.id AND P.id_client = :id_client AND valide = 0" , nativeQuery = true)
    Panier getPanierByClient(@Param("id_client") int id_client);




    // for method add in PanierHelper
    @Query(value = "INSERT INTO PANIER(id,date_creation,id_client,valide,etat) VALUES (DEFAULT,NOW(),:id_client,0,2)" , nativeQuery = true)
    Panier add(@Param("id_client") int id_client);




















}
