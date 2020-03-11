package com.orvif.website3.Repository;


import com.orvif.website3.Entity.JobMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface JobMissionRepository extends JpaRepository<JobMission, Integer> {



    // for method getAll in CareerHelper
    @Query(value = "SELECT * FROM JOB_MISSION WHERE ID_JOB = :ID_JOB" , nativeQuery = true)
    ArrayList<JobMission> findByIdCustom(@Param("ID_JOB") int ID_JOB);









}
