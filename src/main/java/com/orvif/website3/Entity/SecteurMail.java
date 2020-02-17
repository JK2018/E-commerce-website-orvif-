package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "secteur_mail", schema = "t_orvif_web_dev")
public class SecteurMail {
    private int numSecteur;
    private String mail;
    private String copie;

    @Id
    @Column(name = "num_secteur", nullable = false)
    public int getNumSecteur() {
        return numSecteur;
    }

    public void setNumSecteur(int numSecteur) {
        this.numSecteur = numSecteur;
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
    @Column(name = "copie", nullable = false, length = 45)
    public String getCopie() {
        return copie;
    }

    public void setCopie(String copie) {
        this.copie = copie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecteurMail that = (SecteurMail) o;

        if (numSecteur != that.numSecteur) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (copie != null ? !copie.equals(that.copie) : that.copie != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numSecteur;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (copie != null ? copie.hashCode() : 0);
        return result;
    }
}
