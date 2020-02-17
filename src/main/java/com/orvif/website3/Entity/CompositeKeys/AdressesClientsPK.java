package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AdressesClientsPK implements Serializable {
    private int idClient;
    private int idAdresse;

    @Column(name = "id_client", nullable = false)
    @Id
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Column(name = "id_adresse", nullable = false)
    @Id
    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdressesClientsPK that = (AdressesClientsPK) o;

        if (idClient != that.idClient) return false;
        if (idAdresse != that.idAdresse) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + idAdresse;
        return result;
    }
}
