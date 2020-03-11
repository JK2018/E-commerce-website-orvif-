package com.orvif.website3.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "client", schema = "t_orvif_web_dev")
public class Client {
    private int id;
    private int numCliOrvif;
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
    public int getNumCliOrvif() {
        return numCliOrvif;
    }

    public void setNumCliOrvif(int numCliOrvif) {
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


    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<AdressesClients> getAdressesClientsById() {
        return adressesClientsById;
    }

    public void setAdressesClientsById(Collection<AdressesClients> adressesClientsById) {
        this.adressesClientsById = adressesClientsById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                numCliOrvif == client.numCliOrvif &&
                etat == client.etat &&
                mailConfirm == client.mailConfirm &&
                Objects.equals(identifiant, client.identifiant) &&
                Objects.equals(mdp, client.mdp) &&
                Objects.equals(dateDerniereConnexion, client.dateDerniereConnexion) &&
                Objects.equals(mail, client.mail) &&
                Objects.equals(typeClient, client.typeClient) &&
                Objects.equals(profil, client.profil) &&
                Objects.equals(nom, client.nom) &&
                Objects.equals(prenom, client.prenom) &&
                Objects.equals(telephone, client.telephone) &&
                Objects.equals(salt1, client.salt1) &&
                Objects.equals(salt2, client.salt2) &&
                Objects.equals(adressesClientsById, client.adressesClientsById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numCliOrvif, identifiant, mdp, dateDerniereConnexion, mail, typeClient, profil, etat, nom, prenom, telephone, salt1, salt2, mailConfirm, adressesClientsById);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", numCliOrvif=" + numCliOrvif +
                ", identifiant='" + identifiant + '\'' +
                ", mdp='" + mdp + '\'' +
                ", dateDerniereConnexion=" + dateDerniereConnexion +
                ", mail='" + mail + '\'' +
                ", typeClient='" + typeClient + '\'' +
                ", profil='" + profil + '\'' +
                ", etat=" + etat +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salt1='" + salt1 + '\'' +
                ", salt2='" + salt2 + '\'' +
                ", mailConfirm=" + mailConfirm +
                ", adressesClientsById=" + adressesClientsById +
                '}';
    }
}
