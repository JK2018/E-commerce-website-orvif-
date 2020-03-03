package com.orvif.website3.Controller;

import com.orvif.website3.Entity.helper.ProduitsHelper;
import org.w3c.dom.Element;
import com.orvif.website3.Entity.*;
import com.orvif.website3.Repository.*;
import com.orvif.website3.service.MCrypt;
import com.orvif.website3.service.SlideshowImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class AccueilController {

    private SlideshowImage sImg = new SlideshowImage();
    @Value("${slideShow.image1}")
    private String i1;
    @Value("${slideShow.image2}")
    private String i2;
    @Value("${slideShow.image3}")
    private String i3;
    @Autowired
    private FamillesRepository fr;
    @Autowired
    private GroupeRepository gr;
    @Autowired
    private ProduitsRepository pr;
    @Autowired
    private DocumentRepository dr;
    @Autowired
    private DAOFactory daoF;
    @Autowired
    private ProduitsHelper ph;


    private static final long serialVersionUID = 1L;
    private String view = "/WEB-INF/view_client/index.jsp";
    private String pageTitle = "ORVIF - Bienvenue";



    @GetMapping(path = "/")
    public String showAccueil(Model theModel, HttpSession session) {
        theModel.addAttribute("pageTitle", pageTitle);

        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);


        //slideshow imgs
        List<String> slideshow = new ArrayList<>();
        slideshow.add(i1);
        slideshow.add(i2);
        slideshow.add(i3);
        theModel.addAttribute("slideshowImageList", slideshow);




        //bandeaux
        String cleClient = "00001";
        if (session.getAttribute("client") != null) {
            cleClient = String.valueOf(((Utilisateurs) session.getAttribute("client")).getNumCli());
        }
        List<Groupe> groupes2 = ph.getRandomGroups(5, cleClient);
        System.out.println(groupes2.get(0).getProducts().get(0).isAvailable() + " : availability"); ///////////////////// availability test
        System.out.println(groupes2.get(0).getProducts().get(1).isAvailable() + " : availability"); ///////////////////// availability test
        System.out.println(groupes2.get(1).getProducts().get(0).isAvailable() + " : availability/prod id : " + groupes2.get(1).getProducts().get(0).getIdProduits() ); ///////////////////// availability test
        theModel.addAttribute("groups", groupes2);

        return "index";
    }

/////////////// unmapped methods //////////////////////





    /**
    private void getProductsInfoFromNxGroup(List<Groupe> groupes, String cleClient) {
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
        }
        try {
            List<Produits> pToRemove = new ArrayList<>();
            String webService = "appel_multiple_xml " + params;
            MCrypt mcrypt = new MCrypt();
            String encrypted = MCrypt.bytesToHex(mcrypt.encrypt(webService));
            Logger.getLogger(ProduitsRepository.class.getName()).info(encrypted);

            Document result = daoF.execWebService(webService); // <---------------------------- prob in daoFactory -- fixed (casts / imports)

            Element racine = (Element) result.getDocumentElement();
//			// En theorie, la racine ne peux pas etre differente de "RETOURS"


        // En theorie, la racine ne peux pas etre differente de "RETOURS"
        if (racine.getNodeName().equals("RETOURS")) {

            NodeList noeuds = racine.getChildNodes();
            for (Groupe groupe : groupes) {
                for (Produits p : groupe.getProducts()) {
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
                                        Produits pComp = this.pr.findByCleSystem(Integer.parseInt(cleSysytem));

                                        if (pComp != null) {
                                            pComp.setPpht(Float.parseFloat(compProd.getElementsByTagName("PRIXNET").item(0).getTextContent()));
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
            }
            //TODO ICI
            for (Produits p : pToRemove) {
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
            for (Groupe g : groupes) {
                double minGroup = 9999999;
                double maxGroup = 0;
                for (Produits p : g.getProducts()) {
                    if (p.getPpht() < minGroup && p.getPpht() > 0) {
                        minGroup = p.getPpht();
                    }
                    if (p.getPpht() > maxGroup) {
                        maxGroup = p.getPpht();
                    }
                }
                g.setMaxPriceHT(maxGroup);
                g.setMinPriceHT(minGroup);
            }
        } else {
            // TODO error
        }
    } catch (Exception e) {
        Logger.getLogger(ProduitsRepository.class.getName()).severe("An error occured while getting informations form NX. (" + e.getMessage() + ")");
    }
}
**/








}
