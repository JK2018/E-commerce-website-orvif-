package com.orvif.website3.Entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "fidelite", schema = "t_orvif_web_dev")
public class Fidelite {
    private int idFidelite;
    private String civilite;
    private String nomClient;
    private String prenomClient;
    private String numClient;
    private String telFixe;
    private String telMobile;
    private String email;
    private String adresse1;
    private String adresse2;
    private String codePostal;
    private String ville;
    private Date dateInscription;
    private Byte valide;
    private String numCarteFid;
    private Double montantCahtAvant;
    private Double montantCahtApres;
    private Integer soldePoints;
    private Integer idAdelya;
    private Byte bloque;
    private String numCliPrincipal;

    @Id
    @Column(name = "id_fidelite", nullable = false)
    public int getIdFidelite() {
        return idFidelite;
    }

    public void setIdFidelite(int idFidelite) {
        this.idFidelite = idFidelite;
    }

    @Basic
    @Column(name = "civilite", nullable = true, length = 45)
    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    @Basic
    @Column(name = "nom_client", nullable = true, length = 75)
    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    @Basic
    @Column(name = "prenom_client", nullable = true, length = 75)
    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    @Basic
    @Column(name = "num_client", nullable = false, length = 11)
    public String getNumClient() {
        return numClient;
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    @Basic
    @Column(name = "tel_fixe", nullable = true, length = 11)
    public String getTelFixe() {
        return telFixe;
    }

    public void setTelFixe(String telFixe) {
        this.telFixe = telFixe;
    }

    @Basic
    @Column(name = "tel_mobile", nullable = true, length = 11)
    public String getTelMobile() {
        return telMobile;
    }

    public void setTelMobile(String telMobile) {
        this.telMobile = telMobile;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 75)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "adresse1", nullable = true, length = 75)
    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    @Basic
    @Column(name = "adresse2", nullable = true, length = 75)
    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    @Basic
    @Column(name = "code_postal", nullable = true, length = 5)
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Basic
    @Column(name = "ville", nullable = true, length = 75)
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "date_inscription", nullable = true)
    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Basic
    @Column(name = "valide", nullable = true)
    public Byte getValide() {
        return valide;
    }

    public void setValide(Byte valide) {
        this.valide = valide;
    }

    @Basic
    @Column(name = "num_carte_fid", nullable = false, length = 45)
    public String getNumCarteFid() {
        return numCarteFid;
    }

    public void setNumCarteFid(String numCarteFid) {
        this.numCarteFid = numCarteFid;
    }

    @Basic
    @Column(name = "montant_CAHT_avant", nullable = true, precision = 0)
    public Double getMontantCahtAvant() {
        return montantCahtAvant;
    }

    public void setMontantCahtAvant(Double montantCahtAvant) {
        this.montantCahtAvant = montantCahtAvant;
    }

    @Basic
    @Column(name = "montant_CAHT_apres", nullable = true, precision = 0)
    public Double getMontantCahtApres() {
        return montantCahtApres;
    }

    public void setMontantCahtApres(Double montantCahtApres) {
        this.montantCahtApres = montantCahtApres;
    }

    @Basic
    @Column(name = "solde_points", nullable = true)
    public Integer getSoldePoints() {
        return soldePoints;
    }

    public void setSoldePoints(Integer soldePoints) {
        this.soldePoints = soldePoints;
    }

    @Basic
    @Column(name = "id_adelya", nullable = true)
    public Integer getIdAdelya() {
        return idAdelya;
    }

    public void setIdAdelya(Integer idAdelya) {
        this.idAdelya = idAdelya;
    }

    @Basic
    @Column(name = "bloque", nullable = true)
    public Byte getBloque() {
        return bloque;
    }

    public void setBloque(Byte bloque) {
        this.bloque = bloque;
    }

    @Basic
    @Column(name = "num_cli_principal", nullable = true, length = 11)
    public String getNumCliPrincipal() {
        return numCliPrincipal;
    }

    public void setNumCliPrincipal(String numCliPrincipal) {
        this.numCliPrincipal = numCliPrincipal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fidelite fidelite = (Fidelite) o;

        if (idFidelite != fidelite.idFidelite) return false;
        if (civilite != null ? !civilite.equals(fidelite.civilite) : fidelite.civilite != null) return false;
        if (nomClient != null ? !nomClient.equals(fidelite.nomClient) : fidelite.nomClient != null) return false;
        if (prenomClient != null ? !prenomClient.equals(fidelite.prenomClient) : fidelite.prenomClient != null)
            return false;
        if (numClient != null ? !numClient.equals(fidelite.numClient) : fidelite.numClient != null) return false;
        if (telFixe != null ? !telFixe.equals(fidelite.telFixe) : fidelite.telFixe != null) return false;
        if (telMobile != null ? !telMobile.equals(fidelite.telMobile) : fidelite.telMobile != null) return false;
        if (email != null ? !email.equals(fidelite.email) : fidelite.email != null) return false;
        if (adresse1 != null ? !adresse1.equals(fidelite.adresse1) : fidelite.adresse1 != null) return false;
        if (adresse2 != null ? !adresse2.equals(fidelite.adresse2) : fidelite.adresse2 != null) return false;
        if (codePostal != null ? !codePostal.equals(fidelite.codePostal) : fidelite.codePostal != null) return false;
        if (ville != null ? !ville.equals(fidelite.ville) : fidelite.ville != null) return false;
        if (dateInscription != null ? !dateInscription.equals(fidelite.dateInscription) : fidelite.dateInscription != null)
            return false;
        if (valide != null ? !valide.equals(fidelite.valide) : fidelite.valide != null) return false;
        if (numCarteFid != null ? !numCarteFid.equals(fidelite.numCarteFid) : fidelite.numCarteFid != null)
            return false;
        if (montantCahtAvant != null ? !montantCahtAvant.equals(fidelite.montantCahtAvant) : fidelite.montantCahtAvant != null)
            return false;
        if (montantCahtApres != null ? !montantCahtApres.equals(fidelite.montantCahtApres) : fidelite.montantCahtApres != null)
            return false;
        if (soldePoints != null ? !soldePoints.equals(fidelite.soldePoints) : fidelite.soldePoints != null)
            return false;
        if (idAdelya != null ? !idAdelya.equals(fidelite.idAdelya) : fidelite.idAdelya != null) return false;
        if (bloque != null ? !bloque.equals(fidelite.bloque) : fidelite.bloque != null) return false;
        if (numCliPrincipal != null ? !numCliPrincipal.equals(fidelite.numCliPrincipal) : fidelite.numCliPrincipal != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFidelite;
        result = 31 * result + (civilite != null ? civilite.hashCode() : 0);
        result = 31 * result + (nomClient != null ? nomClient.hashCode() : 0);
        result = 31 * result + (prenomClient != null ? prenomClient.hashCode() : 0);
        result = 31 * result + (numClient != null ? numClient.hashCode() : 0);
        result = 31 * result + (telFixe != null ? telFixe.hashCode() : 0);
        result = 31 * result + (telMobile != null ? telMobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (adresse1 != null ? adresse1.hashCode() : 0);
        result = 31 * result + (adresse2 != null ? adresse2.hashCode() : 0);
        result = 31 * result + (codePostal != null ? codePostal.hashCode() : 0);
        result = 31 * result + (ville != null ? ville.hashCode() : 0);
        result = 31 * result + (dateInscription != null ? dateInscription.hashCode() : 0);
        result = 31 * result + (valide != null ? valide.hashCode() : 0);
        result = 31 * result + (numCarteFid != null ? numCarteFid.hashCode() : 0);
        result = 31 * result + (montantCahtAvant != null ? montantCahtAvant.hashCode() : 0);
        result = 31 * result + (montantCahtApres != null ? montantCahtApres.hashCode() : 0);
        result = 31 * result + (soldePoints != null ? soldePoints.hashCode() : 0);
        result = 31 * result + (idAdelya != null ? idAdelya.hashCode() : 0);
        result = 31 * result + (bloque != null ? bloque.hashCode() : 0);
        result = 31 * result + (numCliPrincipal != null ? numCliPrincipal.hashCode() : 0);
        return result;
    }
}
