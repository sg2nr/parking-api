package com.parking.parkingapi.model.car;

import com.parking.parkingapi.model.common.ParkingServiceType;

/**
 * Implementation of a car.
 */
public class Car extends Vehicle {

  public Car(String plate, ParkingServiceType requestedService) {
    super(plate, requestedService);
  }
}
