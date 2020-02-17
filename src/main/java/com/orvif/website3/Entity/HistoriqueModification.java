package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.HistoriqueModificationPK;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "historique_modification", schema = "t_orvif_web_dev")
@IdClass(HistoriqueModificationPK.class)
public class HistoriqueModification {
    private int id;
    private int idProduit;
    private String champModif;
    private String valInitial;
    private String valModif;
    private Timestamp dateModif;
    private String modificateur;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "id_produit", nullable = false)
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Basic
    @Column(name = "champ_modif", nullable = false, length = 60)
    public String getChampModif() {
        return champModif;
    }

    public void setChampModif(String champModif) {
        this.champModif = champModif;
    }

    @Basic
    @Column(name = "val_initial", nullable = false, length = 100)
    public String getValInitial() {
        return valInitial;
    }

    public void setValInitial(String valInitial) {
        this.valInitial = valInitial;
    }

    @Basic
    @Column(name = "val_modif", nullable = true, length = 100)
    public String getValModif() {
        return valModif;
    }

    public void setValModif(String valModif) {
        this.valModif = valModif;
    }

    @Basic
    @Column(name = "date_modif", nullable = true)
    public Timestamp getDateModif() {
        return dateModif;
    }

    public void setDateModif(Timestamp dateModif) {
        this.dateModif = dateModif;
    }

    @Basic
    @Column(name = "modificateur", nullable = true, length = 45)
    public String getModificateur() {
        return modificateur;
    }

    public void setModificateur(String modificateur) {
        this.modificateur = modificateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoriqueModification that = (HistoriqueModification) o;

        if (id != that.id) return false;
        if (idProduit != that.idProduit) return false;
        if (champModif != null ? !champModif.equals(that.champModif) : that.champModif != null) return false;
        if (valInitial != null ? !valInitial.equals(that.valInitial) : that.valInitial != null) return false;
        if (valModif != null ? !valModif.equals(that.valModif) : that.valModif != null) return false;
        if (dateModif != null ? !dateModif.equals(that.dateModif) : that.dateModif != null) return false;
        if (modificateur != null ? !modificateur.equals(that.modificateur) : that.modificateur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProduit;
        result = 31 * result + (champModif != null ? champModif.hashCode() : 0);
        result = 31 * result + (valInitial != null ? valInitial.hashCode() : 0);
        result = 31 * result + (valModif != null ? valModif.hashCode() : 0);
        result = 31 * result + (dateModif != null ? dateModif.hashCode() : 0);
        result = 31 * result + (modificateur != null ? modificateur.hashCode() : 0);
        return result;
    }
}
