package com.agritech_innovator.gardenguardian.service;

import com.agritech_innovator.gardenguardian.model.PestInput;
import com.agritech_innovator.gardenguardian.model.PestOutput;
import org.springframework.stereotype.Service;

@Service
public class PestService {

    public PestOutput identifyAndTreat(PestInput input) {
        String cropType = input.getCropType().toLowerCase();
        String symptoms = input.getSymptoms().toLowerCase();
        double area = input.getAffectedArea();

        String pestName = "Unknown";
        String treatmentMethod = "Consult an expert";
        double sprayRatePerSqm = 0.0; // liters per sqm
        double costPerUnit = 0.0; // cost per liter

        // Expanded rule-based logic for more crop types
        if (cropType.contains("tomato")) {
            if (symptoms.contains("wilting") || symptoms.contains("yellow leaves")) {
                pestName = "Fusarium Wilt";
                treatmentMethod = "Apply fungicide and remove infected plants";
                sprayRatePerSqm = 0.05;
                costPerUnit = 10.0;
            } else if (symptoms.contains("spots") || symptoms.contains("blight")) {
                pestName = "Late Blight";
                treatmentMethod = "Use copper-based fungicide";
                sprayRatePerSqm = 0.1;
                costPerUnit = 15.0;
            }
        } else if (cropType.contains("wheat")) {
            if (symptoms.contains("rust") || symptoms.contains("brown spots")) {
                pestName = "Wheat Rust";
                treatmentMethod = "Apply triazole fungicide";
                sprayRatePerSqm = 0.08;
                costPerUnit = 12.0;
            } else if (symptoms.contains("aphids") || symptoms.contains("sticky leaves")) {
                pestName = "Aphids";
                treatmentMethod = "Insecticidal soap spray";
                sprayRatePerSqm = 0.04;
                costPerUnit = 8.0;
            }
        } else if (cropType.contains("corn")) {
            if (symptoms.contains("holes in leaves") || symptoms.contains("chewed")) {
                pestName = "Corn Borer";
                treatmentMethod = "Apply Bacillus thuringiensis (Bt) spray";
                sprayRatePerSqm = 0.06;
                costPerUnit = 9.0;
            }
        } else if (cropType.contains("rice")) {
            if (symptoms.contains("yellowing") || symptoms.contains("stunted")) {
                pestName = "Rice Blast";
                treatmentMethod = "Apply tricyclazole fungicide";
                sprayRatePerSqm = 0.07;
                costPerUnit = 11.0;
            }
        } else if (cropType.contains("potato")) {
            if (symptoms.contains("black spots") || symptoms.contains("scabs")) {
                pestName = "Potato Scab";
                treatmentMethod = "Use resistant varieties and sulfur-based treatment";
                sprayRatePerSqm = 0.05;
                costPerUnit = 10.0;
            }
        } else if (cropType.contains("soybean")) {
            if (symptoms.contains("defoliation") || symptoms.contains("chewed leaves")) {
                pestName = "Soybean Looper";
                treatmentMethod = "Apply spinosad insecticide";
                sprayRatePerSqm = 0.06;
                costPerUnit = 12.0;
            }
        } else if (cropType.contains("cotton")) {
            if (symptoms.contains("boll damage") || symptoms.contains("worms")) {
                pestName = "Bollworm";
                treatmentMethod = "Apply pyrethroid insecticide";
                sprayRatePerSqm = 0.08;
                costPerUnit = 14.0;
            }
        } else if (cropType.contains("sugarcane")) {
            if (symptoms.contains("borer holes") || symptoms.contains("tunnels")) {
                pestName = "Sugarcane Borer";
                treatmentMethod = "Apply chlorantraniliprole insecticide";
                sprayRatePerSqm = 0.05;
                costPerUnit = 13.0;
            }
        } else if (cropType.contains("apple")) {
            if (symptoms.contains("moth larvae") || symptoms.contains("fruit damage")) {
                pestName = "Codling Moth";
                treatmentMethod = "Apply spinosad-based pesticide";
                sprayRatePerSqm = 0.04;
                costPerUnit = 15.0;
            }
        } else if (cropType.contains("grape")) {
            if (symptoms.contains("powdery mildew") || symptoms.contains("white coating")) {
                pestName = "Powdery Mildew";
                treatmentMethod = "Apply sulfur-based fungicide";
                sprayRatePerSqm = 0.07;
                costPerUnit = 10.0;
            }
        }

        double sprayAmount = area * sprayRatePerSqm;
        double totalCost = sprayAmount * costPerUnit;

        return new PestOutput(pestName, treatmentMethod, sprayAmount, totalCost);
    }

    // Method to provide crop types for dropdown
    public String[] getCropTypes() {
        return new String[] {
                "Tomato", "Wheat", "Corn", "Rice", "Potato",
                "Soybean", "Cotton", "Sugarcane", "Apple", "Grape"
        };
    }
}