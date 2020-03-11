package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.CompositeKeys.CodeConfirmPK;
import com.orvif.website3.Entity.JobMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.DELETE;
import java.util.ArrayList;

public interface CodeConfirmRepository extends JpaRepository<CodeConfirm, CodeConfirmPK> {






    // for method isUnique in CodeConfirmHelper
    @Query(value = "SELECT * FROM CODE_CONFIRM WHERE code = :code" , nativeQuery = true)
    ArrayList<CodeConfirm> isUnique(@Param("code") String code);


    // for method deleteByClient in CodeConfirmHelper
    @Query(value = "DELETE FROM CODE_CONFIRM WHERE client = :client" , nativeQuery = true)
    void deleteByClient(@Param("client") int client);



    // for method add in CodeConfirmHelper
    @Query(value = "INSERT INTO CODE_CONFIRM(code,client,date,type_code) VALUES(:code,:client,NOW()+INTERVAL 2 DAY,:type_code)" , nativeQuery = true)
    void addCustom(@Param("code") String code, @Param("client") int client, @Param("type_code") String type_code);


    // for method removeByCode in CodeConfirmHelper
    @Query(value = "DELETE FROM CODE_CONFIRM WHERE code = :code" , nativeQuery = true)
    void deleteByCode(@Param("code") String code);



    // for method getByCode in CodeConfirmHelper
    @Query(value = "SELECT * FROM CODE_CONFIRM WHERE code = :code" , nativeQuery = true)
    CodeConfirm findByCode(@Param("code") String code);















}
