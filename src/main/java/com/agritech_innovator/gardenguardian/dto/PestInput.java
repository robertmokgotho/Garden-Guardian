package com.agritech_innovator.gardenguardian.dto;

public class PestInput {
    private String cropType;
    private String symptoms;
    private double affectedArea; // in square meters

    // Getters and Setters
    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public double getAffectedArea() {
        return affectedArea;
    }

    public void setAffectedArea(double affectedArea) {
        this.affectedArea = affectedArea;
    }
}
