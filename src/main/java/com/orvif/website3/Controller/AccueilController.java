package com.orvif.website3.Controller;

import com.orvif.website3.Entity.helper.ProduitsHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
        theModel.addAttribute("groups", groupes2);

        return "index";
    }

/**
    @GetMapping(path = "/lol")
    public String test(){
        return "ficheProduit";
    }**/









}
