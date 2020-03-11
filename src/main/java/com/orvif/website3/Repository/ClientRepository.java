package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ClientRepository extends JpaRepository<Client, Integer> {



    // for method uniqueLogin in UtilisateursHelper
    @Query( value = "SELECT * FROM CLIENT WHERE identifiant = :identifiant", nativeQuery = true)
    Client findByIdentifiant(@Param("identifiant") String identifiant);


    // for method getByMail in UtilisateursHelper
    @Query( value = "SELECT * FROM CLIENT WHERE upper(mail) = :mail", nativeQuery = true)
    Client getByMail(@Param("mail") String mail);



    // for method resetPassword in UtilisateursHelper
    @Query(value = "UPDATE CLIENT SET mdp = :mdp,salt1 = :salt1, salt2 = :salt2 WHERE idUtilisateurs = :idUtilisateurs" , nativeQuery = true)
    void resetPassword(@Param("idUtilisateurs") int idUtilisateurs, @Param("salt2") String salt2, @Param("salt1") String salt1, @Param("mdp") String mdp );



    // for method inscrireUtilisateur in UtilisateursHelper
    @Query(value = "INSERT INTO CLIENT(id,num_cli_orvif,identifiant,mdp,date_derniere_connexion,mail,type_client,profil,etat,nom,prenom,telephone,salt1,salt2,mail_confirm) VALUES(DEFAULT,:num_cli_orvif,:identifiant,:mdp,NOW(),:mail,:type_client,:profil,:etat,:nom,:prenom,:telephone,:salt1,:salt2,0);" , nativeQuery = true)
    void inscrireUtilisateur(@Param("num_cli_orvif") int num_cli_orvif, @Param("identifiant") String identifiant, @Param("mdp") String mdp, @Param("mail") String mail, @Param("type_client") String type_client,@Param("profil") String profil,@Param("etat") int etat,@Param("nom") String nom,@Param("prenom") String prenom,@Param("telephone") String telephone, @Param("salt1") String salt1, @Param("salt2") String salt2);







}
