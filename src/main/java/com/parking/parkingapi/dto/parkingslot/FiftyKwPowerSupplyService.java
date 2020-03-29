package com.parking.parkingapi.dto.parkingslot;

import com.parking.parkingapi.dto.car.FiftyKwElectricalCar;

import java.util.Optional;

/**
 * Implementation of the operations can be performed on parking slot having a 50 KW power supply.
 */
public class FiftyKwPowerSupplyService implements ParkingSlotService<FiftyKwElectricalCar> {

  private FiftyKwElectricalCar fiftyKwElectricalCar;

  @Override
  public void insert(FiftyKwElectricalCar vehicle) {
    this.fiftyKwElectricalCar = vehicle;
  }

  @Override
  public void removeVehicle() {
    this.fiftyKwElectricalCar = null;
  }

  @Override
  public Optional<FiftyKwElectricalCar> getParkedVehicle() {
    return Optional.ofNullable(fiftyKwElectricalCar);
  }
}
