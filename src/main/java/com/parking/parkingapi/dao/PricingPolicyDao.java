package com.parking.parkingapi.dao;

import com.parking.parkingapi.model.entities.PricingPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPolicyDao extends JpaRepository<PricingPolicyEntity, Long> {

}
