package com.orvif.website3.Controller;


import com.orvif.website3.Entity.CodepostalSecteur;
import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.Produits;
import com.orvif.website3.Repository.CodepostalSecteurRepository;
import com.orvif.website3.Repository.FamillesRepository;
import com.orvif.website3.Repository.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class TestController {

    @Autowired
    private ProduitsRepository pr;
    @Autowired
    private CodepostalSecteurRepository csr;
    @Autowired
    private FamillesRepository fr;

    @GetMapping(path = "/hi")
    public String test(Model theModel) {
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        List<Produits> prod = pr.findAll();
        theModel.addAttribute("prods", prod);
        List<CodepostalSecteur> code = csr.findAll();
        theModel.addAttribute("codes", code);
        theModel.addAttribute("testText", "blablabla");


        return "test2";
    }
}
