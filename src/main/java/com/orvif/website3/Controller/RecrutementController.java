package com.orvif.website3.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecrutementController {



    @GetMapping(path = "/recrutement")
    public String showRecrutement() {
        return "recrutement";
    }
}
