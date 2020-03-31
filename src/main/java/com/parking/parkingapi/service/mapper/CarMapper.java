package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.car.Car;
import com.parking.parkingapi.model.car.Vehicle;
import com.parking.parkingapi.model.entities.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for Car objects.
 */
@Component
public class CarMapper {

  private ParkingServiceTypeConverter parkingServiceTypeConverter;

  @Autowired
  public CarMapper(ParkingServiceTypeConverter parkingServiceTypeConverter) {
    this.parkingServiceTypeConverter = parkingServiceTypeConverter;
  }

  public Vehicle mapToDto(VehicleEntity vehicleEntity) {
    return new Car(vehicleEntity.getPlate(),
        parkingServiceTypeConverter.convertToParkingService(vehicleEntity.getEngineType()));
  }

}
