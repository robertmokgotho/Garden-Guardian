package com.agritech_innovator.gardenguardian.model;

import jakarta.persistence.*;

@Entity
@Table(name = "crops")
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double plantSpacing;
    private String spacingUnit;
    private int seedsPerPack;
    private double pricePerPack;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
