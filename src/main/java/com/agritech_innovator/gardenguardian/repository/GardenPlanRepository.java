package com.agritech_innovator.gardenguardian.repository;

import com.agritech_innovator.gardenguardian.model.GardenPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GardenPlanRepository extends JpaRepository<GardenPlan, UUID> {
}
