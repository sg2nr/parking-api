package com.parking.parkingapi.dao;

import com.parking.parkingapi.entities.ParkingLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for Parking logs.
 */
@Repository
public interface ParkingLogsDao extends JpaRepository<ParkingLogEntity, Long> {

}
