package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "stock", schema = "t_orvif_web_dev")
public class Stock {
    private int cleSystemProduit;
    private int sud;
    private int nord;
    private int bastille;
    private int melun;
    private int est;
    private int montmartre;
    private int lcdr;
    private int seh53;
    private int bagneux;
    private int orly;

    @Id
    @Column(name = "cle_system_produit", nullable = false)
    public int getCleSystemProduit() {
        return cleSystemProduit;
    }

    public void setCleSystemProduit(int cleSystemProduit) {
        this.cleSystemProduit = cleSystemProduit;
    }

    @Basic
    @Column(name = "SUD", nullable = false)
    public int getSud() {
        return sud;
    }

    public void setSud(int sud) {
        this.sud = sud;
    }

    @Basic
    @Column(name = "NORD", nullable = false)
    public int getNord() {
        return nord;
    }

    public void setNord(int nord) {
        this.nord = nord;
    }

    @Basic
    @Column(name = "BASTILLE", nullable = false)
    public int getBastille() {
        return bastille;
    }

    public void setBastille(int bastille) {
        this.bastille = bastille;
    }

    @Basic
    @Column(name = "MELUN", nullable = false)
    public int getMelun() {
        return melun;
    }

    public void setMelun(int melun) {
        this.melun = melun;
    }

    @Basic
    @Column(name = "EST", nullable = false)
    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    @Basic
    @Column(name = "MONTMARTRE", nullable = false)
    public int getMontmartre() {
        return montmartre;
    }

    public void setMontmartre(int montmartre) {
        this.montmartre = montmartre;
    }

    @Basic
    @Column(name = "LCDR", nullable = false)
    public int getLcdr() {
        return lcdr;
    }

    public void setLcdr(int lcdr) {
        this.lcdr = lcdr;
    }

    @Basic
    @Column(name = "SEH53", nullable = false)
    public int getSeh53() {
        return seh53;
    }

    public void setSeh53(int seh53) {
        this.seh53 = seh53;
    }

    @Basic
    @Column(name = "BAGNEUX", nullable = false)
    public int getBagneux() {
        return bagneux;
    }

    public void setBagneux(int bagneux) {
        this.bagneux = bagneux;
    }

    @Basic
    @Column(name = "ORLY", nullable = false)
    public int getOrly() {
        return orly;
    }

    public void setOrly(int orly) {
        this.orly = orly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (cleSystemProduit != stock.cleSystemProduit) return false;
        if (sud != stock.sud) return false;
        if (nord != stock.nord) return false;
        if (bastille != stock.bastille) return false;
        if (melun != stock.melun) return false;
        if (est != stock.est) return false;
        if (montmartre != stock.montmartre) return false;
        if (lcdr != stock.lcdr) return false;
        if (seh53 != stock.seh53) return false;
        if (bagneux != stock.bagneux) return false;
        if (orly != stock.orly) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cleSystemProduit;
        result = 31 * result + sud;
        result = 31 * result + nord;
        result = 31 * result + bastille;
        result = 31 * result + melun;
        result = 31 * result + est;
        result = 31 * result + montmartre;
        result = 31 * result + lcdr;
        result = 31 * result + seh53;
        result = 31 * result + bagneux;
        result = 31 * result + orly;
        return result;
    }
}
