package com.orvif.website3.Controller;

import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Entity.helper.MarquesHelper;
import com.orvif.website3.Repository.FamillesRepository;
import com.orvif.website3.Repository.MarquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StaticPagesController {

    @Autowired
    private FamillesRepository fr;

    @Autowired
    private MarquesRepository mr;

    @Autowired
    private MarquesHelper mh;


    @GetMapping(path = "cgv")
    public String showConditionsGV(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "conditionsgv";
    }

    @GetMapping(path = "livraison")
    public String showLivraison(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "livraison";
    }





    @GetMapping(path = "club_avantages")
    public String showClubAvantage(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "club_avantage";
    }





    @GetMapping("services")
    public String showServices(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "services";
    }





    @GetMapping(path = "contact")
    public String showContact(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "contact";
    }





    @GetMapping(path = "quisommesnous")
    public String showQuisommesnous(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "quisommesnous";
    }




    @GetMapping(path = "showrooms")
    public String showShowrooms(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        return "showrooms";
    }




    @GetMapping(path = "marques")
    public String showMarques(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        List<Marques> m = new ArrayList<>();
        m = mh.getAllDistinctLogos();
        theModel.addAttribute("marques", m);
        return "marques";
    }






















}
