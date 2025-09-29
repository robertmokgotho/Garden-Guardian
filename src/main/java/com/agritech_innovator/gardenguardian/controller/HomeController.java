package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.model.PestInput;
import com.agritech_innovator.gardenguardian.model.PestOutput;
import com.agritech_innovator.gardenguardian.model.WaterCalculation;
import com.agritech_innovator.gardenguardian.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {



    @GetMapping("/water")
    public String waterForm(Model model) {
        model.addAttribute("calculation", new WaterCalculation());
        return "water"; // Thymeleaf template name (water.html)
    }

    //WaterController
    @PostMapping("/water")
    public String calculate(@ModelAttribute WaterCalculation calculation, Model model) {
        // Perform calculations
        double totalWater = calculation.getArea() * calculation.getWaterRate() * calculation.getWateringDays();
        double estimatedCost = totalWater * calculation.getCostPerLiter();

        calculation.setTotalWater(totalWater);
        calculation.setEstimatedCost(estimatedCost);

        model.addAttribute("calculation", calculation);
        return "water"; // Return to the same template to display results
    }



    //Pest Controller

    @Autowired
    private PestService pestService;

    @GetMapping("/pest-tool")
    public String getForm(Model model) {
        model.addAttribute("pestInput", new PestInput());
        model.addAttribute("cropTypes", pestService.getCropTypes());
        return "pest-form"; // Thymeleaf template name
    }

    @PostMapping("/pest-tool")
    public String processForm(@ModelAttribute PestInput pestInput, Model model) {
        PestOutput output = pestService.identifyAndTreat(pestInput);
        model.addAttribute("pestOutput", output);
        return "pest-result"; // Thymeleaf template for results
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Maps to contact.html in templates
    }

}
