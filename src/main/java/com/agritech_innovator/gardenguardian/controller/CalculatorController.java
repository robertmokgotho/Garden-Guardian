package com.agritech_innovator.gardenguardian.controller;


import com.agritech_innovator.gardenguardian.model.CalculatorInput;
import com.agritech_innovator.gardenguardian.model.Crop;
import com.agritech_innovator.gardenguardian.model.GardenPlan;
import com.agritech_innovator.gardenguardian.repository.GardenPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CalculatorController {

    @Autowired
    private GardenPlanRepository gardenPlanRepository;

    //Home
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/calculator")
    public String showForm(Model model) {
        model.addAttribute("input", new CalculatorInput());
        model.addAttribute("savedPlans", gardenPlanRepository.findAll());
        return "calculator";
    }

    @PostMapping("/calculator")
    public String calculate(@ModelAttribute("input") CalculatorInput input, Model model) {
        List<String> errors = new ArrayList<>();
        List<String> tips = new ArrayList<>();
        List<Object> results = new ArrayList<>();

        // Convert units to meters
        double gardenLength = input.getLengthUnit().equals("centimeters") ? input.getGardenLength() / 100 : input.getGardenLength();
        double gardenWidth = input.getWidthUnit().equals("centimeters") ? input.getGardenWidth() / 100 : input.getGardenWidth();
        double borderWidth = input.getBorderUnit().equals("centimeters") ? input.getBorderWidth() / 100 : input.getBorderWidth();
        double pathwayWidth = input.getPathwayUnit().equals("centimeters") ? input.getPathwayWidth() / 100 : input.getPathwayWidth();
        double pathwayLength = input.getPathwayUnit().equals("centimeters") ? input.getPathwayLength() / 100 : input.getPathwayLength();

        // Validate inputs
        if (gardenLength <= 0 || gardenWidth <= 0) {
            errors.add("Garden dimensions must be positive.");
        }
        if (borderWidth < 0 || borderWidth * 2 >= gardenLength || borderWidth * 2 >= gardenWidth) {
            errors.add("Invalid border width: must be non-negative and less than half of garden dimensions.");
        }
        if (pathwayWidth < 0 || pathwayWidth >= gardenWidth || pathwayLength < 0 || pathwayLength >= gardenLength) {
            errors.add("Invalid pathway dimensions: must be non-negative and fit within garden.");
        }
        if (input.getCrops().isEmpty()) {
            errors.add("At least one crop must be specified.");
        }

        // Calculate effective garden area (subtract borders and pathways)
        double effectiveLength = gardenLength - 2 * borderWidth - pathwayLength;
        double effectiveWidth = gardenWidth - 2 * borderWidth - pathwayWidth;
        double gardenArea = effectiveLength * effectiveWidth; // in square meters

        if (effectiveLength <= 0 || effectiveWidth <= 0) {
            errors.add("Effective garden area is too small after borders and pathways.");
        }

        double totalSeedCost = 0;
        long totalPlants = 0;

        if (errors.isEmpty()) {
            for (CalculatorInput.CropInput crop : input.getCrops()) {
                double spacing = crop.getSpacingUnit().equals("centimeters") ? crop.getPlantSpacing() / 100 : crop.getPlantSpacing();
                if (spacing <= 0 || crop.getSeedsPerPack() <= 0 || crop.getPricePerPack() < 0) {
                    errors.add("Invalid crop data for " + (crop.getName() != null ? crop.getName() : "Crop") + ": spacing and seeds must be positive, price non-negative.");
                    continue;
                }
                if (spacing > effectiveLength || spacing > effectiveWidth) {
                    errors.add("Spacing for " + (crop.getName() != null ? crop.getName() : "Crop") + " is too large for effective garden size.");
                    continue;
                }

                // Calculate plants based on layout
                long plantsAlongLength, plantsAlongWidth;
                if (input.getLayoutType().equals("triangular")) {
                    plantsAlongLength = (long) (effectiveLength / spacing) + 1;
                    plantsAlongWidth = (long) (effectiveWidth / (spacing * Math.sqrt(3) / 2)) + 1;
                } else {
                    plantsAlongLength = (long) (effectiveLength / spacing) + 1;
                    plantsAlongWidth = (long) (effectiveWidth / spacing) + 1;
                }
                long cropPlants = plantsAlongLength * plantsAlongWidth;
                totalPlants += cropPlants;

                // Calculate seed packs and cost
                double packsNeeded = Math.ceil((double) cropPlants / crop.getSeedsPerPack());
                double cropSeedCost = packsNeeded * crop.getPricePerPack();
                totalSeedCost += cropSeedCost;

                // Add result for this crop
                results.add(new Object() {
                    public String name = crop.getName() != null ? crop.getName() : "Crop";
                    public long plants = cropPlants;
                    public double cost = cropSeedCost;
                });

                // Add tips
                if (spacing < 0.1) {
                    tips.add("Warning for " + (crop.getName() != null ? crop.getName() : "Crop") + ": Small spacing (< 10 cm) may lead to nutrient competition. Consider companion planting.");
                } else if (spacing > 1) {
                    tips.add("Tip for " + (crop.getName() != null ? crop.getName() : "Crop") + ": Large spacing (> 1 m) is suitable for larger crops like pumpkins. Ensure adequate soil nutrients.");
                }
            }

            // Calculate additional costs
            double soilCost = input.getSoilCostPerSqm() * gardenArea;
            double fertilizerCost = input.getFertilizerCostPerSqm() * gardenArea;
            double totalCost = totalSeedCost + soilCost + fertilizerCost;

            model.addAttribute("results", results);
            model.addAttribute("totalPlants", totalPlants);
            model.addAttribute("totalSeedCost", totalSeedCost);
            model.addAttribute("soilCost", soilCost);
            model.addAttribute("fertilizerCost", fertilizerCost);
            model.addAttribute("totalCost", totalCost);
            model.addAttribute("gardenArea", gardenArea);
            model.addAttribute("effectiveLength", effectiveLength);
            model.addAttribute("effectiveWidth", effectiveWidth);
            model.addAttribute("tips", tips);
        }

        model.addAttribute("savedPlans", gardenPlanRepository.findAll());
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
        }
        model.addAttribute("input", input); // Retain form values
        return "calculator";
    }

    @PostMapping("/calculator/save")
    public String savePlan(@ModelAttribute("input") CalculatorInput input, Model model) {
        List<String> errors = validateInput(input);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("input", input);
            model.addAttribute("savedPlans", gardenPlanRepository.findAll());
            return "calculator";
        }

        // Convert CalculatorInput to GardenPlan
        GardenPlan plan = new GardenPlan();
        plan.setPlanName(input.getPlanName() != null && !input.getPlanName().isEmpty()
                ? input.getPlanName()
                : "Plan_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
        plan.setGardenLength(input.getGardenLength());
        plan.setGardenWidth(input.getGardenWidth());
        plan.setBorderWidth(input.getBorderWidth());
        plan.setPathwayWidth(input.getPathwayWidth());
        plan.setPathwayLength(input.getPathwayLength());
        plan.setLengthUnit(input.getLengthUnit());
        plan.setWidthUnit(input.getWidthUnit());
        plan.setBorderUnit(input.getBorderUnit());
        plan.setPathwayUnit(input.getPathwayUnit());
        plan.setLayoutType(input.getLayoutType());
        plan.setSoilCostPerSqm(input.getSoilCostPerSqm());
        plan.setFertilizerCostPerSqm(input.getFertilizerCostPerSqm());

        List<Crop> crops = new ArrayList<>();
        for (CalculatorInput.CropInput cropInput : input.getCrops()) {
            Crop crop = new Crop();
            crop.setName(cropInput.getName());
            crop.setPlantSpacing(cropInput.getPlantSpacing());
            crop.setSpacingUnit(cropInput.getSpacingUnit());
            crop.setSeedsPerPack(cropInput.getSeedsPerPack());
            crop.setPricePerPack(cropInput.getPricePerPack());
            crops.add(crop);
        }
        plan.setCrops(crops);

        gardenPlanRepository.save(plan);
        model.addAttribute("message", "Plan saved successfully!");
        model.addAttribute("input", input);
        model.addAttribute("savedPlans", gardenPlanRepository.findAll());
        return "calculator";
    }

    @GetMapping("/calculator/load/{id}")
    public String loadPlan(@PathVariable UUID id, Model model) {
        GardenPlan plan = gardenPlanRepository.findById(id).orElse(null);
        if (plan == null) {
            model.addAttribute("errors", List.of("Plan not found."));
            model.addAttribute("input", new CalculatorInput());
            model.addAttribute("savedPlans", gardenPlanRepository.findAll());
            return "calculator";
        }

        CalculatorInput input = new CalculatorInput();
        input.setPlanName(plan.getPlanName());
        input.setGardenLength(plan.getGardenLength());
        input.setGardenWidth(plan.getGardenWidth());
        input.setBorderWidth(plan.getBorderWidth());
        input.setPathwayWidth(plan.getPathwayWidth());
        input.setPathwayLength(plan.getPathwayLength());
        input.setLengthUnit(plan.getLengthUnit());
        input.setWidthUnit(plan.getWidthUnit());
        input.setBorderUnit(plan.getBorderUnit());
        input.setPathwayUnit(plan.getPathwayUnit());
        input.setLayoutType(plan.getLayoutType());
        input.setSoilCostPerSqm(plan.getSoilCostPerSqm());
        input.setFertilizerCostPerSqm(plan.getFertilizerCostPerSqm());

        List<CalculatorInput.CropInput> crops = new ArrayList<>();
        for (Crop crop : plan.getCrops()) {
            CalculatorInput.CropInput cropInput = new CalculatorInput.CropInput();
            cropInput.setName(crop.getName());
            cropInput.setPlantSpacing(crop.getPlantSpacing());
            cropInput.setSpacingUnit(crop.getSpacingUnit());
            cropInput.setSeedsPerPack(crop.getSeedsPerPack());
            cropInput.setPricePerPack(crop.getPricePerPack());
            crops.add(cropInput);
        }
        input.setCrops(crops);

        model.addAttribute("input", input);
        model.addAttribute("savedPlans", gardenPlanRepository.findAll());
        return "calculator";
    }

    private List<String> validateInput(CalculatorInput input) {
        List<String> errors = new ArrayList<>();
        double gardenLength = input.getLengthUnit().equals("centimeters") ? input.getGardenLength() / 100 : input.getGardenLength();
        double gardenWidth = input.getWidthUnit().equals("centimeters") ? input.getGardenWidth() / 100 : input.getGardenWidth();
        double borderWidth = input.getBorderUnit().equals("centimeters") ? input.getBorderWidth() / 100 : input.getBorderWidth();
        double pathwayWidth = input.getPathwayUnit().equals("centimeters") ? input.getPathwayWidth() / 100 : input.getPathwayWidth();
        double pathwayLength = input.getPathwayUnit().equals("centimeters") ? input.getPathwayLength() / 100 : input.getPathwayLength();

        if (gardenLength <= 0 || gardenWidth <= 0) {
            errors.add("Garden dimensions must be positive.");
        }
        if (borderWidth < 0 || borderWidth * 2 >= gardenLength || borderWidth * 2 >= gardenWidth) {
            errors.add("Invalid border width: must be non-negative and less than half of garden dimensions.");
        }
        if (pathwayWidth < 0 || pathwayWidth >= gardenWidth || pathwayLength < 0 || pathwayLength >= gardenLength) {
            errors.add("Invalid pathway dimensions: must be non-negative and fit within garden.");
        }
        if (input.getCrops().isEmpty()) {
            errors.add("At least one crop must be specified.");
        }
        for (CalculatorInput.CropInput crop : input.getCrops()) {
            double spacing = crop.getSpacingUnit().equals("centimeters") ? crop.getPlantSpacing() / 100 : crop.getPlantSpacing();
            if (spacing <= 0 || crop.getSeedsPerPack() <= 0 || crop.getPricePerPack() < 0) {
                errors.add("Invalid crop data for " + (crop.getName() != null ? crop.getName() : "Crop") + ": spacing and seeds must be positive, price non-negative.");
            }
        }
        return errors;
    }
}
