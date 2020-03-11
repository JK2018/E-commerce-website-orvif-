package com.orvif.website3.Entity;

import com.google.gson.Gson;
import com.orvif.website3.Entity.helper.ProduitsHelper;
import com.orvif.website3.Repository.*;
import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


import com.orvif.website3.bean.DisplayListProd;
import org.springframework.beans.factory.annotation.Autowired;
//import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


@Entity
@Table(name = "produits", schema = "t_orvif_web_dev")
public class Produits {

    private int idProduits;
    private int cleSystem;
    private int code_article;
    private String codeOrvif;
    private String refFournisseur;
    private String libelle;
    private String libelleUrl;//
    private String descriptif;
    private String avantages;
    private float ppht;
    private float ppttc;//
    private Double ecoPart;
    private Double ecoMobilier;
    private float pphtPublic;//
    private float ppttcPublic;//
    private Marques marquesByIdMarques;//
    private UnitesVente unitesVenteByIdUv;//
    private UnitesFacturation unitesFacturationByIdUf;//
    private Familles famillesByIdFamilles;//
    private SsFamilles ssFamillesByIdSsfamilles;//
    private Categories categoriesByIdCategories;//
    private SsCategories ssCategoriesByIdSscategories;//
    private Gammes gammesByIdGammes;//
    private boolean visible;
    private boolean obligatoire; //Utilise uniquement dans les produits complementaires//
    private Map<Integer, Produits> complementairesObligatoire = new HashMap<>(); // Le integer represente le nombre necessaire du produit complementaire
    private List<Produits> complementaryProduct = new ArrayList<>();
    private List<Produits> similarProduct = new ArrayList<>();
    private List<Document> imageCollection = new ArrayList<>();
    private List<Document> otherDocCollection = new ArrayList<>();
    private List<Caracteristiques> caracteristiqueCollection = new ArrayList<>();
    private List<HistoriqueModification> historiqueModification = new ArrayList<>();
    private Groupe groupe = new Groupe();
    private Map<String, Integer> stocks = new HashMap<>();
    private boolean available = false;   //false by default , then webservice sets to true if stock available
    private boolean defi;
    private boolean destockage;

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
    private Map<String, Integer> stockCapitalized;

    @Autowired
    private ProduitsHelper ph; // = new ProduitsHelper();

    @Transient
    public Map<String, Integer> getStockCapitalized() {
        return stockCapitalized;
    }

    public void setStockCapitalized(Map<String, Integer> stockCapitalized) {
        this.stockCapitalized = stockCapitalized;
    }

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
        if (libelle.substring(0, 1).equals("\"")) {
            libelle = libelle.substring(1);
        }
        libelle = libelle.replace("\"\"", "\"");
        libelle = libelle.replace("\" \"", "\"");
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
        try {
            this.libelleUrl = URLEncoder.encode(libelle, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //Do nothing, exception should never occur.
        }
    }

    @Basic
    @Column(name = "descriptif", nullable = false, length = -1)
    public String getDescriptif() {
        return descriptif.replace(String.valueOf((char) 11), "<br/>");
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
    public float getPpht() {
        return ppht;
    }

    public void setPpht(float ppht) {
        this.setPpttc(getPrixTTCFromHT(ppht));
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
    public boolean getVisible() {
        return visible;
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

    public float getPpttc() {
        return ppttc;
    }

    public void setPpttc(float ppttc) {
        this.ppttc = ppttc;
    }

    public float getPphtPublic() {
        return pphtPublic;
    }

    public void setPphtPublic(float pphtPublic) {
        this.pphtPublic = pphtPublic;
        this.ppttcPublic = pphtPublic * 1.2f;
    }

    public float getPpttcPublic() {
        return ppttcPublic;
    }

    public void setPpttcPublic(float ppttcPublic) {
        this.ppttcPublic = ppttcPublic;
    }

    @Transient
    public List<Document> getImageCollection() {
        return imageCollection;
    }

    public void setImageCollection(List<Document> imageCollection) {
        this.imageCollection = imageCollection;
    }

    @Transient
    public Map<String, Integer> getStocks() {
        return stocks;
    }

    public void setStocks(Map<String, Integer> stocks) {
        this.stocks = stocks;
    }

    private float getPrixTTCFromHT(float prix) {
        return (float) ((int) ((prix + (prix * 0.2)) * 100)) / 100;
    }

    public int getCode_article() {
        return code_article;
    }

    public void setCode_article(int code_article) {
        this.code_article = code_article;
    }

    @Transient
    public List<HistoriqueModification> getHistoriqueModification() {
        return historiqueModification;
    }

    public void setHistoriqueModification(List<HistoriqueModification> historiqueModification) {
        this.historiqueModification = historiqueModification;
    }

    @Transient
    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @Transient
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }



    public String getLibelleUrl() throws UnsupportedEncodingException {
        String l = getLibelle();
        this.libelleUrl = URLEncoder.encode(l, "UTF-8");
        return libelleUrl;
    }

    public void setLibelleUrl(String libelleUrl) {
        this.libelleUrl = libelleUrl;
    }

    public boolean isObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isDefi() {
        return defi;
    }

    public void setDefi(boolean defi) {
        this.defi = defi;
    }

    public boolean isDestockage() {
        return destockage;
    }

    public void setDestockage(boolean destockage) {
        this.destockage = destockage;
    }

    @Transient
    public Map<Integer, Produits> getComplementairesObligatoire() {
        return complementairesObligatoire;
    }

    public void setComplementairesObligatoire(Map<Integer, Produits> complementairesObligatoire) {
        this.complementairesObligatoire = complementairesObligatoire;
    }



    @Basic
    @Column(name = "code_article", nullable = false, insertable = false, updatable = false)
    public int getCodeArticle() {
        return code_article;
    }

    public void setCodeArticle(int code_article) {
        this.code_article = code_article;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Transient
    public List<Document> getOtherDocCollection() {
        return otherDocCollection;
    }

    public void setOtherDocCollection(List<Document> otherDocCollection) {
        this.otherDocCollection = otherDocCollection;
    }

    @Transient
    public List<Caracteristiques> getCaracteristiqueCollection() {
        return caracteristiqueCollection;
    }

    public void setCaracteristiqueCollection(List<Caracteristiques> caracteristiqueCollection) {
        this.caracteristiqueCollection = caracteristiqueCollection;
    }

    @Transient
    public List<Produits> getComplementaryProduct() {
        return complementaryProduct;
    }

    public void setComplementaryProduct(List<Produits> complementaryProduct) {
        this.complementaryProduct = complementaryProduct;
    }
    @Transient
    public List<Produits> getSimilarProduct() {
        return similarProduct;
    }

    public void setSimilarProduct(List<Produits> similarProduct) {
        this.similarProduct = similarProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produits produits = (Produits) o;
        return idProduits == produits.idProduits &&
                cleSystem == produits.cleSystem &&
                code_article == produits.code_article &&
                Float.compare(produits.ppht, ppht) == 0 &&
                Float.compare(produits.ppttc, ppttc) == 0 &&
                Float.compare(produits.pphtPublic, pphtPublic) == 0 &&
                Float.compare(produits.ppttcPublic, ppttcPublic) == 0 &&
                visible == produits.visible &&
                obligatoire == produits.obligatoire &&
                available == produits.available &&
                defi == produits.defi &&
                destockage == produits.destockage &&
                idMarques == produits.idMarques &&
                idUv == produits.idUv &&
                idUf == produits.idUf &&
                idFamilles == produits.idFamilles &&
                idSsfamilles == produits.idSsfamilles &&
                idCategories == produits.idCategories &&
                idSscategories == produits.idSscategories &&
                Objects.equals(codeOrvif, produits.codeOrvif) &&
                Objects.equals(refFournisseur, produits.refFournisseur) &&
                Objects.equals(libelle, produits.libelle) &&
                Objects.equals(libelleUrl, produits.libelleUrl) &&
                Objects.equals(descriptif, produits.descriptif) &&
                Objects.equals(avantages, produits.avantages) &&
                Objects.equals(ecoPart, produits.ecoPart) &&
                Objects.equals(ecoMobilier, produits.ecoMobilier) &&
                Objects.equals(marquesByIdMarques, produits.marquesByIdMarques) &&
                Objects.equals(unitesVenteByIdUv, produits.unitesVenteByIdUv) &&
                Objects.equals(unitesFacturationByIdUf, produits.unitesFacturationByIdUf) &&
                Objects.equals(famillesByIdFamilles, produits.famillesByIdFamilles) &&
                Objects.equals(ssFamillesByIdSsfamilles, produits.ssFamillesByIdSsfamilles) &&
                Objects.equals(categoriesByIdCategories, produits.categoriesByIdCategories) &&
                Objects.equals(ssCategoriesByIdSscategories, produits.ssCategoriesByIdSscategories) &&
                Objects.equals(gammesByIdGammes, produits.gammesByIdGammes) &&
                Objects.equals(complementairesObligatoire, produits.complementairesObligatoire) &&
                Objects.equals(complementaryProduct, produits.complementaryProduct) &&
                Objects.equals(similarProduct, produits.similarProduct) &&
                Objects.equals(imageCollection, produits.imageCollection) &&
                Objects.equals(otherDocCollection, produits.otherDocCollection) &&
                Objects.equals(caracteristiqueCollection, produits.caracteristiqueCollection) &&
                Objects.equals(historiqueModification, produits.historiqueModification) &&
                Objects.equals(groupe, produits.groupe) &&
                Objects.equals(stocks, produits.stocks) &&
                Objects.equals(idGammes, produits.idGammes) &&
                Objects.equals(groupesByIdProduits, produits.groupesByIdProduits) &&
                Objects.equals(lignePaniersByIdProduits, produits.lignePaniersByIdProduits) &&
                Objects.equals(ph, produits.ph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduits, cleSystem, code_article, codeOrvif, refFournisseur, libelle, libelleUrl, descriptif, avantages, ppht, ppttc, ecoPart, ecoMobilier, pphtPublic, ppttcPublic, marquesByIdMarques, unitesVenteByIdUv, unitesFacturationByIdUf, famillesByIdFamilles, ssFamillesByIdSsfamilles, categoriesByIdCategories, ssCategoriesByIdSscategories, gammesByIdGammes, visible, obligatoire, complementairesObligatoire, complementaryProduct, similarProduct, imageCollection, otherDocCollection, caracteristiqueCollection, historiqueModification, groupe, stocks, available, defi, destockage, idMarques, idUv, idUf, idFamilles, idSsfamilles, idCategories, idSscategories, idGammes, groupesByIdProduits, lignePaniersByIdProduits, ph);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Check availability in NX and set not available products to not visible in database.
     *METHOD IN PRODUITSHELPER
     */
    public void setNotVisibleProductsNotInNX(ArrayList<String> listCles) {
        ph.setNotVisibleProductsNotInNX(listCles);
    }

    /**
     * Count the number of results for subcategory if there is any
     * METHOD IN PRODUITSHELPER
     */
    public void countProductInSubCategory(DisplayListProd displayObject){
        ph.countProductInSubCategory(displayObject);
    }



    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a eery products in a list of groupes
     * METHOD IN PRODUITSHELPER
     */
    public void getProductsInfoFromNxGroup(List<Groupe> groupes, String cleClient){
        ph.getProductsInfoFromNxGroup(groupes, cleClient);
    }




    /**
     * Set a product not visible in database
     * METHOD IN PRODUITSHELPER
     */
    public void setProductNotVisible(int cleSystem) {
        ph.setProductNotVisible(cleSystem);
    }




    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a one product only
     * METHOD IN PRODUITSHELPER
     */
    public void getProductInfoFromNx(Produits p, String cleClient){
        ph.getProductInfoFromNx(p, cleClient);
    }





    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a list of products
     *  METHOD IN PRODUITSHELPER
     */
    public void getProductsInfoFromNx(List<Produits> pCollection, String cleClient) {
        ph.getProductsInfoFromNx(pCollection, cleClient);
    }


    /**
     * Check availability of a product List and update displayProd object
     * METHOD IN PRODUITSHELPER
     */
    public void checkKeys(List<String> keysToCheck, String cleClient, DisplayListProd displayObject){
        ph.checkKeys(keysToCheck, cleClient, displayObject);
    }


    /**
     * Check availability of a product List
     *  METHOD IN PRODUITSHELPER
     */
    public void checkKeys(List<String> keysToCheck, String cleClient) {
        ph.checkKeys(keysToCheck, cleClient);
    }



    /**
     * Add to base request supplementary statements to handle filters in the request
     * METHOD IN PRODUITSHELPER
     */
    public String filtersHandling(DisplayListProd displayObject, String base2){
        String base = ph.filtersHandling(displayObject, base2);
        return base;
    }



    public void buildCaracteristiqueArborescence(){
        ph.buildCaracteristiqueArborescence();
    }


    public List<Produits> getProductsInList(List<Produits> products, String cleClient){
        ArrayList<Produits> ret = (ArrayList<Produits>) ph.getProductsInList(products, cleClient);
        return ret;
    }


    // method works for different repos depending on case used. NOT SURE IF WORKS check method in ProdHelper
    public int getIdByName(String name, int level){
        int resultat = ph.getIdByName(name, level);
        return resultat;
    }


    public void checkAllKeys(){
        ph.checkAllKeys();
    }


    public boolean removeDocument(int idProduit, int idDoc){
        boolean ret = ph.removeDocument(idProduit,idDoc);
        return ret;
    }



    public int addDocument(int idProduit, com.orvif.website3.Entity.Document doc){
        int ret = ph.addDocument(idProduit, doc);
        return ret;
    }



    public boolean addSeveralProdComplementaire(int p, List<Integer> idProds){
        boolean ret = ph.addSeveralProdComplementaire(p, idProds);
        return ret;
    }


    public boolean addSeveralProdSimilaire(int p, List<Integer> idProds){
        boolean ret = ph.addSeveralProdComplementaire(p, idProds);
        return ret;
    }



    public List<Produits> getBySearchProduitAssocie(int prodBase, String search, String CompOuAlt, String cleClient){
        List<Produits> ret = ph.getBySearchProduitAssocie(prodBase, search, CompOuAlt, cleClient);
        return ret;
    }


    public boolean removeProdAssocie(int p, int pAssoc){
        boolean ret = ph.removeProdAssocie(p, pAssoc);
        return ret;
    }


    public boolean addProdAssocie(int p, int pAssoc) {
        boolean ret = ph.addProdAssocie(p, pAssoc);
        return ret;
    }


    public boolean removeProdComplementaire(int p, int pComp){
        boolean ret = ph.removeProdComplementaire(p, pComp);
        return ret;
    }



    public boolean addProdComplementaire(int p, int pComp){
        boolean ret = ph.addProdComplementaire(p, pComp);
        return ret;
}

    public List<Groupe> getBySearchAdmin(String text){
        List<Groupe> ret = ph.getBySearchAdmin(text);
        return ret;
    }


    public List<Groupe> getRandomGroups(int number, String cleClient){
        List<Groupe> ret = ph.getRandomGroups(number, cleClient);
        return ret;
    }


    public Groupe getGroupeByCodeArticle(int codeArticle){
        Groupe groupe = ph.getGroupeByCodeArticle(codeArticle);
        return groupe;
    }


    public Produits getByCleSysteme(int cleSystem){
        Produits ret = ph.getByCleSysteme(cleSystem);
        return ret;
    }


    public ArrayList<Produits> getProduitsSimilaire(int id, String cleClient){
        ArrayList<Produits> ret = ph.getProduitsSimilaire(id, cleClient);
        return ret;
    }


    public ArrayList<Produits> getProduitsComplementaire(int id, String cleClient){
        ArrayList<Produits> ret = ph.getProduitsComplementaire(id, cleClient);
        return ret;
    }


    public Produits getById(int id, String cleClient){
        Produits ret = ph.getById(id, cleClient);
        return ret;
    }


    public Groupe getGroupeByProduct(int id, String cleClient){
        Groupe ret = ph.getGroupeByProduct(id, cleClient);
        return ret;
    }



    public void fillDisplayListItemsBySearch(DisplayListProd displayObject, String cleClient, String[] words) {
        ph.fillDisplayListItemsBySearch(displayObject, cleClient, words);
    }



    public void fillDisplayListItems(DisplayListProd displayObject, String cleClient){
        ph.fillDisplayListItems(displayObject, cleClient);
    }


    public void changeVisibility(int cleSystem, boolean visible){
        ph.changeVisibility(cleSystem, visible);
    }


    private Produits getByIdAdmin(int id){
        Produits ret = ph.getByIdAdmin(id);
        return ret;
    }

    public boolean update(Produits p, String modificateur){
        boolean b = ph.update(p, modificateur);
        return b;
    }









    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////












/**

    @Transient
    public Map<String, Integer> getStockCapitalized() {
        Map<String, Integer> stocksCapitalized = new HashMap<>();
        for (Map.Entry<String, Integer> s : stocks.entrySet()) {
            if (s.getKey().equals("LCDR"))
                continue;
            if (s.getKey().equals("ORLY")) {
                stocksCapitalized.put("Drive Orly", s.getValue());
                continue;
            }
            String key = s.getKey().substring(0, 1).toUpperCase() + s.getKey().substring(1).toLowerCase();
            stocksCapitalized.put(key, s.getValue());
        }
        return stocksCapitalized;
    }**/




    public HistoriqueModification compareCaracteristique(List<Caracteristiques> lastListC) {
        int nbModif = 0;
        HistoriqueModification ret = new HistoriqueModification();
        for (int i = 0; i < this.caracteristiqueCollection.size(); i++) {
            boolean found = false;
            boolean changes = false;
            for (int j = 0; j < lastListC.size(); j++) {
                if (caracteristiqueCollection.get(i).getIdCaracteristiques() == lastListC.get(j).getIdCaracteristiques()) {
                    found = true;
                    if (!caracteristiqueCollection.get(i).getValeurProduit().equals(lastListC.get(j).getValeurProduit())) {
                        changes = true;
                        if (nbModif == 0) {
                            ret.setChampModif("Caracteristiques");
                            ret.setValInitial(lastListC.get(j).getLibelle() + " : " + lastListC.get(j).getValeurProduit());
                            ret.setValModif(caracteristiqueCollection.get(i).getLibelle() + " : " + caracteristiqueCollection.get(i).getValeurProduit());
                            nbModif++;
                        } else {
                            ret.setValInitial(ret.getValInitial() + ", " + lastListC.get(j).getLibelle() + " : " + lastListC.get(j).getValeurProduit());
                            ret.setValModif(ret.getValModif() + ", " + caracteristiqueCollection.get(i).getLibelle() + " : " + caracteristiqueCollection.get(i).getValeurProduit());
                        }
                    }
                    break;
                }
            }
            if (!found) {
                //Ajout d'une nouvelle caracteristique
                if (nbModif == 0) {
                    ret.setChampModif("Caracteristiques");
                    ret.setValInitial(caracteristiqueCollection.get(i).getLibelle() + " : aucune");
                    ret.setValModif(caracteristiqueCollection.get(i).getLibelle() + " : " + caracteristiqueCollection.get(i).getValeurProduit());
                    nbModif++;
                } else {
                    ret.setValInitial(ret.getValInitial() + ", " + caracteristiqueCollection.get(i).getLibelle() + " : aucune");
                    ret.setValModif(ret.getValModif() + ", " + caracteristiqueCollection.get(i).getLibelle() + " : " + caracteristiqueCollection.get(i).getValeurProduit());
                }
            }
        }
        //Execution d'un for dans l'autre sens pour verifier si une caracteristique a ete supprimee
        for (int i = 0; i < lastListC.size(); i++) {
            boolean found = false;
            for (int j = 0; j < this.caracteristiqueCollection.size(); j++) {
                if (caracteristiqueCollection.get(j).getIdCaracteristiques() == lastListC.get(i).getIdCaracteristiques()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                //Ajout d'une nouvelle caracteristique
                if (nbModif == 0) {
                    ret.setChampModif("Caracteristiques");
                    ret.setValInitial(lastListC.get(i).getLibelle() + " : " + lastListC.get(i).getValeurProduit());
                    ret.setValModif(lastListC.get(i).getLibelle() + " : aucune");
                    nbModif++;
                } else {
                    ret.setValInitial(ret.getValInitial() + ", " + lastListC.get(i).getLibelle() + " : " + lastListC.get(i).getValeurProduit());
                    ret.setValModif(ret.getValModif() + ", " + lastListC.get(i).getLibelle() + " : aucune");
                }
            }
        }
        return ret;
    }

    public boolean checkAvailable() {
        //Verification des stocks pour les produits complementaires
        for (Map.Entry<Integer, Produits> prodComp : complementairesObligatoire.entrySet()) {
            int tmp = 0;
            for (Map.Entry<String, Integer> stock : prodComp.getValue().getStocks().entrySet()) {
                tmp += stock.getValue();
            }
            if (tmp < prodComp.getKey()) return false;
        }
        //Check des stocks du produit lui meme
        for (Map.Entry<String, Integer> stock : stocks.entrySet()) {
            if (stock.getValue() > 0) {
                return true;
            }
        }
        return false;
    }



    @Transient
    public List<HistoriqueModification> genererHistorique(Produits lastP,GammesRepository gammeDao, CategoriesRepository categorieDao, SsCategoriesRepository sousCategorieDao ,FamillesRepository familleDao, SsFamillesRepository sousFamilleDao, MarquesRepository marqueDao,UnitesFacturationRepository ufDao, UnitesVenteRepository uvDao ) {
        List<HistoriqueModification> ret = new ArrayList<HistoriqueModification>();
        int cleSystem = 0;
        if (lastP.getCleSystem() != this.cleSystem) {
            cleSystem = this.getCleSystem();
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Cle systeme");
            h.setValInitial(String.valueOf(lastP.getCleSystem()));
            h.setValModif(String.valueOf(this.getCleSystem()));
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        } else {
            cleSystem = lastP.getCleSystem();
        }
        if (!lastP.getCodeOrvif().equals(this.getCodeOrvif())) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Code orvif");
            h.setValInitial(lastP.getCodeOrvif());
            h.setValModif(this.getCodeOrvif());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (!lastP.getRefFournisseur().equals(this.getRefFournisseur())) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Reference fournisseur");
            h.setValInitial(lastP.getRefFournisseur());
            h.setValModif(this.getRefFournisseur());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (!lastP.getLibelle().equals(this.getLibelle())) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Libelle");
            h.setValInitial(lastP.getLibelle());
            h.setValModif(this.getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (!lastP.getDescriptif().equals(this.getDescriptif())) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Description");
            h.setValInitial(lastP.getDescriptif());
            h.setValModif(this.getDescriptif());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (!lastP.getAvantages().equals(this.getAvantages())) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Avantages");
            h.setValInitial(lastP.getAvantages());
            h.setValModif(this.getAvantages());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getMarquesByIdMarques().getIdMarques() != this.getMarquesByIdMarques().getIdMarques()) {
            Optional<Marques> lastMarque = marqueDao.findById(lastP.getMarquesByIdMarques().getIdMarques());
            Optional<Marques> newMarque = marqueDao.findById(this.getMarquesByIdMarques().getIdMarques());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Marque");
            h.setValInitial(lastMarque.get().getNom());
            h.setValModif(newMarque.get().getNom());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getUnitesFacturationByIdUf().getIdUf() != this.getUnitesFacturationByIdUf().getIdUf()) {
            Optional<UnitesFacturation> lastUf = ufDao.findById(lastP.getUnitesFacturationByIdUf().getIdUf());
            Optional<UnitesFacturation> newUf = ufDao.findById(this.getUnitesFacturationByIdUf().getIdUf());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Unite de facturation");
            h.setValInitial(lastUf.get().getLibelle());
            h.setValModif(newUf.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getUnitesVenteByIdUv().getIdUv() != this.getUnitesVenteByIdUv().getIdUv()) {
            Optional<UnitesVente> lastUv = uvDao.findById(lastP.getUnitesVenteByIdUv().getIdUv());
            Optional<UnitesVente> newUv = uvDao.findById(this.getUnitesVenteByIdUv().getIdUv());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Unite de vente");
            h.setValInitial(lastUv.get().getLibelle());
            h.setValModif(newUv.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getFamillesByIdFamilles().getIdFamilles() != this.getFamillesByIdFamilles().getIdFamilles()) {
            Optional<Familles> lastF = familleDao.findById(lastP.getFamillesByIdFamilles().getIdFamilles());
            Optional<Familles> newF = familleDao.findById(this.getFamillesByIdFamilles().getIdFamilles());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Famille");
            h.setValInitial(lastF.get().getLibelle());
            h.setValModif(lastF.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getSsFamillesByIdSsfamilles().getIdSsfamilles() != this.getSsFamillesByIdSsfamilles().getIdSsfamilles()) {
            Optional<SsFamilles> lastSF = sousFamilleDao.findById(lastP.getSsFamillesByIdSsfamilles().getIdSsfamilles());
            Optional<SsFamilles> newSF = sousFamilleDao.findById(this.getSsFamillesByIdSsfamilles().getIdSsfamilles());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Sous famille");
            h.setValInitial(lastSF.get().getLibelle());
            h.setValModif(newSF.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getCategoriesByIdCategories().getIdCategories() != this.getCategoriesByIdCategories().getIdCategories()) {
            Optional<Categories> lastC = categorieDao.findById(lastP.getCategoriesByIdCategories().getIdCategories());
            Optional<Categories> newC = categorieDao.findById(this.getCategoriesByIdCategories().getIdCategories());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Categorie");
            h.setValModif(lastC.get().getLibelle());
            h.setValModif(newC.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getSsCategoriesByIdSscategories().getIdSscategories() != this.getSsCategoriesByIdSscategories().getIdSscategories()) {
            Optional<SsCategories> lastSC = sousCategorieDao.findById(lastP.getSsCategoriesByIdSscategories().getIdSscategories());
            Optional<SsCategories> newSC = sousCategorieDao.findById(this.getSsCategoriesByIdSscategories().getIdSscategories());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Sous categorie");
            h.setValInitial(lastSC.get().getLibelle());
            h.setValModif(newSC.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.getGammesByIdGammes().getIdGammes() != this.getGammesByIdGammes().getIdGammes()) {
            Optional<Gammes> lastG = gammeDao.findById(lastP.getGammesByIdGammes().getIdGammes());
            Optional<Gammes> newG = gammeDao.findById(this.getGammesByIdGammes().getIdGammes());
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Gamme");
            h.setValInitial(lastG.get().getLibelle());
            h.setValModif(newG.get().getLibelle());
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        if (lastP.isVisible() != this.isVisible()) {
            HistoriqueModification h = new HistoriqueModification();
            h.setChampModif("Visible");
            if (lastP.isVisible()) {
                h.setValInitial("Oui");
                h.setValModif("Non");
            } else {
                h.setValInitial("Non");
                h.setValModif("Oui");
            }
            h.setIdProduit(lastP.getIdProduits());
            ret.add(h);
        }
        return ret;
    }





    /**
     * Method to know if the product is available for drive 1h (available if ORLY stock > 0
     *
     * @return
     */
    public boolean isAvailableForDrive(int n) {
        if (stocks.get("ORLY") < n) return false;
        if (complementairesObligatoire != null) {
            for (Map.Entry<Integer, Produits> entry : complementairesObligatoire.entrySet()) {
                if (entry.getValue().getStocks().get("ORLY") < n * entry.getKey()) return false;
            }
        }
        return true;
    }



}
