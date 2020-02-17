package com.orvif.website3.Entity;

import javax.persistence.*;

@Entity
@Table(name = "reference", schema = "t_orvif_web_dev")
public class Reference {
    private int id;
    private int cleSystem;
    private String codeOrvif;
    private String refFou;
    private double ppht;
    private Double ecoMobilier;
    private Double ecoDee;
    private Integer idProd;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cle_system", nullable = false)
    public int getCleSystem() {
        return cleSystem;
    }

    public void setCleSystem(int cleSystem) {
        this.cleSystem = cleSystem;
    }

    @Basic
    @Column(name = "code_orvif", nullable = false, length = 25)
    public String getCodeOrvif() {
        return codeOrvif;
    }

    public void setCodeOrvif(String codeOrvif) {
        this.codeOrvif = codeOrvif;
    }

    @Basic
    @Column(name = "ref_fou", nullable = false, length = 25)
    public String getRefFou() {
        return refFou;
    }

    public void setRefFou(String refFou) {
        this.refFou = refFou;
    }

    @Basic
    @Column(name = "ppht", nullable = false, precision = 0)
    public double getPpht() {
        return ppht;
    }

    public void setPpht(double ppht) {
        this.ppht = ppht;
    }

    @Basic
    @Column(name = "eco_mobilier", nullable = true, precision = 0)
    public Double getEcoMobilier() {
        return ecoMobilier;
    }

    public void setEcoMobilier(Double ecoMobilier) {
        this.ecoMobilier = ecoMobilier;
    }

    @Basic
    @Column(name = "eco_dee", nullable = true, precision = 0)
    public Double getEcoDee() {
        return ecoDee;
    }

    public void setEcoDee(Double ecoDee) {
        this.ecoDee = ecoDee;
    }

    @Basic
    @Column(name = "id_prod", nullable = true)
    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (id != reference.id) return false;
        if (cleSystem != reference.cleSystem) return false;
        if (Double.compare(reference.ppht, ppht) != 0) return false;
        if (codeOrvif != null ? !codeOrvif.equals(reference.codeOrvif) : reference.codeOrvif != null) return false;
        if (refFou != null ? !refFou.equals(reference.refFou) : reference.refFou != null) return false;
        if (ecoMobilier != null ? !ecoMobilier.equals(reference.ecoMobilier) : reference.ecoMobilier != null)
            return false;
        if (ecoDee != null ? !ecoDee.equals(reference.ecoDee) : reference.ecoDee != null) return false;
        if (idProd != null ? !idProd.equals(reference.idProd) : reference.idProd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + cleSystem;
        result = 31 * result + (codeOrvif != null ? codeOrvif.hashCode() : 0);
        result = 31 * result + (refFou != null ? refFou.hashCode() : 0);
        temp = Double.doubleToLongBits(ppht);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ecoMobilier != null ? ecoMobilier.hashCode() : 0);
        result = 31 * result + (ecoDee != null ? ecoDee.hashCode() : 0);
        result = 31 * result + (idProd != null ? idProd.hashCode() : 0);
        return result;
    }
}
