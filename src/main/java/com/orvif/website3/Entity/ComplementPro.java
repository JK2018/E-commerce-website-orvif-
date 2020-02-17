package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "complement_pro", schema = "t_orvif_web_dev")
public class ComplementPro {
    private int idClient;
    private String numeroSiren;
    private String nomSociete;

    @Id
    @Column(name = "id_client", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "numero_siren", nullable = false, length = 45)
    public String getNumeroSiren() {
        return numeroSiren;
    }

    public void setNumeroSiren(String numeroSiren) {
        this.numeroSiren = numeroSiren;
    }

    @Basic
    @Column(name = "nom_societe", nullable = false, length = 45)
    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplementPro that = (ComplementPro) o;

        if (idClient != that.idClient) return false;
        if (numeroSiren != null ? !numeroSiren.equals(that.numeroSiren) : that.numeroSiren != null) return false;
        if (nomSociete != null ? !nomSociete.equals(that.nomSociete) : that.nomSociete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + (numeroSiren != null ? numeroSiren.hashCode() : 0);
        result = 31 * result + (nomSociete != null ? nomSociete.hashCode() : 0);
        return result;
    }
}
