package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.vehicle.Car;
import com.parking.parkingapi.model.vehicle.Vehicle;
import com.parking.parkingapi.model.entities.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Mapper for Car objects.
 */
@Component
public class VehicleMapper {

  private final ParkingServiceTypeConverter parkingServiceTypeConverter;

  @Autowired
  public VehicleMapper(ParkingServiceTypeConverter parkingServiceTypeConverter) {
    this.parkingServiceTypeConverter = parkingServiceTypeConverter;
  }

  public Vehicle mapToVehicle(VehicleEntity vehicleEntity) {
    return new Car(vehicleEntity.getPlate(),
        parkingServiceTypeConverter.convertToParkingService(vehicleEntity.getEngineType()));
  }

  public VehicleEntity mapToEntity(Vehicle vehicle) {
    VehicleEntity vehicleEntity = new VehicleEntity(vehicle.getPlate(),
        parkingServiceTypeConverter.convertToVehiclePoweredType(vehicle.getRequestedService()));
    vehicleEntity.setParkingLogEntities(new ArrayList<>());
    return vehicleEntity;
  }

}
