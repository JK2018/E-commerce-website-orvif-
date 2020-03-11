package com.orvif.website3.Repository;


import com.orvif.website3.Entity.ComplementPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComplementProRepository extends JpaRepository<ComplementPro, Integer> {




    // for method inscrireUtilisateurPro in UtilisateursHelper
    @Query(value = "INSERT INTO COMPLEMENT_PRO(id_client,numero_siren,nom_societe) VALUES(:id_client,:numero_siren,:nom_societe)" , nativeQuery = true)
    void inscrireUtilisateurPro(@Param("id_client") int id_client, @Param("numero_siren") String numero_siren, @Param("nom_societe") String nom_societe);

























}
