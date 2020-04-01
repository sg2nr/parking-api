package com.parking.parkingapi.dao;

import com.parking.parkingapi.model.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access Object for Vehicles.
 */
@Repository
public interface VehicleDao extends JpaRepository<VehicleEntity, Long> {

  Optional<VehicleEntity> findByPlate(String plate);
}
