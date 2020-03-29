package com.parking.parkingapi.dto.parkingslot;

import com.parking.parkingapi.dto.car.TwentyKwElectricalCar;

import java.util.Optional;

/**
 * Implementation of the operations can be performed on parking slot having a 20 KW power supply.
 */
public class TwentyKwPowerSupplyService implements ParkingSlotService<TwentyKwElectricalCar> {

  private TwentyKwElectricalCar twentyKwElectricalCar;

  @Override
  public void insert(TwentyKwElectricalCar vehicle) {
    this.twentyKwElectricalCar = vehicle;
  }

  @Override
  public void removeVehicle() {
    this.twentyKwElectricalCar = null;
  }

  @Override
  public Optional<TwentyKwElectricalCar> getParkedVehicle() {
    return Optional.ofNullable(twentyKwElectricalCar);
  }
}
