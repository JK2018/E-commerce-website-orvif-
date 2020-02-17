package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "job_mission", schema = "t_orvif_web_dev")
public class JobMission {
    private int id;
    private int idJob;
    private String mission;
    private JobOffer jobOfferByIdJob;//
    //private JobOffer jobOfferByIdJob_0;//

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ID_JOB", nullable = false, insertable = false, updatable = false)
    public int getIdJob() {
        return idJob;
    }

    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }

    @Basic
    @Column(name = "MISSION", nullable = false, length = -1)
    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobMission that = (JobMission) o;

        if (id != that.id) return false;
        if (idJob != that.idJob) return false;
        if (mission != null ? !mission.equals(that.mission) : that.mission != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idJob;
        result = 31 * result + (mission != null ? mission.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_JOB", referencedColumnName = "id", nullable = false)
    public JobOffer getJobOfferByIdJob() {
        return jobOfferByIdJob;
    }

    public void setJobOfferByIdJob(JobOffer jobOfferByIdJob) {
        this.jobOfferByIdJob = jobOfferByIdJob;
    }

    /**
     @ManyToOne
     @JoinColumn(name = "ID_JOB", referencedColumnName = "id", nullable = false)
     public JobOffer getJobOfferByIdJob_0() {
     return jobOfferByIdJob_0;
     }

     public void setJobOfferByIdJob_0(JobOffer jobOfferByIdJob_0) {
     this.jobOfferByIdJob_0 = jobOfferByIdJob_0;
     **/
}
