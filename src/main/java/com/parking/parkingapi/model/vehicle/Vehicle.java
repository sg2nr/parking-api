package com.parking.parkingapi.model.vehicle;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.common.ParkingServiceType;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstraction of a vehicle.
 */
@Getter
@Setter
public abstract class Vehicle implements ParkingApiDO {

  private String plate;

  private ParkingServiceType requestedService;

  public Vehicle(String plate, ParkingServiceType requestedService) {
    this.plate = plate;
    this.requestedService = requestedService;
  }
}
