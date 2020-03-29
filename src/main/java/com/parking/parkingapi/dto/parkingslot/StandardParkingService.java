package com.parking.parkingapi.dto.parkingslot;

import com.parking.parkingapi.dto.car.GasolineCar;

import java.util.Optional;

/**
 * Implementation of the operations can be performed on common parking slot.
 */
public class StandardParkingService implements ParkingSlotService<GasolineCar> {

  private GasolineCar gasolineCar;

  @Override
  public void insert(GasolineCar vehicle) {
    this.gasolineCar = vehicle;
  }

  @Override
  public void removeVehicle() {
    this.gasolineCar = null;
  }

  @Override
  public Optional<GasolineCar> getParkedVehicle() {
    return Optional.ofNullable(gasolineCar);
  }
}
