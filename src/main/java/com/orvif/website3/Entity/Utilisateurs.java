package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "utilisateurs", schema = "t_orvif_web_dev")
public class Utilisateurs {
    private int idUtilisateurs;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;
    private int profil;
    private String salt1;
    private String salt2;
    private Profils profilsByProfil;//

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
}
