package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.GroupePK;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groupe", schema = "t_orvif_web_dev")
@IdClass(GroupePK.class)
public class Groupe {
    private int id =-1;
    private int idProd;
    private Produits produitsByIdProd;
    private List<Produits> products = new ArrayList<>();//
    private List<String> clesSystemes = new ArrayList<>();//
    private double minPriceHT = 0;//
    private double maxPriceHT = 0;//
    private double minPriceTTC = 0;//
    private double maxPriceTTC = 0;//
    private Familles famille;
    private SsFamilles sousFamille;
    private Categories categorie;
    private SsCategories sousCategorie;
    private Marques marque;
    private Gammes gamme;
    private String libelle;
    private String description;
    private String avantages;
    private List<Document> imageList;
    private List<Document> documentList;
    private List<Caracteristiques> criteres = new ArrayList<>();
    private int numberRef;
    private int codeArticle;



    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "idProd", nullable = false)
    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Groupe groupe = (Groupe) o;

        if (id != groupe.id) return false;
        if (idProd != groupe.idProd) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProd;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idProd", referencedColumnName = "id_produits", nullable = false, insertable = false, updatable = false)
    public Produits getProduitsByIdProd() {
        return produitsByIdProd;
    }

    public void setProduitsByIdProd(Produits produitsByIdProd) {
        this.produitsByIdProd = produitsByIdProd;
    }

    @Transient
    public List<Produits> getProducts() {
        return products;
    }

    public void setProducts(List<Produits> products) {
        this.products = products;
    }

    @Transient
    public List<String> getClesSystemes() {
        return clesSystemes;
    }

    public void setClesSystemes(List<String> clesSystemes) {
        this.clesSystemes = clesSystemes;
    }

    public double getMinPriceHT() {
        return minPriceHT;
    }

    public void setMinPriceHT(double minPriceHT) {
        this.minPriceHT = minPriceHT;
    }

    public double getMaxPriceHT() {
        return maxPriceHT;
    }

    public void setMaxPriceHT(double maxPriceHT) {
        this.maxPriceHT = maxPriceHT;
    }

    public double getMinPriceTTC() {
        return minPriceTTC;
    }

    public void setMinPriceTTC(double minPriceTTC) {
        this.minPriceTTC = minPriceTTC;
    }

    public double getMaxPriceTTC() {
        return maxPriceTTC;
    }

    public void setMaxPriceTTC(double maxPriceTTC) {
        this.maxPriceTTC = maxPriceTTC;
    }

    @Transient
    public Familles getFamille() {
        return famille;
    }

    public void setFamille(Familles famille) {
        this.famille = famille;
    }

    @Transient
    public SsFamilles getSousFamille() {
        return sousFamille;
    }

    public void setSousFamille(SsFamilles sousFamille) {
        this.sousFamille = sousFamille;
    }

    @Transient
    public Categories getCategorie() {
        return categorie;
    }

    public void setCategorie(Categories categorie) {
        this.categorie = categorie;
    }

    @Transient
    public SsCategories getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(SsCategories sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    @Transient
    public Marques getMarque() {
        return marque;
    }

    public void setMarque(Marques marque) {
        this.marque = marque;
    }

    @Transient
    public Gammes getGamme() {
        return gamme;
    }

    public void setGamme(Gammes gamme) {
        this.gamme = gamme;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvantages() {
        return avantages;
    }

    public void setAvantages(String avantages) {
        this.avantages = avantages;
    }

    @Transient
    public List<Document> getImageList() {
        return imageList;
    }

    public void setImageList(List<Document> imageList) {
        this.imageList = imageList;
    }

    @Transient
    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    @Transient
    public List<Caracteristiques> getCriteres() {
        return criteres;
    }

    public void setCriteres(List<Caracteristiques> criteres) {
        this.criteres = criteres;
    }

    public int getNumberRef() {
        return numberRef;
    }

    public void setNumberRef(int numberRef) {
        this.numberRef = numberRef;
    }

    public int getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(int codeArticle) {
        this.codeArticle = codeArticle;
    }
}
