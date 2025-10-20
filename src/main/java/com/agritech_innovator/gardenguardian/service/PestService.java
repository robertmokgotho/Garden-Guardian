package com.agritech_innovator.gardenguardian.service;

import com.agritech_innovator.gardenguardian.model.PestInput;
import com.agritech_innovator.gardenguardian.model.PestOutput;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PestService {

    // Map of crop types to their possible symptoms
    private final Map<String, String[]> cropSymptomsMap = new HashMap<>();

    public PestService() {
        // Initialize crop-to-symptoms mapping
        cropSymptomsMap.put("Tomato", new String[]{"Wilting", "Yellow Leaves", "Spots", "Blight"});
        cropSymptomsMap.put("Wheat", new String[]{"Rust", "Brown Spots", "Aphids", "Sticky Leaves"});
        cropSymptomsMap.put("Corn", new String[]{"Holes in Leaves", "Chewed", "Yellowing", "Stunted Growth"});
        cropSymptomsMap.put("Rice", new String[]{"Yellowing", "Stunted Growth", "Brown Spots", "Leaf Blast"});
        cropSymptomsMap.put("Potato", new String[]{"Black Spots", "Scabs", "Wilting", "Tuber Damage"});
        cropSymptomsMap.put("Soybean", new String[]{"Defoliation", "Chewed Leaves", "Yellowing", "Pod Damage"});
        cropSymptomsMap.put("Cotton", new String[]{"Boll Damage", "Worms", "Yellowing", "Leaf Curl"});
        cropSymptomsMap.put("Sugarcane", new String[]{"Borer Holes", "Tunnels", "Yellowing", "Stunted Growth"});
        cropSymptomsMap.put("Apple", new String[]{"Moth Larvae", "Fruit Damage", "Leaf Spots", "Powdery Mildew"});
        cropSymptomsMap.put("Grape", new String[]{"Powdery Mildew", "White Coating", "Leaf Spots", "Berry Rot"});
    }

    public PestOutput identifyAndTreat(PestInput input) {
        String cropType = input.getCropType().toLowerCase();
        String symptoms = input.getSymptoms().toLowerCase();
        double area = input.getAffectedArea();

        String pestName = "Unknown";
        String treatmentMethod = "Consult an expert";
        double sprayRatePerSqm = 0.0; // liters per sqm
        double costPerUnit = 0.0; // cost per liter

        // Rule-based logic for pest identification
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
            } else if (symptoms.contains("yellowing") || symptoms.contains("stunted")) {
                pestName = "Nutrient Deficiency";
                treatmentMethod = "Apply foliar fertilizer";
                sprayRatePerSqm = 0.05;
                costPerUnit = 7.0;
            }
        } else if (cropType.contains("rice")) {
            if (symptoms.contains("yellowing") || symptoms.contains("stunted")) {
                pestName = "Rice Blast";
                treatmentMethod = "Apply tricyclazole fungicide";
                sprayRatePerSqm = 0.07;
                costPerUnit = 11.0;
            } else if (symptoms.contains("brown spots") || symptoms.contains("leaf blast")) {
                pestName = "Leaf Blast";
                treatmentMethod = "Apply propiconazole fungicide";
                sprayRatePerSqm = 0.06;
                costPerUnit = 10.0;
            }
        } else if (cropType.contains("potato")) {
            if (symptoms.contains("black spots") || symptoms.contains("scabs")) {
                pestName = "Potato Scab";
                treatmentMethod = "Use resistant varieties and sulfur-based treatment";
                sprayRatePerSqm = 0.05;
                costPerUnit = 10.0;
            } else if (symptoms.contains("wilting") || symptoms.contains("tuber damage")) {
                pestName = "Potato Blight";
                treatmentMethod = "Apply mancozeb fungicide";
                sprayRatePerSqm = 0.07;
                costPerUnit = 12.0;
            }
        } else if (cropType.contains("soybean")) {
            if (symptoms.contains("defoliation") || symptoms.contains("chewed leaves")) {
                pestName = "Soybean Looper";
                treatmentMethod = "Apply spinosad insecticide";
                sprayRatePerSqm = 0.06;
                costPerUnit = 12.0;
            } else if (symptoms.contains("yellowing") || symptoms.contains("pod damage")) {
                pestName = "Soybean Aphid";
                treatmentMethod = "Apply imidacloprid insecticide";
                sprayRatePerSqm = 0.04;
                costPerUnit = 9.0;
            }
        } else if (cropType.contains("cotton")) {
            if (symptoms.contains("boll damage") || symptoms.contains("worms")) {
                pestName = "Bollworm";
                treatmentMethod = "Apply pyrethroid insecticide";
                sprayRatePerSqm = 0.08;
                costPerUnit = 14.0;
            } else if (symptoms.contains("yellowing") || symptoms.contains("leaf curl")) {
                pestName = "Cotton Leaf Curl Virus";
                treatmentMethod = "Remove infected plants and control whiteflies";
                sprayRatePerSqm = 0.05;
                costPerUnit = 11.0;
            }
        } else if (cropType.contains("sugarcane")) {
            if (symptoms.contains("borer holes") || symptoms.contains("tunnels")) {
                pestName = "Sugarcane Borer";
                treatmentMethod = "Apply chlorantraniliprole insecticide";
                sprayRatePerSqm = 0.05;
                costPerUnit = 13.0;
            } else if (symptoms.contains("yellowing") || symptoms.contains("stunted")) {
                pestName = "Sugarcane Mosaic Virus";
                treatmentMethod = "Remove infected plants and use resistant varieties";
                sprayRatePerSqm = 0.0; // No spray
                costPerUnit = 0.0;
            }
        } else if (cropType.contains("apple")) {
            if (symptoms.contains("moth larvae") || symptoms.contains("fruit damage")) {
                pestName = "Codling Moth";
                treatmentMethod = "Apply spinosad-based pesticide";
                sprayRatePerSqm = 0.04;
                costPerUnit = 15.0;
            } else if (symptoms.contains("leaf spots") || symptoms.contains("powdery mildew")) {
                pestName = "Apple Scab";
                treatmentMethod = "Apply myclobutanil fungicide";
                sprayRatePerSqm = 0.06;
                costPerUnit = 12.0;
            }
        } else if (cropType.contains("grape")) {
            if (symptoms.contains("powdery mildew") || symptoms.contains("white coating")) {
                pestName = "Powdery Mildew";
                treatmentMethod = "Apply sulfur-based fungicide";
                sprayRatePerSqm = 0.07;
                costPerUnit = 10.0;
            } else if (symptoms.contains("leaf spots") || symptoms.contains("berry rot")) {
                pestName = "Downy Mildew";
                treatmentMethod = "Apply copper-based fungicide";
                sprayRatePerSqm = 0.08;
                costPerUnit = 11.0;
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

    // Method to provide symptoms for a given crop
    public String[] getSymptomsForCrop(String cropType) {
        return cropSymptomsMap.getOrDefault(cropType, new String[]{});
    }
}