package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "motif_contact", schema = "t_orvif_web_dev")
public class MotifContact {
    private int id;
    private String libelle;
    private Collection<MailContact> mailContactsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 100)
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

        MotifContact that = (MotifContact) o;

        if (id != that.id) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "motifContactByIdMotif")
    public Collection<MailContact> getMailContactsById() {
        return mailContactsById;
    }

    public void setMailContactsById(Collection<MailContact> mailContactsById) {
        this.mailContactsById = mailContactsById;
    }
}
