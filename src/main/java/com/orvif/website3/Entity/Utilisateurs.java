package com.orvif.website3.Entity;

import com.orvif.website3.bean.Encours;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "utilisateurs", schema = "t_orvif_web_dev")
public class Utilisateurs {
    private static final long serialVersionUID = 9107137246805194887L;//
    private int idUtilisateurs;
    private int numCli;//
    private Date lastSignin;//
    private String mail;//
    private String type_client;//
    private String etat;//
    private String telephone;//
    private String numeroSiren;//
    private String nomSociete;//
    private Adresse companyAdress;//
    private Encours encours;//
    private boolean bloqued = false; //Client bloqué/non bloqué (que pour les pros)//
    private boolean closed = false; //Client fermé (=supprimé)/ non fermé (que pour les pros)//
    private boolean verifiedMail;//
    private String nom;
    private String prenom;
    private String login;
    private String mdp;
    private int profil;
    private String salt1;
    private String salt2;
    private Profils profilsByProfil;//
    private List<Adresse> adresseCollection = new ArrayList<>();//

    @Id
    @Column(name = "id_utilisateurs", nullable = false)
    public int getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
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
    @Column(name = "login", nullable = false, length = 45)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "mdp", nullable = false, length = 45)
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Basic
    @Column(name = "profil", nullable = false, insertable = false, updatable = false)
    public int getProfil() {
        return profil;
    }

    public void setProfil(int profil) {
        this.profil = profil;
    }

    @Basic
    @Column(name = "salt1", nullable = false, length = 10)
    public String getSalt1() {
        return salt1;
    }

    public void setSalt1(String salt1) {
        this.salt1 = salt1;
    }

    @Basic
    @Column(name = "salt2", nullable = false, length = 10)
    public String getSalt2() {
        return salt2;
    }

    public void setSalt2(String salt2) {
        this.salt2 = salt2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateurs that = (Utilisateurs) o;

        if (idUtilisateurs != that.idUtilisateurs) return false;
        if (profil != that.profil) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (mdp != null ? !mdp.equals(that.mdp) : that.mdp != null) return false;
        if (salt1 != null ? !salt1.equals(that.salt1) : that.salt1 != null) return false;
        if (salt2 != null ? !salt2.equals(that.salt2) : that.salt2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUtilisateurs;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (mdp != null ? mdp.hashCode() : 0);
        result = 31 * result + profil;
        result = 31 * result + (salt1 != null ? salt1.hashCode() : 0);
        result = 31 * result + (salt2 != null ? salt2.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "profil", referencedColumnName = "id_profils", nullable = false)
    public Profils getProfilsByProfil() {
        return profilsByProfil;
    }

    public void setProfilsByProfil(Profils profilsByProfil) {
        this.profilsByProfil = profilsByProfil;
    }




    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getNumCli() {
        return numCli;
    }

    public void setNumCli(int numCli) {
        this.numCli = numCli;
    }

    public Date getLastSignin() {
        return lastSignin;
    }

    public void setLastSignin(Date lastSignin) {
        this.lastSignin = lastSignin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType_client() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client = type_client;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumeroSiren() {
        return numeroSiren;
    }

    public void setNumeroSiren(String numeroSiren) {
        this.numeroSiren = numeroSiren;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

   /** public Adresse getCompanyAdress() {
        return companyAdress;
    }**/

    public void setCompanyAdress(Adresse companyAdress) {
        this.companyAdress = companyAdress;
    }

    public Encours getEncours() {
        return encours;
    }

    public void setEncours(Encours encours) {
        this.encours = encours;
    }

    public boolean isBloqued() {
        return bloqued;
    }

    public void setBloqued(boolean bloqued) {
        this.bloqued = bloqued;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isVerifiedMail() {
        return verifiedMail;
    }

    public void setVerifiedMail(boolean verifiedMail) {
        this.verifiedMail = verifiedMail;
    }

    @Transient
    public List<Adresse> getAdresseCollection() {
        return adresseCollection;
    }

    public void setAdresseCollection(List<Adresse> adresseCollection) {
        this.adresseCollection = adresseCollection;
    }
}
