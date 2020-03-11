package com.orvif.website3.Entity;

import com.orvif.website3.Entity.helper.AdresseHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "adresse", schema = "t_orvif_web_dev")
public class Adresse {
    private int idAdresse;
    private String nomVoie;
    private String complementVoie;
    private int codePostal;
    private String ville;
    private String pays;
    private String libelle;
    private Collection<AdressesClients> adressesClientsByIdAdresse;

    @Autowired
    private AdresseHelper ah;

    @Id
    @Column(name = "id_adresse", nullable = false)
    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    @Basic
    @Column(name = "nom_voie", nullable = false, length = 45)
    public String getNomVoie() {
        return nomVoie;
    }

    public void setNomVoie(String nomVoie) {
        this.nomVoie = nomVoie;
    }

    @Basic
    @Column(name = "complement_voie", nullable = true, length = 45)
    public String getComplementVoie() {
        return complementVoie;
    }

    public void setComplementVoie(String complementVoie) {
        this.complementVoie = complementVoie;
    }

    @Basic
    @Column(name = "code_postal", nullable = false)
    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    @Basic
    @Column(name = "ville", nullable = false, length = 45)
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "pays", nullable = false, length = 45)
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 45)
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adresse adresse = (Adresse) o;

        if (idAdresse != adresse.idAdresse) return false;
        if (codePostal != adresse.codePostal) return false;
        if (nomVoie != null ? !nomVoie.equals(adresse.nomVoie) : adresse.nomVoie != null) return false;
        if (complementVoie != null ? !complementVoie.equals(adresse.complementVoie) : adresse.complementVoie != null)
            return false;
        if (ville != null ? !ville.equals(adresse.ville) : adresse.ville != null) return false;
        if (pays != null ? !pays.equals(adresse.pays) : adresse.pays != null) return false;
        if (libelle != null ? !libelle.equals(adresse.libelle) : adresse.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAdresse;
        result = 31 * result + (nomVoie != null ? nomVoie.hashCode() : 0);
        result = 31 * result + (complementVoie != null ? complementVoie.hashCode() : 0);
        result = 31 * result + codePostal;
        result = 31 * result + (ville != null ? ville.hashCode() : 0);
        result = 31 * result + (pays != null ? pays.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "adresseByIdAdresse")
    public Collection<AdressesClients> getAdressesClientsByIdAdresse() {
        return adressesClientsByIdAdresse;
    }

    public void setAdressesClientsByIdAdresse(Collection<AdressesClients> adressesClientsByIdAdresse) {
        this.adressesClientsByIdAdresse = adressesClientsByIdAdresse;
    }


    public boolean remove(int idAdresse) {
        ah.remove(idAdresse);
        return true;
    }
}
