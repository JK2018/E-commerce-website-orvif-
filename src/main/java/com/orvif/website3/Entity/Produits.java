package com.orvif.website3.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "produits", schema = "t_orvif_web_dev")
public class Produits {
    private int idProduits;
    private int cleSystem;
    private String codeOrvif;
    private String refFournisseur;
    private String libelle;
    private String descriptif;
    private String avantages;
    private double ppht;
    private Double ecoPart;
    private Double ecoMobilier;
    private byte visible;
    private int codeArticle;
    private Marques marquesByIdMarques;//
    private Familles famillesByIdFamilles;//
    private Categories categoriesByIdCategories;//
    private Gammes gammesByIdGammes;//
    private int idMarques;
    private int idUv;
    private int idUf;
    private int idFamilles;
    private int idSsfamilles;
    private int idCategories;
    private int idSscategories;
    private Integer idGammes;
    private Collection<Groupe> groupesByIdProduits;//
    private Collection<LignePanier> lignePaniersByIdProduits;//
    private UnitesVente unitesVenteByIdUv;//
    private UnitesFacturation unitesFacturationByIdUf;//
    private SsFamilles ssFamillesByIdSsfamilles;//
    private SsCategories ssCategoriesByIdSscategories;//

    @Id
    @Column(name = "id_produits", nullable = false)
    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
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
    @Column(name = "code_orvif", nullable = false, length = 45)
    public String getCodeOrvif() {
        return codeOrvif;
    }

    public void setCodeOrvif(String codeOrvif) {
        this.codeOrvif = codeOrvif;
    }

    @Basic
    @Column(name = "ref_fournisseur", nullable = true, length = 45)
    public String getRefFournisseur() {
        return refFournisseur;
    }

    public void setRefFournisseur(String refFournisseur) {
        this.refFournisseur = refFournisseur;
    }

    @Basic
    @Column(name = "libelle", nullable = false, length = 150)
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Basic
    @Column(name = "descriptif", nullable = false, length = -1)
    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    @Basic
    @Column(name = "avantages", nullable = true, length = -1)
    public String getAvantages() {
        return avantages;
    }

    public void setAvantages(String avantages) {
        this.avantages = avantages;
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
    @Column(name = "eco_part", nullable = true, precision = 0)
    public Double getEcoPart() {
        return ecoPart;
    }

    public void setEcoPart(Double ecoPart) {
        this.ecoPart = ecoPart;
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
    @Column(name = "visible", nullable = false)
    public byte getVisible() {
        return visible;
    }

    public void setVisible(byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "code_article", nullable = false)
    public int getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(int codeArticle) {
        this.codeArticle = codeArticle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produits produits = (Produits) o;

        if (idProduits != produits.idProduits) return false;
        if (cleSystem != produits.cleSystem) return false;
        if (Double.compare(produits.ppht, ppht) != 0) return false;
        if (visible != produits.visible) return false;
        if (codeArticle != produits.codeArticle) return false;
        if (codeOrvif != null ? !codeOrvif.equals(produits.codeOrvif) : produits.codeOrvif != null) return false;
        if (refFournisseur != null ? !refFournisseur.equals(produits.refFournisseur) : produits.refFournisseur != null)
            return false;
        if (libelle != null ? !libelle.equals(produits.libelle) : produits.libelle != null) return false;
        if (descriptif != null ? !descriptif.equals(produits.descriptif) : produits.descriptif != null) return false;
        if (avantages != null ? !avantages.equals(produits.avantages) : produits.avantages != null) return false;
        if (ecoPart != null ? !ecoPart.equals(produits.ecoPart) : produits.ecoPart != null) return false;
        if (ecoMobilier != null ? !ecoMobilier.equals(produits.ecoMobilier) : produits.ecoMobilier != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idProduits;
        result = 31 * result + cleSystem;
        result = 31 * result + (codeOrvif != null ? codeOrvif.hashCode() : 0);
        result = 31 * result + (refFournisseur != null ? refFournisseur.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (descriptif != null ? descriptif.hashCode() : 0);
        result = 31 * result + (avantages != null ? avantages.hashCode() : 0);
        temp = Double.doubleToLongBits(ppht);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ecoPart != null ? ecoPart.hashCode() : 0);
        result = 31 * result + (ecoMobilier != null ? ecoMobilier.hashCode() : 0);
        result = 31 * result + (int) visible;
        result = 31 * result + codeArticle;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_marques", referencedColumnName = "id_marques", nullable = false)
    public Marques getMarquesByIdMarques() {
        return marquesByIdMarques;
    }

    public void setMarquesByIdMarques(Marques marquesByIdMarques) {
        this.marquesByIdMarques = marquesByIdMarques;
    }

    @ManyToOne
    @JoinColumn(name = "id_familles", referencedColumnName = "id_familles", nullable = false)
    public Familles getFamillesByIdFamilles() {
        return famillesByIdFamilles;
    }

    public void setFamillesByIdFamilles(Familles famillesByIdFamilles) {
        this.famillesByIdFamilles = famillesByIdFamilles;
    }

    @ManyToOne
    @JoinColumn(name = "id_categories", referencedColumnName = "id_categories", nullable = false)
    public Categories getCategoriesByIdCategories() {
        return categoriesByIdCategories;
    }

    public void setCategoriesByIdCategories(Categories categoriesByIdCategories) {
        this.categoriesByIdCategories = categoriesByIdCategories;
    }

    @ManyToOne
    @JoinColumn(name = "id_gammes", referencedColumnName = "id_gammes")
    public Gammes getGammesByIdGammes() {
        return gammesByIdGammes;
    }

    public void setGammesByIdGammes(Gammes gammesByIdGammes) {
        this.gammesByIdGammes = gammesByIdGammes;
    }

    @Basic
    @Column(name = "id_marques", nullable = false, insertable = false, updatable = false)
    public int getIdMarques() {
        return idMarques;
    }

    public void setIdMarques(int idMarques) {
        this.idMarques = idMarques;
    }

    @Basic
    @Column(name = "id_uv", nullable = false, insertable = false, updatable = false)
    public int getIdUv() {
        return idUv;
    }

    public void setIdUv(int idUv) {
        this.idUv = idUv;
    }

    @Basic
    @Column(name = "id_uf", nullable = false, insertable = false, updatable = false)
    public int getIdUf() {
        return idUf;
    }

    public void setIdUf(int idUf) {
        this.idUf = idUf;
    }

    @Basic
    @Column(name = "id_familles", nullable = false, insertable = false, updatable = false)
    public int getIdFamilles() {
        return idFamilles;
    }

    public void setIdFamilles(int idFamilles) {
        this.idFamilles = idFamilles;
    }

    @Basic
    @Column(name = "id_ssfamilles", nullable = false, insertable = false, updatable = false)
    public int getIdSsfamilles() {
        return idSsfamilles;
    }

    public void setIdSsfamilles(int idSsfamilles) {
        this.idSsfamilles = idSsfamilles;
    }

    @Basic
    @Column(name = "id_categories", nullable = false, insertable = false, updatable = false)
    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    @Basic
    @Column(name = "id_sscategories", nullable = false, insertable = false, updatable = false)
    public int getIdSscategories() {
        return idSscategories;
    }

    public void setIdSscategories(int idSscategories) {
        this.idSscategories = idSscategories;
    }

    @Basic
    @Column(name = "id_gammes", nullable = true, insertable = false, updatable = false)
    public Integer getIdGammes() {
        return idGammes;
    }

    public void setIdGammes(Integer idGammes) {
        this.idGammes = idGammes;
    }

    @OneToMany(mappedBy = "produitsByIdProd")
    public Collection<Groupe> getGroupesByIdProduits() {
        return groupesByIdProduits;
    }

    public void setGroupesByIdProduits(Collection<Groupe> groupesByIdProduits) {
        this.groupesByIdProduits = groupesByIdProduits;
    }


    @OneToMany(mappedBy = "produitsByIdProduit")
    public Collection<LignePanier> getLignePaniersByIdProduits() {
        return lignePaniersByIdProduits;
    }

    public void setLignePaniersByIdProduits(Collection<LignePanier> lignePaniersByIdProduits) {
        this.lignePaniersByIdProduits = lignePaniersByIdProduits;
    }

    @ManyToOne
    @JoinColumn(name = "id_uv", referencedColumnName = "id_uv", nullable = false)
    public UnitesVente getUnitesVenteByIdUv() {
        return unitesVenteByIdUv;
    }

    public void setUnitesVenteByIdUv(UnitesVente unitesVenteByIdUv) {
        this.unitesVenteByIdUv = unitesVenteByIdUv;
    }

    @ManyToOne
    @JoinColumn(name = "id_uf", referencedColumnName = "id_uf", nullable = false)
    public UnitesFacturation getUnitesFacturationByIdUf() {
        return unitesFacturationByIdUf;
    }

    public void setUnitesFacturationByIdUf(UnitesFacturation unitesFacturationByIdUf) {
        this.unitesFacturationByIdUf = unitesFacturationByIdUf;
    }

    @ManyToOne
    @JoinColumn(name = "id_ssfamilles", referencedColumnName = "id_ssfamilles", nullable = false)
    public SsFamilles getSsFamillesByIdSsfamilles() {
        return ssFamillesByIdSsfamilles;
    }

    public void setSsFamillesByIdSsfamilles(SsFamilles ssFamillesByIdSsfamilles) {
        this.ssFamillesByIdSsfamilles = ssFamillesByIdSsfamilles;
    }

    @ManyToOne
    @JoinColumn(name = "id_sscategories", referencedColumnName = "id_sscategories", nullable = false)
    public SsCategories getSsCategoriesByIdSscategories() {
        return ssCategoriesByIdSscategories;
    }

    public void setSsCategoriesByIdSscategories(SsCategories ssCategoriesByIdSscategories) {
        this.ssCategoriesByIdSscategories = ssCategoriesByIdSscategories;
    }
}
