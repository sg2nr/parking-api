package com.parking.parkingapi.model.vehicle;

import com.parking.parkingapi.model.common.ParkingServiceType;

/**
 * Implementation of a car.
 */
public class Car extends Vehicle {

  public Car(String plate, ParkingServiceType requestedService) {
    super(plate, requestedService);
  }
}
