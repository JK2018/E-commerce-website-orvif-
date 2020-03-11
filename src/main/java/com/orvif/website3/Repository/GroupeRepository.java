package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.GroupePK;
import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupeRepository extends JpaRepository<Groupe, GroupePK> {



    // for method add in GroupHelper
    @Query(value = "INSERT INTO GROUPE(id,idProd) VALUES(DEFAULT,:idProd)" , nativeQuery = true)
    void getAdd1(@Param("idProd") int idProd);


    // for method add in GroupHelper
    @Query(value = "SELECT * FROM GROUPE WHERE idProd = :idProd)" , nativeQuery = true)
    Groupe getfind1(@Param("idProd") int idProd);


    // for method add in GroupHelper
    @Query(value = "INSERT INTO GROUPE(id,idProf) VALUES(:id,:idProf)" , nativeQuery = true)
    void getAdd2(@Param("id") int id, @Param("idProf") int idProf);


    // for method add in GroupHelper
    @Query(value = "INSERT INTO GROUPE_CRITERE(idGroup,idCritere) VALUES(:idGroup,:idCritere)" , nativeQuery = true)
    void getAdd3(@Param("idGroup") int idGroup, @Param("idCritere") int idCritere);



    // for method getByProduct in GroupHelper
    @Query(value = "SELECT * FROM GROUPE G WHERE G.id = (SELECT id FROM GROUPE WHERE idProd = :idProd)" , nativeQuery = true)
    Groupe getByProduct(@Param("idProd") int idProd);



    // for method removeProductFromGroup in GroupHelper
    @Query(value = "DELETE FROM GROUPE WHERE idProd = :idProd" , nativeQuery = true)
    void removeProductFromGroup(@Param("idProd") int idProd);



    // for method removeCritereFromGroup in GroupHelper
    @Query(value = "DELETE FROM GROUPE_CRITERE WHERE idGroup = :idGroup AND idCritere = :idCritere" , nativeQuery = true)
    void removeCritereFromGroup(@Param("idGroup") int idGroup, @Param("idCritere")int idCritere);













}
