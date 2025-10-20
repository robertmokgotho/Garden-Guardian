package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.model.PestInput;
import com.agritech_innovator.gardenguardian.model.PestOutput;
import com.agritech_innovator.gardenguardian.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PestController {

    @Autowired
    private PestService pestService;

    @GetMapping("/pest-tool")
    public String showForm(Model model) {
        model.addAttribute("pestInput", new PestInput());
        model.addAttribute("cropTypes", pestService.getCropTypes());
        // Initialize with default symptoms for the first crop or empty
        model.addAttribute("symptoms", pestService.getSymptomsForCrop("Tomato"));
        return "pest-form"; // Thymeleaf template name
    }

    @PostMapping("/pest-tool")
    public String processForm(@ModelAttribute PestInput pestInput, Model model) {
        PestOutput output = pestService.identifyAndTreat(pestInput);
        model.addAttribute("pestOutput", output);
        return "pest-result"; // Thymeleaf template for results
    }
}
