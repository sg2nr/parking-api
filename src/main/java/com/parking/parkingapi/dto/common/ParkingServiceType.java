package com.parking.parkingapi.dto.common;

/**
 * Enumerator indicating the possible services offered by a Parking.
 * It is used by the Data Transfer Objects.
 */
public enum ParkingServiceType {

  STANDARD_PARKING("Standard parking"),
  TWENTY_KW_POWER_SUPPLY("20 KW power supply"),
  FIFTY_KW_POWER_SUPPLY("50KW power supply");

  private String description;

  ParkingServiceType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
