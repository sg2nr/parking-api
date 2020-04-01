package com.parking.parkingapi.model.vehicle;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.common.ParkingServiceType;

/**
 * Abstraction of a vehicle.
 */
public abstract class Vehicle implements ParkingApiDO {

  private String plate;

  private ParkingServiceType requestedService;

  public Vehicle(String plate, ParkingServiceType requestedService) {
    this.plate = plate;
    this.requestedService = requestedService;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public ParkingServiceType getRequestedService() {
    return requestedService;
  }

  public void setRequestedService(ParkingServiceType requestedService) {
    this.requestedService = requestedService;
  }
}
