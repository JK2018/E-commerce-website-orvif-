package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupeCriterePK implements Serializable {
    private int idGroup;
    private int idCritere;

    @Column(name = "idGroup", nullable = false)
    @Id
    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Column(name = "idCritere", nullable = false)
    @Id
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

        GroupeCriterePK that = (GroupeCriterePK) o;

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
