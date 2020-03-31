package com.parking.parkingapi.dao;

import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for Parking Slots.
 */
@Repository
public interface ParkingSlotDao extends JpaRepository<ParkingSlotEntity, Long> {

  List<ParkingSlotEntity> findByParkingEntity(ParkingEntity parkingEntity);
}
