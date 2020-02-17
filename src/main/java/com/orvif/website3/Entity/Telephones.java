package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "telephones", schema = "t_orvif_web_dev")
public class Telephones {
    private int idTels;
    private String numero;

    @Id
    @Column(name = "id_tels", nullable = false)
    public int getIdTels() {
        return idTels;
    }

    public void setIdTels(int idTels) {
        this.idTels = idTels;
    }

    @Basic
    @Column(name = "numero", nullable = false, length = 15)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telephones that = (Telephones) o;

        if (idTels != that.idTels) return false;
        if (numero != null ? !numero.equals(that.numero) : that.numero != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTels;
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }
}
