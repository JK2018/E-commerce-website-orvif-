package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.TelsClientsPK;

import javax.persistence.*;

@Entity
@Table(name = "tels_clients", schema = "t_orvif_web_dev")
@IdClass(TelsClientsPK.class)
public class TelsClients {
    private int idTels;
    private int idClients;
    private String type;

    @Id
    @Column(name = "id_tels", nullable = false)
    public int getIdTels() {
        return idTels;
    }

    public void setIdTels(int idTels) {
        this.idTels = idTels;
    }

    @Id
    @Column(name = "id_clients", nullable = false)
    public int getIdClients() {
        return idClients;
    }

    public void setIdClients(int idClients) {
        this.idClients = idClients;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelsClients that = (TelsClients) o;

        if (idTels != that.idTels) return false;
        if (idClients != that.idClients) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTels;
        result = 31 * result + idClients;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
