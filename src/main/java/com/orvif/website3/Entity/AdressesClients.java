package com.orvif.website3.Entity;



import com.orvif.website3.Entity.CompositeKeys.AdressesClientsPK;

import javax.persistence.*;

@Entity
@Table(name = "adresses_clients", schema = "t_orvif_web_dev")
@IdClass(AdressesClientsPK.class)
public class AdressesClients {
    private int idClient;
    private int idAdresse;
    private Client clientByIdClient;
    private Adresse adresseByIdAdresse;



    @Id
    @Column(name = "id_client", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Id
    @Column(name = "id_adresse", nullable = false)
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

        AdressesClients that = (AdressesClients) o;

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

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Client getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(Client clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "id_adresse", referencedColumnName = "id_adresse", nullable = false, insertable = false, updatable = false)
    public Adresse getAdresseByIdAdresse() {
        return adresseByIdAdresse;
    }

    public void setAdresseByIdAdresse(Adresse adresseByIdAdresse) {
        this.adresseByIdAdresse = adresseByIdAdresse;
    }


    public AdressesClients() {
    }


}
