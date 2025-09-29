package com.agritech_innovator.gardenguardian.model;

import java.util.ArrayList;
import java.util.List;

public class CalculatorInput {
    private String planName; // Added field for plan name
    private double gardenLength; // in meters
    private double gardenWidth; // in meters
    private double borderWidth; // in meters
    private double pathwayWidth; // in meters
    private double pathwayLength; // in meters
    private String lengthUnit; // meters or centimeters
    private String widthUnit; // meters or centimeters
    private String borderUnit; // meters or centimeters
    private String pathwayUnit; // meters or centimeters
    private String layoutType; // grid or triangular
    private double soilCostPerSqm; // ZAR per square meter
    private double fertilizerCostPerSqm; // ZAR per square meter
    private List<CropInput> crops; // List of crops

    public CalculatorInput() {
        this.crops = new ArrayList<>();
        this.crops.add(new CropInput()); // Initialize with one crop
        this.lengthUnit = "meters";
        this.widthUnit = "meters";
        this.borderUnit = "meters";
        this.pathwayUnit = "meters";
        this.layoutType = "grid";
    }

    // Getters and Setters
    public String getPlanName() { // Added getter
        return planName;
    }

    public void setPlanName(String planName) { // Added setter
        this.planName = planName;
    }

    public double getGardenLength() {
        return gardenLength;
    }

    public void setGardenLength(double gardenLength) {
        this.gardenLength = gardenLength;
    }

    public double getGardenWidth() {
        return gardenWidth;
    }

    public void setGardenWidth(double gardenWidth) {
        this.gardenWidth = gardenWidth;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public double getPathwayWidth() {
        return pathwayWidth;
    }

    public void setPathwayWidth(double pathwayWidth) {
        this.pathwayWidth = pathwayWidth;
    }

    public double getPathwayLength() {
        return pathwayLength;
    }

    public void setPathwayLength(double pathwayLength) {
        this.pathwayLength = pathwayLength;
    }

    public String getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public String getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(String widthUnit) {
        this.widthUnit = widthUnit;
    }

    public String getBorderUnit() {
        return borderUnit;
    }

    public void setBorderUnit(String borderUnit) {
        this.borderUnit = borderUnit;
    }

    public String getPathwayUnit() {
        return pathwayUnit;
    }

    public void setPathwayUnit(String pathwayUnit) {
        this.pathwayUnit = pathwayUnit;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public double getSoilCostPerSqm() {
        return soilCostPerSqm;
    }

    public void setSoilCostPerSqm(double soilCostPerSqm) {
        this.soilCostPerSqm = soilCostPerSqm;
    }

    public double getFertilizerCostPerSqm() {
        return fertilizerCostPerSqm;
    }

    public void setFertilizerCostPerSqm(double fertilizerCostPerSqm) {
        this.fertilizerCostPerSqm = fertilizerCostPerSqm;
    }

    public List<CropInput> getCrops() {
        return crops;
    }

    public void setCrops(List<CropInput> crops) {
        this.crops = crops;
    }


    public static class CropInput {
        private String name;
        private double plantSpacing; // in meters
        private String spacingUnit; // meters or centimeters
        private int seedsPerPack;
        private double pricePerPack; // in ZAR

        public CropInput() {
            this.spacingUnit = "meters";
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPlantSpacing() {
            return plantSpacing;
        }

        public void setPlantSpacing(double plantSpacing) {
            this.plantSpacing = plantSpacing;
        }

        public String getSpacingUnit() {
            return spacingUnit;
        }

        public void setSpacingUnit(String spacingUnit) {
            this.spacingUnit = spacingUnit;
        }

        public int getSeedsPerPack() {
            return seedsPerPack;
        }

        public void setSeedsPerPack(int seedsPerPack) {
            this.seedsPerPack = seedsPerPack;
        }

        public double getPricePerPack() {
            return pricePerPack;
        }

        public void setPricePerPack(double pricePerPack) {
            this.pricePerPack = pricePerPack;
        }
    }
}
