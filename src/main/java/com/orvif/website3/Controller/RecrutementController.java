package com.orvif.website3.Controller;

import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.JobOffer;
import com.orvif.website3.Repository.FamillesRepository;
import com.orvif.website3.Repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecrutementController {



    @Autowired
    private JobOfferRepository jor;

    @Autowired
    private FamillesRepository fr;


    @GetMapping("recrutement")
    public String showRecrutement(Model theModel) {
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        List<JobOffer> ajo = jor.findAll();
        theModel.addAttribute("jobOfferList", ajo);
        return "recrutement";
    }



    @GetMapping("annonce{id}")
    public String showJobOffers(Model theModel, @PathVariable String id){
        //menu
        List<Familles> famCol = fr.findAll();
        theModel.addAttribute("familleCollection", famCol);

        JobOffer jo = jor.findById(Integer.parseInt(id)).get();
        theModel.addAttribute("jobOffer", jo);
        return "recrutementAnnonce";

    }


















}
