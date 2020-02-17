package com.orvif.website3.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "client", schema = "t_orvif_web_dev")
public class Client {
    private int id;
    private String numCliOrvif;
    private String identifiant;
    private String mdp;
    private Date dateDerniereConnexion;
    private String mail;
    private String typeClient;
    private String profil;
    private byte etat;
    private String nom;
    private String prenom;
    private String telephone;
    private String salt1;
    private String salt2;
    private int mailConfirm;
    private Collection<AdressesClients> adressesClientsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "num_cli_orvif", nullable = true, length = 5)
    public String getNumCliOrvif() {
        return numCliOrvif;
    }

    public void setNumCliOrvif(String numCliOrvif) {
        this.numCliOrvif = numCliOrvif;
    }

    @Basic
    @Column(name = "identifiant", nullable = false, length = 45)
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    @Basic
    @Column(name = "mdp", nullable = false, length = 100)
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Basic
    @Column(name = "date_derniere_connexion", nullable = false)
    public Date getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public void setDateDerniereConnexion(Date dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 45)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "type_client", nullable = false, length = 45)
    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    @Basic
    @Column(name = "profil", nullable = true, length = 45)
    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    @Basic
    @Column(name = "etat", nullable = false)
    public byte getEtat() {
        return etat;
    }

    public void setEtat(byte etat) {
        this.etat = etat;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 45)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = false, length = 45)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 45)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "salt1", nullable = false, length = 45)
    public String getSalt1() {
        return salt1;
    }

    public void setSalt1(String salt1) {
        this.salt1 = salt1;
    }

    @Basic
    @Column(name = "salt2", nullable = false, length = 45)
    public String getSalt2() {
        return salt2;
    }

    public void setSalt2(String salt2) {
        this.salt2 = salt2;
    }

    @Basic
    @Column(name = "mail_confirm", nullable = false)
    public int getMailConfirm() {
        return mailConfirm;
    }

    public void setMailConfirm(int mailConfirm) {
        this.mailConfirm = mailConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (etat != client.etat) return false;
        if (mailConfirm != client.mailConfirm) return false;
        if (numCliOrvif != null ? !numCliOrvif.equals(client.numCliOrvif) : client.numCliOrvif != null) return false;
        if (identifiant != null ? !identifiant.equals(client.identifiant) : client.identifiant != null) return false;
        if (mdp != null ? !mdp.equals(client.mdp) : client.mdp != null) return false;
        if (dateDerniereConnexion != null ? !dateDerniereConnexion.equals(client.dateDerniereConnexion) : client.dateDerniereConnexion != null)
            return false;
        if (mail != null ? !mail.equals(client.mail) : client.mail != null) return false;
        if (typeClient != null ? !typeClient.equals(client.typeClient) : client.typeClient != null) return false;
        if (profil != null ? !profil.equals(client.profil) : client.profil != null) return false;
        if (nom != null ? !nom.equals(client.nom) : client.nom != null) return false;
        if (prenom != null ? !prenom.equals(client.prenom) : client.prenom != null) return false;
        if (telephone != null ? !telephone.equals(client.telephone) : client.telephone != null) return false;
        if (salt1 != null ? !salt1.equals(client.salt1) : client.salt1 != null) return false;
        if (salt2 != null ? !salt2.equals(client.salt2) : client.salt2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (numCliOrvif != null ? numCliOrvif.hashCode() : 0);
        result = 31 * result + (identifiant != null ? identifiant.hashCode() : 0);
        result = 31 * result + (mdp != null ? mdp.hashCode() : 0);
        result = 31 * result + (dateDerniereConnexion != null ? dateDerniereConnexion.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (typeClient != null ? typeClient.hashCode() : 0);
        result = 31 * result + (profil != null ? profil.hashCode() : 0);
        result = 31 * result + (int) etat;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (salt1 != null ? salt1.hashCode() : 0);
        result = 31 * result + (salt2 != null ? salt2.hashCode() : 0);
        result = 31 * result + mailConfirm;
        return result;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<AdressesClients> getAdressesClientsById() {
        return adressesClientsById;
    }

    public void setAdressesClientsById(Collection<AdressesClients> adressesClientsById) {
        this.adressesClientsById = adressesClientsById;
    }
}
