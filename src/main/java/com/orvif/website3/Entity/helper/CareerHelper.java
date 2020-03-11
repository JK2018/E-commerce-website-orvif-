package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.JobMission;
import com.orvif.website3.Entity.JobOffer;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.JobMissionRepository;
import com.orvif.website3.Repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class CareerHelper {

    @Autowired
    private JobOfferRepository jor;

    @Autowired
    private JobMissionRepository jmr;



    public void add(JobOffer career) throws DAOException {
        try {
          jor.save(career);
          jor.flush();
            this.addAllMissions(career);
        } catch (SQLException e) {
            throw new DAOException("Creating job offer failed, SQL error occured : " + e.getMessage());
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Creating job offer failed, " + e.getClass().getName() + " error occured.");
        }
    }





    public void delete(JobOffer career) throws DAOException {
        try {
            jor.delete(career);
            jor.flush();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Deleting job offer failed, " + e.getClass().getName() + " error occured.");
        }
    }



    public List<JobOffer> getAll() throws DAOException {
        List<JobOffer> careerList;
        List<JobMission> missions;
        try {
            careerList = jor.findAll();
           for(JobOffer j : careerList){
               missions = jmr.findByIdCustom(j.getId());

               for(JobMission m : missions){
                   j.getJobMissionsById().add(m); // MAKE SURE THEY WERE NOT ALREADY THERE AND NOW ARE DUPLICATED..
               }
           }
            return careerList;
        }  catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Fetching job offer failed, " + e.getClass().getName() + " error occured.");
        }
    }





    public JobOffer get(int id) throws DAOException {
        JobOffer career;
        JobMission m;
        try {
            career = jor.findById(id).get();
            m = jmr.findById(career.getId()).get();
            career.getJobMissionsById().add(m);// MAKE SURE THEY WERE NOT ALREADY THERE AND NOW ARE DUPLICATED..
            return career;
        }  catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("Fetching job offer failed, " + e.getClass().getName() + " error occured.");
        }
    }





    private void addAllMissions(JobOffer career) throws Exception {
        try {
            for (JobMission mission : career.getJobMissionsById()) {
                jmr.save(mission);
                jmr.flush();
            }
        } catch (Exception e){
        }
    }












}
