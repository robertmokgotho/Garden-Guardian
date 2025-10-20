package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PestApiController {

    @Autowired
    private PestService pestService;

    @GetMapping("/pest-tool/symptoms")
    public String[] getSymptoms(@RequestParam String crop) {
        return pestService.getSymptomsForCrop(crop);
    }
}