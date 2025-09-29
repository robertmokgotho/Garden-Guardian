package com.agritech_innovator.gardenguardian.model;

public class WaterCalculation {
    private double area; // in square meters
    private double waterRate; // water rate in liters per square meter (per watering day)
    private int wateringDays; // number of watering days
    private double costPerLiter; // cost per liter
    private double totalWater; // computed total water used in liters
    private double estimatedCost; // computed estimated cost

    // Getters and Setters
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getWaterRate() {
        return waterRate;
    }

    public void setWaterRate(double waterRate) {
        this.waterRate = waterRate;
    }

    public int getWateringDays() {
        return wateringDays;
    }

    public void setWateringDays(int wateringDays) {
        this.wateringDays = wateringDays;
    }

    public double getCostPerLiter() {
        return costPerLiter;
    }

    public void setCostPerLiter(double costPerLiter) {
        this.costPerLiter = costPerLiter;
    }

    public double getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(double totalWater) {
        this.totalWater = totalWater;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
