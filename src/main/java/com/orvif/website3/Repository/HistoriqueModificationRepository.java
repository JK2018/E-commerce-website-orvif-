package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.HistoriqueModificationPK;
import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.HistoriqueModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface HistoriqueModificationRepository extends JpaRepository<HistoriqueModification, HistoriqueModificationPK> {





    // for method getByProduit in HistoriqueModifHelper
    @Query(value = "SELECT * FROM HISTORIQUE_MODIFICATION WHERE id_produit = :id_produit ORDER BY date_modif" , nativeQuery = true)
    ArrayList<HistoriqueModification> getByProduit(@Param("id_produit") int id_produit);


















}
