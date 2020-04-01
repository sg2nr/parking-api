package com.parking.parkingapi.dao;

import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for Parking logs.
 */
@Repository
public interface ParkingLogsDao extends JpaRepository<ParkingLogEntity, Long> {

  List<ParkingLogEntity> findByVehicleEntity(VehicleEntity vehicleEntity);

  List<ParkingLogEntity> findByParkingSlotEntityIn(List<ParkingSlotEntity> parkingSlotEntities);
}
