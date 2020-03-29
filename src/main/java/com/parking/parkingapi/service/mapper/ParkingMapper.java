package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.dto.Parking;
import com.parking.parkingapi.entities.ParkingEntity;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Class for Parking - ParkingEntity mapping.
 */
@Component
public class ParkingMapper implements Mapper<Parking, ParkingEntity> {

  public Parking mapToDto(@NotNull ParkingEntity parkingEntity) {
    long parkingEntityId = parkingEntity.getId();
    String parkingEntityName = parkingEntity.getName();
    String parkingEntityAddress = parkingEntity.getAddress();
    String parkingEntityCity = parkingEntity.getCity();

    return new Parking(parkingEntityId, parkingEntityName, parkingEntityAddress, parkingEntityCity);
  }

  @Override
  public ParkingEntity mapToEntity(@NotNull Parking dto) {
    long parkingId = dto.getId();
    String parkingName = dto.getName();
    String parkingAddress = dto.getAddress();
    String parkingCity = dto.getCity();

    return new ParkingEntity(parkingId, parkingName, parkingAddress, parkingCity);
  }
}
