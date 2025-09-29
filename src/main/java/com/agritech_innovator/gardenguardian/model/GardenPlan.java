package com.agritech_innovator.gardenguardian.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "garden_plans")
public class GardenPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String planName;
    private double gardenLength;
    private double gardenWidth;
    private double borderWidth;
    private double pathwayWidth;
    private double pathwayLength;
    private String lengthUnit;
    private String widthUnit;
    private String borderUnit;
    private String pathwayUnit;
    private String layoutType;
    private double soilCostPerSqm;
    private double fertilizerCostPerSqm;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "garden_plan_id")
    private List<Crop> crops = new ArrayList<>();

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
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

    public List<Crop> getCrops() {
        return crops;
    }

    public void setCrops(List<Crop> crops) {
        this.crops = crops;
    }
}
