package com.parking.parkingapi.dto.car;

import com.parking.parkingapi.dto.ParkingApiDto;

public abstract class Vehicle implements ParkingApiDto {

  private String plate;

  public Vehicle(String plate) {
    this.plate = plate;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }
}
