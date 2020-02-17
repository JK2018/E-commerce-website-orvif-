package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.GroupeCriterePK;

import javax.persistence.*;

@Entity
@Table(name = "groupe_critere", schema = "t_orvif_web_dev")
@IdClass(GroupeCriterePK.class)
public class GroupeCritere {
    private int idGroup;
    private int idCritere;

    @Id
    @Column(name = "idGroup", nullable = false)
    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Id
    @Column(name = "idCritere", nullable = false)
    public int getIdCritere() {
        return idCritere;
    }

    public void setIdCritere(int idCritere) {
        this.idCritere = idCritere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupeCritere that = (GroupeCritere) o;

        if (idGroup != that.idGroup) return false;
        if (idCritere != that.idCritere) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idGroup;
        result = 31 * result + idCritere;
        return result;
    }
}
