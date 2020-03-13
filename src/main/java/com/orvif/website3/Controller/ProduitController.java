package com.orvif.website3.Controller;

import com.orvif.website3.Entity.*;
import com.orvif.website3.Entity.helper.*;
import com.orvif.website3.Repository.FamillesRepository;
import com.orvif.website3.form.FormProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class ProduitController {

    @Autowired
    private DocumentHelper dh;

    @Autowired
    private ProduitsHelper ph;

    @Autowired
    private GroupHelper gh;

    @Autowired
    private FamillesRepository fr;

    @Autowired
    private FamillesHelper fh;

    @Autowired
    private SsFamillesHelper sfh;

    @Autowired
    private CategoriesHelper ch;


    private final String view_error = "error";
    private final String view = "ficheProduit";




    @GetMapping(path = "/P{idProduits}__{libelleUrl}")
    public String showAccueil(HttpSession session, Model theModel, @PathVariable int idProduits) {

        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        //client
        String cleClient = "00001";
        if (session.getAttribute("client") != null) {
            cleClient = String.valueOf(((Utilisateurs) session.getAttribute("client")).getNumCli());
        }

        // get all prod data
        Groupe g = ph.getGroupeByProduct(idProduits, cleClient);

        //broken getstockcapitalized method
        for (Produits pp : g.getProducts()){
            Map<String, Integer> stocksCapitalized = new HashMap<>();
            for (Map.Entry<String, Integer> s : pp.getStocks().entrySet()) {
                if (s.getKey().equals("LCDR"))
                    continue;
                if (s.getKey().equals("ORLY")) {
                    stocksCapitalized.put("Drive Orly", s.getValue());
                    continue;
                }
                String key = s.getKey().substring(0, 1).toUpperCase() + s.getKey().substring(1).toLowerCase();
                stocksCapitalized.put(key, s.getValue());
            }
            pp.setStockCapitalized(stocksCapitalized);
        }

        //arianeSubtop
        Categories c = ch.getByIdProduit(idProduits);
        SsFamilles sf = sfh.getByIdProduit(idProduits);
        Familles f = fh.getByProduit(idProduits);
        String as = "Accueil > "+f.getLibelle()+" > "+sf.getLibelle()+" > "+c.getLibelle();

        theModel.addAttribute("as", as);
        theModel.addAttribute("groupe", g);

        return "ficheProduit";
    }












}
