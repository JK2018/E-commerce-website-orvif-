package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TelsClientsPK implements Serializable {
    private int idTels;
    private int idClients;

    @Column(name = "id_tels", nullable = false)
    @Id
    public int getIdTels() {
        return idTels;
    }

    public void setIdTels(int idTels) {
        this.idTels = idTels;
    }

    @Column(name = "id_clients", nullable = false)
    @Id
    public int getIdClients() {
        return idClients;
    }

    public void setIdClients(int idClients) {
        this.idClients = idClients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelsClientsPK that = (TelsClientsPK) o;

        if (idTels != that.idTels) return false;
        if (idClients != that.idClients) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTels;
        result = 31 * result + idClients;
        return result;
    }
}
