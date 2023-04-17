package com.ipi.jva320.Controller;


import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HomeController {
        @Autowired
        private SalarieAideADomicileService salarieAideADomicileService;
        @GetMapping("/")
        public String home(final ModelMap model) throws SalarieException {

            if(salarieAideADomicileService.countSalaries()==0){
                SalarieAideADomicile salarie=new SalarieAideADomicile("Jean", LocalDate.now(), LocalDate.now(), 10, 0,80,5,1);
                SalarieAideADomicile salarie2=new SalarieAideADomicile("Mohamed", LocalDate.now(), LocalDate.now(), 10, 0,80,5,1);
                salarieAideADomicileService.creerSalarieAideADomicile(salarie);
                salarieAideADomicileService.creerSalarieAideADomicile(salarie2);
            }

            model.put("nbSalarie",salarieAideADomicileService.countSalaries());
            model.put("htmlMessage"," Salariés)");


            return "home";
        }

        @GetMapping("/salaries/{id}")
        public String detailSalarie(final ModelMap model, @PathVariable("id") long id){
            SalarieAideADomicile salarie=salarieAideADomicileService.getSalarie(id);
            model.put("salarie",salarie);

            return "detail_Salarie";
        }
        @GetMapping("/salaries/aide/new")
        public String ajoutSalarie(){
            return "detail_Salarie";
        }
        @PostMapping("/salaries/aide/save")
        public String saveSalarie(SalarieAideADomicile salarie) throws SalarieException {

                salarieAideADomicileService.creerSalarieAideADomicile(salarie);



            return  "redirect:/salaries/"+salarie.getId();
        }

        @GetMapping("/salaries")
        public String salariesList(final ModelMap model){
            SalarieAideADomicile salarie;

            model.put("listSalaries", salarieAideADomicileService.getSalaries());
            return "list";
        }

    @PostMapping("/salaries/{id}")
    public String updateSalarie(SalarieAideADomicile salarie) throws SalarieException {

        salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        return  "redirect:/salaries/"+salarie.getId();
    }

    @GetMapping("/salaries/delete/{id}")
    public String deleteSalarie(SalarieAideADomicile salarie,@PathVariable("id") long id, final ModelMap model) throws SalarieException {
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
            model.put("listSalaries", salarieAideADomicileService.getSalaries());
            return "list";
    }

    @GetMapping("/salaries/recherche")
    public String findByNom(@RequestParam("nom") String nom, final ModelMap model){
           if(salarieAideADomicileService.getSalaries(nom)!=null) {
               model.put("listSalaries", salarieAideADomicileService.getSalaries(nom));
           }
        return "list";
    }
}
