package com.agritech_innovator.gardenguardian.dto;

public class PestOutput {
    private String pestName;
    private String treatmentMethod;
    private double sprayAmount; // in liters or units
    private double totalCost; // in currency units, e.g., USD

    // Constructors, Getters, and Setters
    public PestOutput() {}

    public PestOutput(String pestName, String treatmentMethod, double sprayAmount, double totalCost) {
        this.pestName = pestName;
        this.treatmentMethod = treatmentMethod;
        this.sprayAmount = sprayAmount;
        this.totalCost = totalCost;
    }

    public String getPestName() {
        return pestName;
    }

    public void setPestName(String pestName) {
        this.pestName = pestName;
    }

    public String getTreatmentMethod() {
        return treatmentMethod;
    }

    public void setTreatmentMethod(String treatmentMethod) {
        this.treatmentMethod = treatmentMethod;
    }

    public double getSprayAmount() {
        return sprayAmount;
    }

    public void setSprayAmount(double sprayAmount) {
        this.sprayAmount = sprayAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}