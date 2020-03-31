package com.parking.parkingapi.dao;

import com.parking.parkingapi.model.entities.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access Object for Parking.
 */
@Repository
public interface ParkingDao extends JpaRepository<ParkingEntity, Long> {

  Optional<ParkingEntity> findById(Long id);
}
