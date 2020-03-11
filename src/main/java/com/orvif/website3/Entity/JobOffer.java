package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "job_offer", schema = "t_orvif_web_dev")
public class JobOffer {
    private int id;
    private String jobTitle;
    private String jobDescription;
    private Collection<JobMission> jobMissionsById;//
    //private Collection<JobMission> jobMissionsById_0;//

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "JOB_TITLE", nullable = false, length = 150)
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Basic
    @Column(name = "JOB_DESCRIPTION", nullable = false, length = -1)
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobOffer jobOffer = (JobOffer) o;

        if (id != jobOffer.id) return false;
        if (jobTitle != null ? !jobTitle.equals(jobOffer.jobTitle) : jobOffer.jobTitle != null) return false;
        if (jobDescription != null ? !jobDescription.equals(jobOffer.jobDescription) : jobOffer.jobDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (jobDescription != null ? jobDescription.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "jobOfferByIdJob")
    public Collection<JobMission> getJobMissionsById() {
        return jobMissionsById;
    }

    public void setJobMissionsById(Collection<JobMission> jobMissionsById) {
        this.jobMissionsById = jobMissionsById;
    }
/**
 @OneToMany(mappedBy = "jobOfferByIdJob_0")
 public Collection<JobMission> getJobMissionsById_0() {
 return jobMissionsById_0;
 }

 public void setJobMissionsById_0(Collection<JobMission> jobMissionsById_0) {
 this.jobMissionsById_0 = jobMissionsById_0;
 }**/
}
