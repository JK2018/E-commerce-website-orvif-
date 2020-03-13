package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.*;
import com.orvif.website3.Repository.*;
import com.orvif.website3.bean.DisplayListProd;
import com.orvif.website3.service.MCrypt;
import com.orvif.website3.service.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;
import static java.lang.Math.round;

@Component
public class ProduitsHelper {

    @Autowired
    private UnitesVenteRepository uvr;

    @Autowired
    private UnitesFacturationRepository ufr;

    @Autowired
    private GammesRepository gr;

    @Autowired
    private HistoriqueModificationRepository hr;

    @Autowired
    private DocumentHelper dh;

    @Autowired
    private CaracteristiquesHelper ch;

    @Autowired
    private FamillesHelper fh;

    @Autowired
    private MarquesRepository mr;

    @Autowired
    private ProduitsComplementairesRepository pcr;

    @Autowired
    private ProduitsAlternatifsRepository par;

    @Autowired
    private DAOFactory df;

    @Autowired
    private ProduitsRepository pr;

    @Autowired
    private FamillesRepository fr;

    @Autowired
    private CaracteristiquesRepository cr;

    @Autowired
    private SsFamillesRepository sfr;

    @Autowired
    private CategoriesRepository catr;

    @Autowired
    private SsCategoriesRepository scatr;

    @Autowired
    private CaracteristiquesProduitsRepository cpr;

    @Autowired
    private CaracteristiquesCategoriesRepository ccr;

    @Autowired
    private DocumentProduitRepository dpr;

    @Autowired
    private DocumentRepository dr;

    @Autowired
    private MarquesHelper mh;

    public ProduitsHelper() {
    }






    public boolean add(Produits p) {
        boolean ret = true;
        try {
            int i = 0;
            i = pr.add(p.getCleSystem(), p.getIdFamilles(), p.getIdSsfamilles(), p.getIdCategories(), p.getIdSscategories());
            pr.flush();
            if (i!=0) {
                //Le produit existe, on le met à jour seulement. Pas d'ajout
//				preparedStmt = connexion.prepareStatement("UPDATE PRODUITS SET cle_system = ?, code_orvif = ?, ref_fournisseur = ?, libelle = ?, descriptif = ?, avantages = ?, id_marques = ?, id_uv = ?, id_uf = ?, id_familles = ?, id_ssfamilles = ?, id_categories = ?, id_sscategories = ?, id_gammes = ?, visible = ?, codeArticle = ? WHERE id_produits = ?");
//				preparedStmt.setInt(1, p.getCleSystem());
//				preparedStmt.setString(2, p.getCodeOrvif());
//				preparedStmt.setString(3, p.getRefFournisseur());
//				preparedStmt.setString(4, p.getLibelle());
//				preparedStmt.setString(5, p.getDescriptif());
//				preparedStmt.setString(6, p.getAvantages());
//				preparedStmt.setInt(7, p.getMarque().getId());
//				preparedStmt.setInt(8, p.getUniteVente().getId());
//				preparedStmt.setInt(9, p.getUniteFacturation().getId());
//				preparedStmt.setInt(10, p.getFamille().getId());
//				preparedStmt.setInt(11, p.getSousFamille().getId());
//				preparedStmt.setInt(12, p.getCategorie().getId());
//				preparedStmt.setInt(13, p.getSousCategorie().getId());
//				if (p.getGamme() == null) {
//					preparedStmt.setString(14, null);
//				} else {
//					preparedStmt.setInt(14, p.getGamme().getId());
//				}
//				if (p.isVisible()) {
//					preparedStmt.setInt(15, 1);
//				} else {
//					preparedStmt.setInt(15, 0);
//				}
//				preparedStmt.setString(16, p.getCodeArticle());
//				preparedStmt.setInt(17, resultat.getInt("id_produits"));
//				preparedStmt.executeUpdate();
//				return true;
                return true;
            } else {
                //Produit non existant, on le crée
                p.setPpht(0);
                p.setEcoPart((double)0);
                p.setEcoMobilier((double)0);
                p = pr.save(p);
                pr.flush();
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " error occured when trying to add product.");
        }
        return ret;
    }






    /**
     * Check availability in NX and set not available products to not visible in database.
     * called in Produits
     */
    public void setNotVisibleProductsNotInNX(ArrayList<String> listCles) {
        if (listCles != null && !listCles.isEmpty()) {
            StringBuilder param = new StringBuilder();
            for (int i = 0; i < listCles.size(); i++) {
                if (i > 0) {
                    param.append(",");
                }
                param.append(listCles.get(i));
            }
            try {
                // Appel au webService
                String webService = "wn_product_exist " + param;
                Document result = df.execWebService(webService);
                final Element racine = result.getDocumentElement();
                if (racine.getNodeName().equals("RETOUR")) {
                    NodeList listToHide = racine.getElementsByTagName("CLE");
                    if (listToHide != null && listToHide.getLength() > 0) {
                        for (int j = 0; j < listToHide.getLength(); j++) {
                            setProductNotVisible(Integer.parseInt(listToHide.item(j).getTextContent()));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Une erreur s'est produite :" + e.getMessage());
            }

        } else {
            System.out.println("aucune cle ????");
        }
    }




    /**
     * Set a product not visible in database
     * called in Produits
     */
    public void setProductNotVisible(int cleSystem) {
        try {
            pr.setProductNotVisible(cleSystem);
        } catch (Exception e) {
            System.out.println("Probleme de requete pour rendre un produit invisible.");
        }
    }









    /**
     * Count the number of results for subcategory if there is any
     *  called in Produits
     */
    public void countProductInSubCategory(DisplayListProd displayObject) {
        if (displayObject.getSousCategorie() == null) {
            if (displayObject.getCategorie() != null && displayObject.getCategorie().getSsCategoriesByIdCategories() != null) {
                try {
                    /***Requetes pour toutes les sous categories**/
                    for (SsCategories sousCategorie : displayObject.getCategorie().getSsCategoriesByIdCategories()) {
                        int resultat = pr.countProdInSubCategory(sousCategorie.getIdSscategories());
                        sousCategorie.setNbProducts(resultat);
                    }
                } catch (Exception e) {
                    throw new DAOException(e.getClass().getName() + " error occured when trying to get product number of sub categories : ");
                }
            } else if (displayObject.getSousFamille() != null && displayObject.getSousFamille().getCategoriesByIdSsfamilles() != null) {
                /***Requetes pour toutes les categories**/
                try {
                    /***Requetes pour toutes les sous categories**/
                  //  preparedStmt = connexion.prepareStatement("SELECT COUNT(DISTINCT codeArticle) FROM PRODUITS WHERE id_categories = ? AND visible = 1");
                    for (Categories categorie : displayObject.getSousFamille().getCategoriesByIdSsfamilles()) {
                        int resultat = pr.countProdInSubCategory2(categorie.getIdCategories());
                        categorie.setNbProducts(resultat);
                    }
                } catch (Exception e) {
                    throw new DAOException(e.getClass().getName() + " error occured when trying to get product number of categories : ");
                }
            }
        }
    }



    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a eery products in a list of groupes
     * called in Produits
     * needed for method randGrp()
     */
    // METHOD WORKS prices fetched will be HT if cleClient != 1 , or TTC if cleClient = 1 (particulier)
    public void getProductsInfoFromNxGroup(List<Groupe> groupes, String cleClient) {
        // Construction du parametre pour le webservice appel_multiple
        StringBuilder params = new StringBuilder("wn_tarif_site-");
        int i = 0;
        for (Groupe groupe : groupes) {
            for (Produits p : groupe.getProducts()) {
                if (i == 0) {
                    params.append(cleClient).append("_").append(String.valueOf(p.getCleSystem()));
                    i++;
                } else {
                    params.append(",").append(cleClient).append("_").append(String.valueOf(p.getCleSystem()));
                }
            }
        }// builds params string
        try {
            List<Produits> pToRemove = new ArrayList<>();
            String webService = "appel_multiple_xml " + params;
    //System.out.println(webService + ": webservice test");
            MCrypt mcrypt = new MCrypt();
            String encrypted = MCrypt.bytesToHex(mcrypt.encrypt(webService));
    //System.out.println(encrypted + ": webservice encrypted test");
            Logger.getLogger(ProduitsHelper.class.getName()).info(encrypted);//slaps the encrypted in an info msg?????
            Document result = df.execWebService(webService);
            Element racine = (Element) result.getDocumentElement();
    //System.out.println(racine.getNodeName() + ": node name test");
            // En theorie, la racine ne peux pas etre differente de "RETOURS"
            if (racine.getNodeName().equals("RETOURS")) {
                NodeList noeuds = racine.getChildNodes();
                for (Groupe groupe : groupes) { // for each grp of products
                    for (Produits p : groupe.getProducts()) {
                        for (int j = 0; j < noeuds.getLength(); j++) {
                            Element unRetour = (Element) noeuds.item(j);
                            String cleSystem = unRetour.getElementsByTagName("CLESYSTEM").item(0).getTextContent();
    //System.out.println(cleSystem + ": cleSystem for each prod");
                            // etienne : Recuperation des produits complementaires
                            if (cleSystem.equals(String.valueOf(p.getCleSystem()))) {
                                try {//si il y a des produits complementaires :
                                    Node complementary = unRetour.getElementsByTagName("COMPLEMENTAIRES").item(0);
    //System.out.println(complementary + ": node complementary for each prod");
                                    if (complementary.getChildNodes().getLength() > 0) {
                                        for (int k = 0; k < complementary.getChildNodes().getLength(); k++) {
                                            Element compProd = (Element) complementary.getChildNodes().item(k);
                                            String cleSysytem = compProd.getElementsByTagName("CLE").item(0)
                                                    .getTextContent();
    //System.out.println(cleSysytem + ": clesysyteme");
                                            Produits pComp = this.pr.findByCleSystem(Integer.parseInt(cleSysytem));
    //System.out.println(pComp.getLibelle() + ": prod comp");
                                            if (pComp != null) {
                                                pComp.setPpht(Float.parseFloat(compProd.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                                                pComp.setPpttc(getPrixTTCFromHT(Float.parseFloat(compProd.getElementsByTagName("PRIXNET").item(0).getTextContent())));///////////////
                                                pComp.setEcoMobilier((double) Float.parseFloat(compProd.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                                                pComp.setEcoPart((double) Float.parseFloat(compProd.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                                                int nb = Integer.parseInt(compProd.getElementsByTagName("NB").item(0).getTextContent());
                                                pComp.setDefi(compProd.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                                                /////GETTING STOCKS/////
                                                Node stocks = compProd.getElementsByTagName("STOCK").item(0);
                                                if (stocks.getChildNodes().getLength() != 7) {
                                                    pToRemove.add(p);
                                                } else {
                                                    for (int l = 0; l < stocks.getChildNodes().getLength(); l++) {
                                                        Element oneStock = (Element) stocks.getChildNodes().item(l);
                                                        switch (oneStock.getNodeName()) {
                                                            //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                                            case "NORD":
                                                                pComp.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "SUD":
                                                                pComp.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "BASTILLE":
                                                                pComp.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "EST":
                                                                pComp.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "MONTMARTRE":
                                                                pComp.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "LCDR":
                                                                pComp.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                            case "ORLY":
                                                                pComp.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                                                break;
                                                        }
                                                    }
                                                }
                                                ////END GET STOCKS////
                                                p.getComplementairesObligatoire().put(nb, pComp);
                                            } else {
                                                pToRemove.add(p);
                                                throw new Exception("Produit complémentaire non disponible.");
                                            }
                                        }
                                    }
                                    // PAS DE PRODUITS COMPLEMENTAIRES

    ///////////////to see all elem tagnames///////////////
   /** NodeList nodeList=unRetour.getElementsByTagName("*");
    for (int x=0; x<nodeList.getLength(); x++)
    {
        // Get element
        Element element = (Element)nodeList.item(x);
        System.out.println(element.getNodeName());
    }**////////////////////////////////////////////////////

                                    p.setPpht(Float.parseFloat(unRetour.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                                    p.setPpttc(getPrixTTCFromHT(Float.parseFloat(unRetour.getElementsByTagName("PRIXNET").item(0).getTextContent())));///////////////
                                    p.setEcoPart((double) Float.parseFloat(unRetour.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                                    p.setEcoMobilier((double) Float.parseFloat(unRetour.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                                    p.setDefi(unRetour.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                                    /////GETTING STOCKS/////
                                    Node stocks = unRetour.getElementsByTagName("STOCK").item(0);
                                    if (stocks.getChildNodes().getLength() != 7) {
                                        pToRemove.add(p);
                                    } else {
                                        for (int k = 0; k < stocks.getChildNodes().getLength(); k++) {
                                            Element oneStock = (Element) stocks.getChildNodes().item(k);
                                            switch (oneStock.getNodeName()) {
                                                //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                                case "NORD":
                                                    p.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "SUD":
                                                    p.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "BASTILLE":
                                                    p.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "EST":
                                                    p.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "MONTMARTRE":
                                                    p.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "LCDR":
                                                    p.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                                case "ORLY":
                                                    p.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                                    break;
                                            }
                                        }
                                    }
                                    p.setAvailable(p.checkAvailable());
                                    ////END GET STOCKS////
                                    racine.removeChild(unRetour);
                                    break;
                                } catch (Exception e) {
                                    pToRemove.add(p);
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        }
                    }
                }
                //TODO ICI
                for (Produits p : pToRemove) { // deletes pToRemove from groupes
                    System.out.println(p.getLibelle() + "prods to remove");
                    boolean found = false;
                    boolean deleteGroup = false;
                    for (Groupe groupe : groupes) {
                        for (Produits p2 : groupe.getProducts()) {
                            if (p.getIdProduits() == p2.getIdProduits()) {
                                groupe.getProducts().remove(p2);
                                found = true;
                                if (groupe.getProducts().size() == 0) {
                                    deleteGroup = true;
                                }
                                break;
                            }
                        }
                        if (found) {
                            if (deleteGroup) {
                                groupes.remove(groupe);
                            }
                            break;
                        }
                    }
                }
                for (Groupe g : groupes) {// for each groupe of identical prods   /   NO PARTICULIER TTC PRICE!
                    double minGroup = 9999999;
                    double maxGroup = 0;
                    double minGroupTtc = 9999999;
                    double maxGroupTtc = 0;
                    for (Produits p : g.getProducts()) {
                        if (p.getPpht() < minGroup && p.getPpht() > 0) {
                            minGroup = p.getPpht();
                        }
                        if (p.getPpht() > maxGroup) {
                            maxGroup = p.getPpht();
                        }


                        if (p.getPpttc() < minGroupTtc && p.getPpttc() > 0) {
                            minGroupTtc = p.getPpttc();
                        }
                        if (p.getPpttc() > maxGroupTtc) {
                            maxGroupTtc = p.getPpttc();
                        }
                    }
                    g.setMaxPriceHT(maxGroup);
                    g.setMinPriceHT(minGroup);
                    g.setMaxPriceTTC(maxGroupTtc);
                    g.setMinPriceTTC(minGroupTtc);
                }
            } else {
                // TODO error
            }
        } catch (Exception e) {
            Logger.getLogger(ProduitsRepository.class.getName()).severe("An error occured while getting informations form NX. (" + e.getMessage() + ")");
        }
    }






    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a one product only
     * called in Produits
     */
    public void getProductInfoFromNx(Produits p, String cleClient) {
        // Recuperation prix HT avec le cle du client + ecos taxes + stocks
        String webService = "wn_tarif_site " + cleClient + "_" + String.valueOf(p.getCleSystem());
        Document result = df.execWebService(webService);
        final Element racine = result.getDocumentElement();
        if (racine.getNodeName().equals("RETOUR")) {
            String cleSystem = racine.getElementsByTagName("CLESYSTEM").item(0).getTextContent();
            if (cleSystem.equals(String.valueOf(p.getCleSystem()))) {
                try {
                    Node complementary = racine.getElementsByTagName("COMPLEMENTAIRES").item(0);
                    if (complementary.getChildNodes().getLength() > 0) {
                        for (int k = 0; k < complementary.getChildNodes().getLength(); k++) {
                            Element compProd = (Element) complementary.getChildNodes().item(k);
                            String cleSysytem = compProd.getElementsByTagName("CLE").item(0)
                                    .getTextContent();
                            Produits pComp = this.getByCleSysteme(Integer.parseInt(cleSysytem));
                            if (pComp != null) {
                                pComp.setPpht(Float.parseFloat(compProd.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                                pComp.setPphtPublic(Float.parseFloat(compProd.getElementsByTagName("PRIXNETPUBLIC").item(0).getTextContent()));
                                pComp.setEcoMobilier((double) Float.parseFloat(compProd.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                                pComp.setEcoPart((double) Float.parseFloat(compProd.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                                int nb = Integer.parseInt(compProd.getElementsByTagName("NB").item(0).getTextContent());
                                pComp.setDefi(compProd.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                                /////GETTING STOCKS/////
                                Node stocks = compProd.getElementsByTagName("STOCK").item(0);
                                if (stocks.getChildNodes().getLength() != 7) {
                                    //throw new NotAvailableProduct();
                                } else {
                                    for (int l = 0; l < stocks.getChildNodes().getLength(); l++) {
                                        Element oneStock = (Element) stocks.getChildNodes().item(l);
                                        switch (oneStock.getNodeName()) {
                                            //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                            case "NORD":
                                                pComp.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "SUD":
                                                pComp.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "BASTILLE":
                                                pComp.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "EST":
                                                pComp.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "MONTMARTRE":
                                                pComp.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "LCDR":
                                                pComp.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "ORLY":
                                                pComp.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                        }
                                    }
                                }
                                ////END GET STOCKS////
                                p.getComplementairesObligatoire().put(nb, pComp);
                            } else {
                                //throw new NotAvailableProduct("Produit compl&eacute;mentaire non disponible.");
                            }
                        }
                    }
                    p.setPphtPublic(Float.parseFloat(racine.getElementsByTagName("PRIXNETPUBLIC").item(0).getTextContent()));
                    p.setPpht(Float
                            .parseFloat(racine.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                    p.setEcoPart((double) Float
                            .parseFloat(racine.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                    p.setEcoMobilier((double) Float
                            .parseFloat(racine.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                    p.setDefi(racine.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                    /////GETTING STOCKS/////
                    Node stocks = racine.getElementsByTagName("STOCK").item(0);
                    if (stocks.getChildNodes().getLength() != 7) {
                        //throw new NotAvailableProduct("No stock");
                    } else {
                        for (int k = 0; k < stocks.getChildNodes().getLength(); k++) {
                            Element oneStock = (Element) stocks.getChildNodes().item(k);
                            switch (oneStock.getNodeName()) {
                                //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                case "NORD":
                                    p.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "SUD":
                                    p.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "BASTILLE":
                                    p.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "EST":
                                    p.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "MONTMARTRE":
                                    p.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "LCDR":
                                    p.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                                case "ORLY":
                                    p.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                    break;
                            }
                        }
                    }
                    p.setAvailable(p.checkAvailable());
                    ////END GET STOCKS////
                } catch (Exception e) {
                    //throw new NotAvailableProduct();
                }
            }
        } else {
            //throw new NotAvailableProduct();
        }
    }




    /**
     * Fetch information (Stocks, prices, taxes...) from NX for a list of products
     * called in Produits
     */
    public void getProductsInfoFromNx(List<Produits> pCollection, String cleClient) {
        // Construction du parametre pour le webservice appel_multiple
        StringBuilder params = new StringBuilder("wn_tarif_site-");
        int i = 0;
        for (Produits p : pCollection) {
            if (i == 0) {
                params.append(cleClient).append("_").append(String.valueOf(p.getCleSystem()));
                i++;
            } else {
                params.append(",").append(cleClient).append("_").append(String.valueOf(p.getCleSystem()));
            }
        }
        try {
            List<Produits> pToRemove = new ArrayList<>();
            String webService = "appel_multiple_xml " + params;

            MCrypt mcrypt = new MCrypt();
            String encrypted = MCrypt.bytesToHex(mcrypt.encrypt(webService));
            Logger.getLogger(ProduitsRepository.class.getName()).info(encrypted);
            Document result = this.df.execWebService(webService);
            Element racine = result.getDocumentElement();
//			// En theorie, la racine ne peux pas etre differente de "RETOURS"
            if (racine.getNodeName().equals("RETOURS")) {
                NodeList noeuds = racine.getChildNodes();
                for (Produits p : pCollection) {
                    for (int j = 0; j < noeuds.getLength(); j++) {
                        Element unRetour = (Element) noeuds.item(j);
                        String cleSystem = unRetour.getElementsByTagName("CLESYSTEM").item(0).getTextContent();
                        // Recuperation des produits complementaires

                        if (cleSystem.equals(String.valueOf(p.getCleSystem()))) {
                            try {
                                Node complementary = unRetour.getElementsByTagName("COMPLEMENTAIRES").item(0);
                                if (complementary.getChildNodes().getLength() > 0) {
                                    for (int k = 0; k < complementary.getChildNodes().getLength(); k++) {
                                        Element compProd = (Element) complementary.getChildNodes().item(k);
                                        String cleSysytem = compProd.getElementsByTagName("CLE").item(0)
                                                .getTextContent();
                                        Produits pComp = this.getByCleSysteme(Integer.parseInt(cleSysytem));
                                        if (pComp != null) {
                                            pComp.setPpht(Float.parseFloat(compProd.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                                            pComp.setPphtPublic(Float.parseFloat(compProd.getElementsByTagName("PRIXNETPUBLIC").item(0).getTextContent()));
                                            pComp.setEcoMobilier((double) Float.parseFloat(compProd.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                                            pComp.setEcoPart((double) Float.parseFloat(compProd.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                                            int nb = Integer.parseInt(compProd.getElementsByTagName("NB").item(0).getTextContent());
                                            pComp.setDefi(compProd.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                                            /////GETTING STOCKS/////
                                            Node stocks = compProd.getElementsByTagName("STOCK").item(0);
                                            if (stocks.getChildNodes().getLength() != 7) {
                                                pToRemove.add(p);
                                            } else {
                                                for (int l = 0; l < stocks.getChildNodes().getLength(); l++) {
                                                    Element oneStock = (Element) stocks.getChildNodes().item(l);
                                                    switch (oneStock.getNodeName()) {
                                                        //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                                        case "NORD":
                                                            pComp.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "SUD":
                                                            pComp.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "BASTILLE":
                                                            pComp.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "EST":
                                                            pComp.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "MONTMARTRE":
                                                            pComp.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "LCDR":
                                                            pComp.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                        case "ORLY":
                                                            pComp.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                                            break;
                                                    }
                                                }
                                            }
                                            ////END GET STOCKS////
                                            p.getComplementairesObligatoire().put(nb, pComp);
                                        } else {
                                            pToRemove.add(p);
                                            throw new Exception("Produit complémentaire non disponible.");
                                        }
                                    }
                                }
                                p.setPphtPublic(Float.parseFloat(unRetour.getElementsByTagName("PRIXNETPUBLIC").item(0).getTextContent()));
                                p.setPpht(Float
                                        .parseFloat(unRetour.getElementsByTagName("PRIXNET").item(0).getTextContent()));
                                p.setEcoPart((double) Float
                                        .parseFloat(unRetour.getElementsByTagName("ECODEEE").item(0).getTextContent()));
                                p.setEcoMobilier((double) Float
                                        .parseFloat(unRetour.getElementsByTagName("ECOMOBI").item(0).getTextContent()));
                                p.setDefi(unRetour.getElementsByTagName("DEFI").item(0).getTextContent().equals("OUI"));
                                /////GETTING STOCKS/////
                                Node stocks = unRetour.getElementsByTagName("STOCK").item(0);
                                if (stocks.getChildNodes().getLength() != 7) {
                                    pToRemove.add(p);
                                } else {
                                    for (int k = 0; k < stocks.getChildNodes().getLength(); k++) {
                                        Element oneStock = (Element) stocks.getChildNodes().item(k);
                                        switch (oneStock.getNodeName()) {
                                            //In each case, it's not possible that the value inside is not an integer (see wn-tarif-site web service)
                                            case "NORD":
                                                p.getStocks().put("SAINT-DENIS", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "SUD":
                                                p.getStocks().put("GENTILLY", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "BASTILLE":
                                                p.getStocks().put("BASTILLE", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "EST":
                                                p.getStocks().put("MONTREUIL", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "MONTMARTRE":
                                                p.getStocks().put("MONTMARTRE", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "LCDR":
                                                p.getStocks().put("LCDR", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                            case "ORLY":
                                                p.getStocks().put("ORLY", Integer.valueOf(oneStock.getTextContent()));
                                                break;
                                        }
                                    }
                                }
                                p.setAvailable(p.checkAvailable());
                                ////END GET STOCKS////
                                racine.removeChild(unRetour);
                                break;
                            } catch (Exception e) {
                                pToRemove.add(p);
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                }
                for (Produits p : pToRemove) {
                    pCollection.remove(p);
                }
            } else {
                // TODO error
            }
        } catch (Exception e) {
            Logger.getLogger(ProduitsRepository.class.getName()).severe("An error occured while getting informations form NX. (" + e.getMessage() + ")");
        }
    }



    /**
     * Check availability of a product List
     * called in Produits
     */
    public void checkKeys(List<String> keysToCheck, String cleClient) {
        checkKeys(keysToCheck, cleClient, null);
    }



    /**
     * Check availability of a product List and update displayProd object
     * called in Produits
     */
    public void checkKeys(List<String> keysToCheck, String cleClient, DisplayListProd displayObject) {
        try {
            //Creation du parametre
            StringBuilder param = new StringBuilder();
            int i = 0;
            for (String key : keysToCheck) {
                param.append((i == 0) ? key : ("," + key));
                i++;
            }
            String webService = "wn_product_exist " + param + "_" + cleClient;
            //Ajout des min price et max price si ils existent dans l'objet displayObject
            if (displayObject != null && displayObject.getMinMaxSelectedPrice() != null) {
                int min = displayObject.getMinMaxSelectedPrice().getLeft();
                min = displayObject.isProAsking() ? min : (int) (min / 1.2);
                int max = displayObject.getMinMaxSelectedPrice().getRight();
                max = displayObject.isProAsking() ? max : (int) (max / 1.2);
                webService += "_" + min + "_" + max;
            }
            Document result = this.df.execWebService(webService);
            final Element racine = result.getDocumentElement();
            Map<String, List<String>> results = new HashMap<>();
            if (racine.getNodeName().equals("RETOUR")) {
                if (displayObject != null) {
                    String maxPrice = racine.getElementsByTagName("MAXPRICE").item(0).getTextContent();
                    String minPrice = racine.getElementsByTagName("MINPRICE").item(0).getTextContent();
                    int minPriceInt = 0;
                    int maxPriceInt = 0;
                    try {
                        float maxTmp = Float.parseFloat(maxPrice);
                        float minTmp = Float.parseFloat(minPrice);
                        minPriceInt = (int) minTmp;
                        maxPriceInt = (int) maxTmp;
                    } catch (NumberFormatException e) {
                        //Do nothing
                    }
                    minPriceInt = displayObject.isProAsking() ? minPriceInt : (int) (minPriceInt * 1.2);
                    maxPriceInt = displayObject.isProAsking() ? maxPriceInt : (int) (maxPriceInt * 1.2);
                    displayObject.setMinMaxPrice(new Pair<>(minPriceInt, maxPriceInt));
                    //Check si les min et max price selected sont null : dans ce cas ils sont égaux aux min et max de tous les produits
                    if (displayObject.getMinMaxSelectedPrice() == null) {
                        displayObject.setMinMaxSelectedPrice(new Pair<>(displayObject.getMinMaxPrice().getLeft(), displayObject.getMinMaxPrice().getRight() + 1));
                    }
                }
                NodeList listExistingKeys = racine.getElementsByTagName("CLE");
                if (listExistingKeys != null && listExistingKeys.getLength() > 0) {
                    for (int j = 0; j < listExistingKeys.getLength(); j++) {
                        Element cleXML = (Element) listExistingKeys.item(j);
                        NodeList keyNodeList = cleXML.getElementsByTagName("KEY");
                        String cle = keyNodeList.item(0).getTextContent();
                        List<String> compKeys = new ArrayList<>();
                        NodeList compXML = ((Element) ((Element) listExistingKeys.item(j)).getElementsByTagName("COMP").item(0)).getElementsByTagName("KEY");
                        for (int k = 0; k < compXML.getLength(); k++) {
                            compKeys.add(compXML.item(k).getTextContent());
                        }
                        results.put(cle, compKeys);
                    }
                }
            }
            List<String> keysToRemove = new ArrayList<>();
            for (String key : keysToCheck) {
                if (results.get(key) == null) {
                    keysToRemove.add(key);
                } else {
                    if (!results.get(key).isEmpty()) {
                        //Check si les prod complementaires sont dispo dans notre base
                        for (String toCheck : results.get(key)) {
                           Produits tempRes = pr.checkKeys(toCheck);
                           if(tempRes == null) {  ////////// a verifier ...
                               keysToRemove.add(key);
                               break;
                           }

                            }
                        }
                    }
                }
            for (String k : keysToRemove) {
                keysToCheck.remove(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Une erreur s'est produite :" + e.getMessage());
        }
    }

////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////// A VERIFIER
////////////////////////////////////////////////////////////////////////////////////
    /**
     * Add to base request supplementary statements to handle filters in the request
     * called in Produits
     */
    public String filtersHandling(DisplayListProd displayObject, String base2) {
        if (!displayObject.getAppliedFilters().isEmpty()) {
            StringBuilder baseBuilder = new StringBuilder(base2);
            for (DisplayListProd.Filter f : displayObject.getAppliedFilters()) {
                baseBuilder.append("AND id_produits IN ( SELECT id_produits FROM CARACTERISTIQUES_PRODUITS CP WHERE (CP.id_caracteristiques = ").append(f.getIdFeature()).append(" AND ( ");
                int i = 0;
                for (Pair<String, String> p : f.getItems()) {
                    baseBuilder.append((i == 0) ? "CP.valeur = '" + p.getLeft().replace("'", "\\'") + "' " : " OR CP.valeur = '" + p.getLeft().replace("'", "\\'") + "' ");
                    i++;
                }
                baseBuilder.append(")))");
            }
            return baseBuilder.toString();
        }
        return base2;
    }
////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////// A VERIFIER (prepared statement ? et faire la methode dans produits
//////////////////////////////////////////////////////////////////////////////////////
    /**
     * Generate statement to get product list for sous famille, categorie or sous categorie case and filters
     * called in Produits
     */
    public String generatePreparedStmtProductListQuery(DisplayListProd displayObject) throws SQLException {
        if (displayObject.getSousCategorie() != null) {
            int i = displayObject.getSousCategorie().getIdSscategories();
            String base = "SELECT * FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT(code_article) FROM PRODUITS WHERE id_sscategories = "+i+" AND visible = 1 ";
            base = filtersHandling(displayObject, base);
            base += " LIMIT " + ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()) + " ," + displayObject.getNbPerPage() + ") as t) ORDER BY code_article, " + displayObject.getOrder() + ";";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        } else if (displayObject.getCategorie() != null) {
            int i = displayObject.getCategorie().getIdCategories();
            String base = "SELECT * FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT(code_article) FROM PRODUITS WHERE id_categories = "+i+" AND visible = 1 ";
            base = filtersHandling(displayObject, base);
            base += " LIMIT " + ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()) + " ," + displayObject.getNbPerPage() + ") as t) ORDER BY code_article, " + displayObject.getOrder() + ";";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        } else if (displayObject.getSousFamille() != null) {
            int i = displayObject.getSousFamille().getIdSsfamilles();
            String base = "SELECT * FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT(code_article) FROM PRODUITS WHERE id_ssfamilles = "+i+" AND visible = 1 ";
            base = filtersHandling(displayObject, base);
            base += " LIMIT " + ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()) + " ," + displayObject.getNbPerPage() + ") as t) ORDER BY code_article, " + displayObject.getOrder() + ";";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        }
        return null;
    }
////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////// A VERIFIER (prepared statement ? idem qu au dessus
//////////////////////////////////////////////////////////////////////////////////////

    /**
     * Generate statement to count products for sous famille, categorie or sous categorie case and filters
     *
     * @param displayObject
     * @return
     * @throws SQLException
     */
    private String generatePreparedStmtCountProductListQuery(DisplayListProd displayObject) throws SQLException {
        if (displayObject.getSousCategorie() != null) {
            int i = displayObject.getSousCategorie().getIdSscategories();
            String base = "SELECT COUNT(*) FROM (SELECT DISTINCT code_article FROM PRODUITS WHERE id_sscategories = "+i+" AND visible = 1";
            base = filtersHandling(displayObject, base);
            base += " ) AS t;";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        } else if (displayObject.getCategorie() != null) {
            int i = displayObject.getCategorie().getIdCategories();
//			//commented by etienne :String base = "SELECT cle_system,code_article FROM PRODUITS WHERE id_categories = ? AND visible = 1 ";
            String base = "SELECT COUNT(*) FROM (SELECT DISTINCT code_article FROM PRODUITS WHERE id_categories = "+i+" AND visible = 1";
            base = filtersHandling(displayObject, base);
            base += " ) AS t;";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        } else if (displayObject.getSousFamille() != null) {
            int i = displayObject.getSousFamille().getIdSsfamilles();
//			//commented by etienne : String base = "SELECT cle_system,code_article FROM PRODUITS WHERE id_ssfamilles = ? AND visible = 1 ";
            String base = "SELECT COUNT(*) FROM (SELECT DISTINCT code_article FROM PRODUITS WHERE id_ssfamilles = "+i+" AND visible = 1";
            base = filtersHandling(displayObject, base);
            base += " ) AS t;";
//			//commented by etienne :base = filtersHandling(displayObject, base) + " ORDER BY codeArticle;";
            Logger.getLogger(this.getClass().getName()).info(base);
            return base;
        }
        return null;
    }

////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////


    public void buildCaracteristiqueArborescence() throws DAOException {
        try {
            //Recuperation de toute l'arborescence et de toutes les caracteristiques
            List<Familles> familleList = fr.findAll();
            List<Caracteristiques> caracteristiqueList = cr.findAll();
            for (Familles famille : familleList) {
                for (SsFamilles sousFamille : famille.getSsFamillesByIdFamilles()) {
                    for (Categories categorie : sousFamille.getCategoriesByIdSsfamilles()) {
                        List<Caracteristiques> caracteristiqueCommunes = new ArrayList<>();
                        //Recuperation du nb de produits pour chaque categorie
                       int nbProduits = pr.buildCaracteristiqueArborescence1(categorie.getIdCategories());
                        for (Caracteristiques c : caracteristiqueList) {
                            int result = pr.buildCaracteristiqueArborescence2(categorie.getIdCategories(),c.getIdCaracteristiques());
                            if(nbProduits / 2 < result){
                                caracteristiqueCommunes.add(c);
                            }
                        }
                        //Recuperation des valeurs possibles
                        for (Caracteristiques c : caracteristiqueCommunes) {
                            List<CaracteristiquesProduits> tempValeurs = cpr.buildCaracteristiqueArborescence3(c.getIdCaracteristiques() , categorie.getIdCategories());
                            for(CaracteristiquesProduits caractProds : tempValeurs){
                                if(caractProds.getValeur() != ""){
                                    c.getValeurCollection().add(caractProds.getValeur());
                                }
                            }
                        }
                        for (Caracteristiques c : caracteristiqueCommunes) {
                            for (String val : c.getValeurCollection()) {
                                ccr.buildCaracteristiqueArborescence4(c.getIdCaracteristiques(),categorie.getIdCategories(),val);
                            }
                        }
                    }
                }
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception occured.");
        }

    }



    public List<Produits> getProductsInList(List<Produits> products, String cleClient) throws DAOException {
        ArrayList<Produits> ret = new ArrayList<>();
        try {
            for (Produits p : products) {
                Produits resultat = pr.findById(p.getIdProduits()).get();
                    // TODO Recup prix et img
                    ret.add(resultat);
            }
            getProductsInfoFromNx(ret, cleClient);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public int getIdByName(String name, int level) throws DAOException {
        int resultat = 0;
        if(level == 1){ resultat = fr.getIdByName(name); }
        if(level == 2){ resultat = sfr.getIdByName(name); }
        if(level == 3){ resultat = catr.getIdByName(name); }
        if(level == 4){ resultat = scatr.getIdByName(name); }
        return resultat;

    }




    public void checkAllKeys() {
        List<Integer> tempList = new ArrayList<>();
        ArrayList<String> resultList = new ArrayList<>();
        try {
            tempList = pr.checkAllKeys();
            for ( int item : tempList ) {
                String tempItem = String.valueOf(item);
                resultList.add(tempItem);
            }
            this.setNotVisibleProductsNotInNX(resultList);
        } catch (Exception e) {
            System.out.println(
                    "Un probleme est survenu pendant le check de toutes les cles systemes. : " + e.getMessage());
        }
    }



    // perhaps set removeDocument query to return void?
    // method greatly modified, if error check original version
    public boolean removeDocument(int idProduit, int idDoc) {
        boolean ret=false;
        try {
            // Remove the entry in DOCUMENT_PRODUIT
            dpr.removeDocument(idDoc, idProduit);
            if(dpr.selectDocument(idDoc,idProduit) == null){
                try{
                    dr.deleteById(idDoc);
                    ret = true;
                } catch (Exception e){
                    throw new DAOException("Une erreur est survenue sur delete2 : " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue sur delete1: " + e.getMessage());
        }
        return ret;
    }




    // according to original method, ret suposed to return document id? possible transactionnal error
    @Transactional
    public int addDocument(int idProduit, com.orvif.website3.Entity.Document doc) {
        int ret = -1;
        try {
            dr.saveAndFlush(doc);
            DocumentProduit docprod = new DocumentProduit();
            docprod.setIdDocument(doc.getId());
            docprod.setIdProduit(idProduit);
            ret = docprod.getIdDocument();
            dpr.saveAndFlush(docprod);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    // possible flush or @Transactionnal error
    @Transactional
    public boolean addSeveralProdSimilaire(int p, List<Integer> idProds) {
        boolean ret = true;
        List<ProduitsAlternatifs> list = new ArrayList<>();
        try{
        for ( int tempIdProd : idProds) {
            ProduitsAlternatifs pa = new ProduitsAlternatifs();
            pa.setIdProduits(p);
            pa.setIdProduitsAlt(tempIdProd);
            list.add(pa);
        }
        par.saveAll(list);
        par.flush();

        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    // possible flush or @Transactionnal error
    @Transactional
    public boolean addSeveralProdComplementaire(int p, List<Integer> idProds) {
        boolean ret = true;
        List<ProduitsAlternatifs> list = new ArrayList<>();
        try{
            for ( int tempIdProd : idProds) {
                ProduitsAlternatifs pa = new ProduitsAlternatifs();
                pa.setIdProduits(p);
                pa.setIdProduitsAlt(tempIdProd);
                list.add(pa);
            }
            par.saveAll(list);
            par.flush();

        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public List<Produits> getBySearchProduitAssocie(int prodBase, String search, String CompOuAlt, String cleClient) {
        ArrayList<Produits> ret = new ArrayList<>();
        // Traitement du text
        search = "%" + search + "%";
        ResultSet resultat = null;
        try {
            // Recuperation des produits complementaires/similaires pour les exclure de la
            // requete de recherche
            StringBuilder listId = new StringBuilder(String.valueOf(prodBase));
            List<Produits> ids;
            if (CompOuAlt.equals("complementaire")) {
                ids = this.getProduitsComplementaire(prodBase, cleClient);
            } else {
                ids = this.getProduitsSimilaire(prodBase, cleClient);
            }
            for (Produits pTmp : ids) {
                listId.append(", ").append(String.valueOf(pTmp.getIdProduits()));
            }
            ret = pr.getBySearchProduitAssocie( search, search, search ,listId);

            for ( Produits tempRet : ret) {
                tempRet.setDestockage(resultat.getInt("destockage") == 1);
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }


    // possibleflush / @transactionnal error
    @Transactional
    public boolean removeProdAssocie(int p, int pAssoc) {
        boolean ret = true;
        try {
            par.removeProdAssocie(p, pAssoc);
            par.flush();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public boolean removeProduct(int idProduit) {
        boolean ret;
        PreparedStatement preparedStmt = null;
        try {
            Produits p = pr.findById(idProduit).get();
            pr.delete(p);
            pr.flush();
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    // possibleflush / @transactionnal error
    @Transactional
    public boolean addProdAssocie(int p, int pAssoc) {
        boolean ret = true;
        try {
            par.addProdAssocie(p, pAssoc);
            par.flush();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }


    // possibleflush / @transactionnal error
    @Transactional
    public boolean removeProdComplementaire(int p, int pComp) {
        boolean ret = true;
        try {
            pcr.removeProdComplementaire(p, pComp);
            pcr.flush();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }


    // possibleflush / @transactionnal error
    @Transactional
    public boolean addProdComplementaire(int p, int pComp) {
        boolean ret = true;
        try {
            pcr.addProdComplementaire(p, pComp);
            pcr.flush();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }



    public List<Groupe> getBySearchAdmin(String text) throws DAOException {
        List<Groupe> ret = new ArrayList<>();
        // Traitement du text
        text = "%" + text + "%";
        try {
            ArrayList<Produits> prodList = new ArrayList<>();
            prodList = pr.getBySearchAdmin(text, text, text);
            for ( Produits tempsProd : prodList){
                Groupe groupe = new Groupe();
                groupe.setCodeArticle(tempsProd.getCodeArticle());
                groupe.setNumberRef(tempsProd.getIdProduits());
                groupe.getProducts().add(tempsProd);
                ret.add(groupe);
            }
            return ret;
        }  catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception occured when trying to search a product.");
        }
    }


    public List<Groupe> getRandomGroups(int number, String cleClient) throws DAOException {
        try {
            List<Groupe> groupes = new ArrayList<>();
            Groupe currentGroupe = null;
            List<Produits> listProd = pr.getRandomGroups2(number);

            for(Produits tProd : listProd) { // for each prod
                int codeArticle = tProd.getCodeArticle();
                tProd.setDestockage(tProd.isDestockage() == true);
                if (currentGroupe == null || currentGroupe.getId() != codeArticle) {
                    if (currentGroupe != null) {
                        groupes.add(currentGroupe);
                    }
                    currentGroupe = new Groupe();
                    currentGroupe.setId(codeArticle);//adds a groupe to the groupes array with its id = codearticle
                    List<com.orvif.website3.Entity.Document> documents = new ArrayList<>();
                    documents.add(dr.getFirstImageByProduct(tProd.getIdProduits()));// gets a document obj for one img
                    tProd.setImageCollection(documents);// sets that img as imgCollection of each prod
                }
                currentGroupe.getProducts().add(tProd);
            } // end each prod
            if (currentGroupe != null) {
                groupes.add(currentGroupe);
            }
            getProductsInfoFromNxGroup(groupes, cleClient);/////////////////////////
            return groupes;
        } catch (DAOException e) {
            throw e;
        }  catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception while fetching random groups.");
        }
    }





    public Groupe getGroupeByCodeArticle(int codeArticle) throws DAOException {
        try {
            ArrayList<Produits> prodList = pr.getGroupeByCodeArticle(codeArticle);
            Groupe groupe = new Groupe();
            groupe.setCodeArticle(codeArticle);
            boolean firstLoop = true;
            for (Produits tprod : prodList) {
                if (firstLoop){
                    firstLoop = false;
                    //Get famille, sous famille etc.
                    groupe.setLibelle(tprod.getLibelle());
                    groupe.setDescription(tprod.getDescriptif());
                    groupe.setAvantages(tprod.getAvantages());
                    groupe.setFamille(tprod.getFamillesByIdFamilles());
                    groupe.setSousFamille(tprod.getSsFamillesByIdSsfamilles());
                    groupe.setCategorie(tprod.getCategoriesByIdCategories());
                    groupe.setSousCategorie(tprod.getSsCategoriesByIdSscategories());
                    groupe.setMarque(tprod.getMarquesByIdMarques());
                    groupe.setGamme(tprod.getGammesByIdGammes());
                    groupe.setImageList(tprod.getImageCollection());
                    groupe.setDocumentList(tprod.getOtherDocCollection());
                    Map<String, List<com.orvif.website3.Entity.Document>> map = dh.getDocumentListByProduit(tprod.getIdProduits());
                   // getProductInfoFromNx(ret, cleClient);
                    groupe.setImageList(map.get("imageCollection"));
                    groupe.setDocumentList(map.get("otherCollection"));
                }
                tprod.setDestockage(tprod.isDestockage() == true);
                tprod.setVisible(tprod.isVisible() == true);
                // Get specs
                groupe.getProducts().add(tprod);
            }
            return groupe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception when trying to fetch groupe (article code : \"" + codeArticle + "\")");
        }
    }


    // ORIGINAL METHOD RETURNED A SINGLE PRODUIT, BUT I NOTICEDS SEVERAL PRODS UNDER SAME CLE SYSTEME...
    public Produits getByCleSysteme(int cleSystem) {
            Produits prodList = new Produits();
        try {
            prodList = pr.getByCleSysteme(cleSystem);
            prodList.setDestockage(prodList.isDestockage() == true);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return prodList;
    }



    // possible error in PR native query since its a joint query on a composite key...
    public ArrayList<Produits> getProduitsSimilaire(int id, String cleClient) {
        ArrayList<Produits> pList = new ArrayList<>();
        try {
             pList = pr.getProduitsSimilaire(id);
            for( Produits tprod : pList){
                tprod.setDestockage(tprod.isDestockage()==true);
                // TODO Recup une image
            }
            getProductsInfoFromNx(pList, cleClient);
            List<Produits> pToRemove = new ArrayList<>();
            for (Produits p : pList) {
                if (p.getPpht() == 0) pToRemove.add(p);
            }
            pList.removeAll(pToRemove);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return pList;
    }




    // possible error in PR native query since its a joint query on a composite key...
    public ArrayList<Produits> getProduitsComplementaire(int id, String cleClient) {
        ArrayList<Produits> ret = new ArrayList<>();
        try {
            ret = pr.getProduitsComplementaire(id);
            for( Produits tprod : ret)
                tprod.setDestockage(tprod.isDestockage() == true);
                /** ????????  if (resultat.getString("PC.obligatoire").equals("1")) {
                    produit.setObligatoire(true);
                } else {
                    produit.setObligatoire(false);
                }**/
                // TODO Recup une image
            getProductsInfoFromNx(ret, cleClient);
            List<Produits> pToRemove = new ArrayList<>();
            for (Produits p : ret) {
                if (p.getPpht() == 0) pToRemove.add(p);
            }
            ret.removeAll(pToRemove);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    //possible error in getDocumentListByProduit call
    public Produits getById(int id, String cleClient) {
        Produits ret = null;
        try {
            ret = pr.getById(id);
                Map<String, List<com.orvif.website3.Entity.Document>> map = dh.getDocumentListByProduit(ret.getIdProduits());
                ret.setImageCollection(map.get("imageCollection"));
                ret.setOtherDocCollection(map.get("otherCollection"));
                getProductInfoFromNx(ret, cleClient);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public Groupe getGroupeByProduct(int id, String cleClient) throws DAOException {
        ArrayList<Produits> lProd = new ArrayList<>();
        try {
            lProd = pr.getGroupeByProduct(id);
            Groupe groupe = new Groupe();
            boolean firstLine = true;
            for(Produits tProd : lProd){
                tProd.setDestockage(tProd.isDestockage()==true);
                if (firstLine) { //because same info for all prods in that grp
                    //Recuperation des docs/images
                    Map<String, List<com.orvif.website3.Entity.Document>> map = dh.getDocumentListByProduit(tProd.getIdProduits());
                    tProd.setMarquesByIdMarques(mh.getById(tProd.getIdMarques()));
                    tProd.setImageCollection(map.get("imageCollection"));
                    tProd.setOtherDocCollection(map.get("otherCollection"));
                    tProd.setFamillesByIdFamilles(fh.getByProduit(tProd.getIdProduits()));
                    tProd.setSsFamillesByIdSsfamilles(sfr.getByIdProduit(tProd.getIdProduits()));
                    tProd.setCategoriesByIdCategories(catr.getByIdProduit(tProd.getIdProduits()));
                    tProd.setSsCategoriesByIdSscategories(scatr.getByIdProduit(tProd.getIdProduits()));
                    groupe.setId(tProd.getCodeArticle());
                    firstLine = false;
                }
                tProd.setCaracteristiqueCollection(ch.getByProduit(tProd.getIdProduits()));
                groupe.getProducts().add(tProd);
            }
            if (groupe.getProducts().size() > 0) {
                getProductsInfoFromNx(groupe.getProducts(), cleClient);
            }
            return groupe;
        } catch (DAOException e) {
            throw e;
        }  catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception occured when fetching group.");
        }
    }



    // EXTREMELY ERROR PRONE
    public void fillDisplayListItemsBySearch(DisplayListProd displayObject, String cleClient, String[] words) {
        //Connection connexion1 = null;
       // PreparedStatement preparedStatement1 = null;
        //ResultSet result1 = null;
       // PreparedStatement preparedStatement2 = null;
       //ResultSet result2 = null;
      //  PreparedStatement preparedStatement3 = null;
        //ResultSet result3 = null;
      //  PreparedStatement preparedStatementFilter = null;
      //  ResultSet resultFilter = null;
        try {
            //Recuperation dans la base SQL
            /*****Traitement des filtres******/

//			for (DisplayListProd.Filter filter : displayObject.getAvailableFilters()) {
//				List<Pair<String, String>> newPairList = new ArrayList<>();
//				for (Pair<String, String> pair : filter.getItems()) {
//					DisplayListProd displayListProdTh = displayObject.clone();
//					displayListProdTh.addAppliedFilter(filter.getIdFeature(), pair.getLeft(), daoFactory.getCaracteristiqueDao());
//					preparedStatementFilter = generatePreparedStmtCountProductListQuery(displayListProdTh, connexion1);
//					resultFilter = preparedStatementFilter.executeQuery();
//					if (resultFilter.next()) {
//						newPairList.add(new Pair<>(pair.getLeft(), resultFilter.getString(1)));
//					}
//				}
//				filter.setItems(newPairList);
//			}
            ArrayList<Produits> ap = new ArrayList<>();
            if (words.length == 1) {
                String w = words[0];
                int i1 = ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage());
                int i2 = displayObject.getNbPerPage();

                ap = pr.fillDisplayListItemsBySearch1(words[0], words[0], w, i1, i2);
                //String query = "SELECT * FROM PRODUITS WHERE codeArticle IN (SELECT * FROM (SELECT DISTINCT(codeArticle) FROM PRODUITS WHERE code_orvif = ? OR ref_fournisseur = ? OR libelle LIKE '%" + words[0] + "%' AND visible = 1 LIMIT " + ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()) + " ," + displayObject.getNbPerPage() + ") as t) ORDER BY codeArticle;";
//				// commented by etienne : String query = "SELECT cle_system FROM PRODUITS WHERE code_orvif = ? OR ref_fournisseur = ? OR libelle LIKE '%" + words[0] + "%'";
            } else if (words.length == 0) {
                throw new DAOException("No words given for the search.");
            } else {
                String param1 = "";
                //String query = "SELECT * FROM PRODUITS WHERE codeArticle IN (SELECT * FROM (SELECT DISTINCT codeArticle FROM PRODUITS WHERE libelle LIKE ";
                int c = 0;
                for (String str : words) {
                    if (c == 0) {
                        param1 += " '%" + str + "%' ";
                        c++;
                    } else {
                        param1 += " AND libelle LIKE '%" + str + "%'";
                    }
                }
                param1 += " AND visible = 1 LIMIT " + ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()) + " ," + displayObject.getNbPerPage() + " ) as t)";
                ap = pr.fillDisplayListItemsBySearch2(param1);

            }

            Map<Integer, List<Produits>> groupProducts = new HashMap<>();
            for (Produits a : ap){
                a.setDestockage(a.isDestockage()==true);
                if(groupProducts.containsKey(a.getCodeArticle())){
                    groupProducts.get(a.getCodeArticle()).add(a);
                }else {
                    com.orvif.website3.Entity.Document image = dr.getFirstImageByProduct(a.getIdProduits());
                    if (image != null) {
                        a.getImageCollection().add(image);
                    }
                    groupProducts.put(a.getCodeArticle(), new ArrayList<Produits>());
                    groupProducts.get(a.getCodeArticle()).add(a);
                }
            }
            List<Groupe> groupList = new ArrayList<>();
            for (Map.Entry<Integer, List<Produits>> entry : groupProducts.entrySet()) {
                Groupe group = new Groupe();
                group.setId(entry.getKey());
                group.setProducts(entry.getValue());
                groupList.add(group);
            }
            displayObject.setGroupeList(groupList);

            //TODO different request for group number
            int ap3 = 0;
            if (words.length == 1) {
                String w = "%" + words[0] + "%";
                ap3 = pr.fillDisplayListItemsBySearch3(words[0], words[0], w);
                //String query = "SELECT COUNT(*) FROM (SELECT DISTINCT(codeArticle) FROM PRODUITS WHERE code_orvif = ? OR ref_fournisseur = ? OR libelle LIKE '%" + words[0] + "%' AND visible = 1) as t ORDER BY codeArticle;";
//				//commented by etienne : String query = "SELECT cle_system FROM PRODUITS WHERE code_orvif = ? OR ref_fournisseur = ? OR libelle LIKE '%" + words[0] + "%'";
            } else if (words.length == 0) {
                throw new DAOException("No words given for the search.");
            } else {
                String query = " ";
                int c = 0;
                for (String str : words) {
                    if (c == 0) {
                        query += " '%" + str + "%' ";
                        c++;
                    } else {
                        query += " AND libelle LIKE '%" + str + "%'";
                    }
                }
                query += " AND visible = 1 ) as t";
                ap3 = pr.fillDisplayListItemsBySearch4(query);
            }
            if (ap3 != 0) {
                displayObject.setNbResult(ap3);
            }

            if (displayObject.getNbResult() % displayObject.getNbPerPage() == 0) {
                displayObject.setLastPage(displayObject.getNbResult() / displayObject.getNbPerPage());
            } else {
                displayObject.setLastPage(round(displayObject.getNbResult() / displayObject.getNbPerPage() + 1));
            }
            getProductsInfoFromNxGroup(displayObject.getGroupeList(), cleClient);
            displayObject.setNbCurrentPage(displayObject.getGroupeList().size());

//			checkKeys(keys, cleClient, connexion1, displayObject);

//			displayObject.setNbResult(keys.size());
//			if ((keys.size() % displayObject.getNbPerPage()) == 0) {
//				displayObject.setLastPage(keys.size() / displayObject.getNbPerPage());
//			} else {
//				displayObject.setLastPage(round(keys.size() / displayObject.getNbPerPage() + 1));
//			}
//			StringBuilder paramKeys = new StringBuilder();
//			int i = 0;
//			for (String k : keys) {
//				paramKeys.append((i == 0) ? k : ("," + k));
//				i++;
//			}
//			if (displayObject.getOrder().contains("ppht")) {
//				String ascdesc = (displayObject.getOrder().equals("ppht asc")) ? "ASC" : "DESC";
//				String webService = "wn_tri_prix " + cleClient + "_" + ascdesc + "_" + paramKeys + "_" + displayObject.getNbPerPage() + "_"
//						+ displayObject.getPageNumber();
//				Document resultXML = this.daoFactory.execWebService(webService);
//				final Element racine = resultXML.getDocumentElement();
//				List<String> listFinalKeys = new ArrayList<>();
//				if (racine.getNodeName().equals("RETOUR")) {
//					NodeList list = racine.getElementsByTagName("CLE");
//					for (i = 0; i < list.getLength(); i++) {
//						listFinalKeys.add(list.item(i).getTextContent());
//					}
//				}
//
//				if (!listFinalKeys.isEmpty()) {
//					// Recuperation dans base SQL
//					StringBuilder paramIn = new StringBuilder();
//					i = 0;
//					for (String k : listFinalKeys) {
//						paramIn.append((i == 0) ? k : ("," + k));
//						i++;
//					}
//					String stmtRecupProds = "SELECT * FROM PRODUITS WHERE cle_system IN ( " + paramIn + ") ";
//					StringBuilder ordering = new StringBuilder();
//					for (int j = 0; j < listFinalKeys.size(); j++) {
//						if (j == 0) {
//							ordering.append(listFinalKeys.get(j));
//						} else {
//							ordering.append(",").append(listFinalKeys.get(j));
//						}
//					}
//					stmtRecupProds += " ORDER BY FIELD (cle_system," + ordering + ")";
//					preparedStatement2 = connexion1.prepareStatement(stmtRecupProds);
//					result2 = preparedStatement2.executeQuery();
//					while (result2.next()) {
//						Produit produit = new Produit();
//						produit.setId(result2.getInt("id_produits"));
//						produit.setLibelle(result2.getString("libelle"));
//						produit.setMarque(this.daoFactory.getMarqueDao().getById(result2.getInt("id_marques")));
//						produit.setCodeOrvif(result2.getString("code_orvif"));
//						produit.setCleSystem(result2.getInt("cle_system"));
//						displayObject.getProductList().add(produit);
//					}
//					getProductsInfoFromNx(displayObject.getProductList(), cleClient);
//				}
//			} else {
//				StringBuilder paramCles = new StringBuilder();
//				i = 0;
//				for (String k : keys) {
//					paramCles.append((i == 0) ? "\"" + k + "\"" : ",\"" + k + "\"");
//					i++;
//				}
//				if (paramCles.toString().equals(""))
//					return;
//				String query = "SELECT * FROM PRODUITS WHERE cle_system IN (" + paramCles + ") ORDER BY :order: LIMIT ? , ?";
//				query = query.replace(":order:", displayObject.getOrder());
//				preparedStatement3 = connexion1.prepareStatement(query);
//				preparedStatement3.setInt(1, ((displayObject.getPageNumber() - 1) * displayObject.getNbPerPage()));
//				preparedStatement3.setInt(2, displayObject.getNbPerPage());
//				result3 = preparedStatement3.executeQuery();
//				while (result3.next()) {
//					Produit produit = new Produit();
//					produit.setId(result3.getInt("id_produits"));
//					produit.setCleSystem(result3.getInt("cle_system"));
//					produit.setLibelle(result3.getString("libelle"));
//					produit.setMarque(this.daoFactory.getMarqueDao().getById(result3.getInt("id_marques")));
//					produit.setCodeOrvif(result3.getString("code_orvif"));
//					// TODO Recup prix et img
//					displayObject.getProductList().add(produit);
//				}
//				getProductsInfoFromNx(displayObject.getProductList(), cleClient);
//			}
//			displayObject.setNbCurrentPage(displayObject.getProductList().size());
        } catch (NullPointerException e) {
            throw new DAOException("Un probleme est survenu.");
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " eception occured.");
        }
    }





    public void fillDisplayListItems(DisplayListProd displayObject, String cleClient) {
        //Connection connexion1 = null;
       // PreparedStatement preparedStatement1 = null;
        //ResultSet result1 = null;
       // PreparedStatement preparedStatementFilter = null;
       // ResultSet resultFilter = null;
        try {
           // connexion1 = daoFactory.getConnection();
            /*****Traitement des filtres******/
//			for (DisplayListProd.Filter filter : displayObject.getAvailableFilters()) {
//				if(filter.getIdFeature() != 1){
//					List<Pair<String, String>> newPairList = new ArrayList<>();
//					for (Pair<String, String> pair : filter.getItems()) {
//						DisplayListProd displayListProdTh = displayObject.clone();
//						displayListProdTh.addAppliedFilter(filter.getIdFeature(), pair.getLeft(), daoFactory.getCaracteristiqueDao());
//						preparedStatementFilter = generatePreparedStmtCountProductListQuery(displayListProdTh, connexion1);
//						resultFilter = preparedStatementFilter.executeQuery();
//						if (resultFilter.next()) {
//							newPairList.add(new Pair<>(pair.getLeft(), resultFilter.getString(1)));
//						}
//					}
//					filter.setItems(newPairList);
//				}
//			}
            /************Recuperation du nombre de resultats soit pour les categories, soit pour les sous categories*************/
            this.countProductInSubCategory(displayObject);
            //Recuperation dans la base SQL
            //TODO prendre en compte "order" et les filtres
            String theQuery = generatePreparedStmtProductListQuery(displayObject);
            ArrayList<Produits> array = pr.fillDisplayListItems(theQuery);

            Map<Integer, List<Produits>> groupProducts = new HashMap<>();
            for( Produits p : array){
                p.setDestockage(p.isDestockage()==true);
                if (groupProducts.containsKey(p.getCodeArticle())) {
                    groupProducts.get(p.getCodeArticle()).add(p);
                } else {
                    com.orvif.website3.Entity.Document image = dr.getFirstImageByProduct(p.getIdProduits());
                    if (image != null) {
                        p.getImageCollection().add(image);
                    }
                    groupProducts.put(p.getCodeArticle(), new ArrayList<Produits>());
                    groupProducts.get(p.getCodeArticle()).add(p);
                }
            }
            List<Groupe> groupList = new ArrayList<>();
            for (Map.Entry<Integer, List<Produits>> entry : groupProducts.entrySet()) {
                Groupe group = new Groupe();
                group.setId(entry.getKey());
                group.setProducts(entry.getValue());
                groupList.add(group);
            }
            //TODO decide if we do the check or not (cost lot of time)
//			checkKeys(keys, cleClient, connexion1, displayObject);
            displayObject.setGroupeList(groupList);

            //TODO different request for group number
            theQuery  = generatePreparedStmtCountProductListQuery(displayObject);
            int resultat = pr.fillDisplayListItems3(theQuery);
            if (resultat != 0) {
                displayObject.setNbResult(resultat);
            }
            if (displayObject.getNbResult() % displayObject.getNbPerPage() == 0) {
                displayObject.setLastPage(displayObject.getNbResult() / displayObject.getNbPerPage());
            } else {
                displayObject.setLastPage(round(displayObject.getNbResult() / displayObject.getNbPerPage() + 1));
            }
            getProductsInfoFromNxGroup(displayObject.getGroupeList(), cleClient);
            displayObject.setNbCurrentPage(displayObject.getGroupeList().size());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Un probleme est survenu. [SQL Exception : " + e.getMessage() + "]");
        } catch (NullPointerException e) {
            throw new DAOException("Un probleme est survenu. [NullPointer Exception : " + e.getMessage() + "]");
        } catch (DAOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception occured.");
        }
    }


    @Transactional
    public void changeVisibility(int cleSystem, boolean visible) throws DAOException {
        try {
            boolean v =  visible ? true : false;
            pr.changeVisibility(v, cleSystem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception when trying to change product visibility (system key : \"" + cleSystem + "\")");
        }
    }




    public Produits getByIdAdmin(int id) {
        Optional<Produits> ret = pr.findById(id);
        return ret.get();
    }




    @Transactional
    public boolean update(Produits p, String modificateur) {
        boolean ret = false;
        try {
            Produits lastP = this.getByIdAdmin(p.getIdProduits());
            List<HistoriqueModification> modifs = p.genererHistorique(lastP, gr, catr, scatr, fr, sfr, mr, ufr, uvr);
            // Recuperation du produit pour comparer avant/apres pour l'historique
            if (!modifs.isEmpty()) {
                pr.updateCustom(p.getIdProduits(), p.getCleSystem(), p.getCodeOrvif(), p.getRefFournisseur(), p.getLibelle(), p.getDescriptif(), p.getAvantages(), p.getIdMarques(), p.getIdUv(), p.getIdUf(), p.getIdFamilles(), p.getIdSsfamilles(), p.getIdCategories(), p.getIdSscategories(), p.getIdGammes(), p.isVisible());
                    for (HistoriqueModification h : modifs) {
                        h.setModificateur(modificateur);
                    }
            }
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }


    public float getPrixTTCFromHT(float prix) {
        return (float) ((int) ((prix + (prix * 0.2)) * 100)) / 100;
    }

















    public Map<String, Integer> getStockCapitalized(Produits p) {
        Map<String, Integer> stocksCapitalized = new HashMap<>();
        for (Map.Entry<String, Integer> s : p.getStocks().entrySet()) {
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
    }







}
