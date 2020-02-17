package com.orvif.website3.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mail_contact", schema = "t_orvif_web_dev")
public class MailContact {
    private int id;
    private String commentaire;
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private String fax;
    private String raiSoc;
    private String formeJuridique;
    private String numSiret;
    private String adresse1;
    private String adresse2;
    private String ville;
    private String codePostal;
    private String civilite;
    private Timestamp date;
    private int idMotif;
    private MotifContact motifContactByIdMotif;//

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "commentaire", nullable = true, length = 1000)
    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
    @Column(name = "nom", nullable = false, length = 45)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 45)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "fax", nullable = true, length = 45)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "rai_soc", nullable = true, length = 45)
    public String getRaiSoc() {
        return raiSoc;
    }

    public void setRaiSoc(String raiSoc) {
        this.raiSoc = raiSoc;
    }

    @Basic
    @Column(name = "forme_juridique", nullable = true, length = 45)
    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    @Basic
    @Column(name = "num_siret", nullable = true, length = 45)
    public String getNumSiret() {
        return numSiret;
    }

    public void setNumSiret(String numSiret) {
        this.numSiret = numSiret;
    }

    @Basic
    @Column(name = "adresse1", nullable = true, length = 45)
    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    @Basic
    @Column(name = "adresse2", nullable = true, length = 45)
    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    @Basic
    @Column(name = "ville", nullable = true, length = 45)
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "code_postal", nullable = false, length = 10)
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Basic
    @Column(name = "civilite", nullable = false, length = 20)
    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "id_motif", nullable = false, insertable = false, updatable = false)
    public int getIdMotif() {
        return idMotif;
    }

    public void setIdMotif(int idMotif) {
        this.idMotif = idMotif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailContact that = (MailContact) o;

        if (id != that.id) return false;
        if (idMotif != that.idMotif) return false;
        if (commentaire != null ? !commentaire.equals(that.commentaire) : that.commentaire != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (raiSoc != null ? !raiSoc.equals(that.raiSoc) : that.raiSoc != null) return false;
        if (formeJuridique != null ? !formeJuridique.equals(that.formeJuridique) : that.formeJuridique != null)
            return false;
        if (numSiret != null ? !numSiret.equals(that.numSiret) : that.numSiret != null) return false;
        if (adresse1 != null ? !adresse1.equals(that.adresse1) : that.adresse1 != null) return false;
        if (adresse2 != null ? !adresse2.equals(that.adresse2) : that.adresse2 != null) return false;
        if (ville != null ? !ville.equals(that.ville) : that.ville != null) return false;
        if (codePostal != null ? !codePostal.equals(that.codePostal) : that.codePostal != null) return false;
        if (civilite != null ? !civilite.equals(that.civilite) : that.civilite != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentaire != null ? commentaire.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (raiSoc != null ? raiSoc.hashCode() : 0);
        result = 31 * result + (formeJuridique != null ? formeJuridique.hashCode() : 0);
        result = 31 * result + (numSiret != null ? numSiret.hashCode() : 0);
        result = 31 * result + (adresse1 != null ? adresse1.hashCode() : 0);
        result = 31 * result + (adresse2 != null ? adresse2.hashCode() : 0);
        result = 31 * result + (ville != null ? ville.hashCode() : 0);
        result = 31 * result + (codePostal != null ? codePostal.hashCode() : 0);
        result = 31 * result + (civilite != null ? civilite.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + idMotif;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_motif", referencedColumnName = "id", nullable = false)
    public MotifContact getMotifContactByIdMotif() {
        return motifContactByIdMotif;
    }

    public void setMotifContactByIdMotif(MotifContact motifContactByIdMotif) {
        this.motifContactByIdMotif = motifContactByIdMotif;
    }
}
