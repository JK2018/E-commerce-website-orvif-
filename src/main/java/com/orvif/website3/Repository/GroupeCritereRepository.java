package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.GroupeCriterePK;
import com.orvif.website3.Entity.Groupe;
import com.orvif.website3.Entity.GroupeCritere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface GroupeCritereRepository extends JpaRepository<GroupeCritere, GroupeCriterePK> {


    // for method getByProduct in GroupHelper
    @Query(value = "SELECT * FROM GROUPE_CRITERE WHERE idGroup = :idGroup" , nativeQuery = true)
    ArrayList<GroupeCritere> getByProduct(@Param("idGroup") int idGroup);


}
