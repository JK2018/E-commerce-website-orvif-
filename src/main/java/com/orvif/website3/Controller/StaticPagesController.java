package com.orvif.website3.Controller;

import com.orvif.website3.Entity.Marques;
import com.orvif.website3.Entity.helper.MarquesHelper;
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
    private MarquesRepository mr;

    @Autowired
    private MarquesHelper mh;


    @GetMapping(path = "cgv")
    public String showConditionsGV() {
        return "conditionsgv";
    }

    @GetMapping(path = "livraison")
    public String showLivraison() {
        return "livraison";
    }

    @GetMapping(path = "club_avantages")
    public String showClubAvantage() {
        return "club_avantage";
    }

    @GetMapping("services")
    public String showServices() {
        return "services";
    }

    @GetMapping(path = "contact")
    public String showContact() {
        return "contact";
    }

    @GetMapping(path = "quisommesnous")
    public String showQuisommesnous() {
        return "quisommesnous";
    }

    @GetMapping(path = "showrooms")
    public String showShowrooms() {
        return "showrooms";
    }

    @GetMapping(path = "marques")
    public String showMarques(Model theModel) {
        List<Marques> m = new ArrayList<>();
        m = mh.getAllDistinctLogos();
        theModel.addAttribute("marques", m);
        return "marques";
    }






















}
