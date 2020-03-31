package com.parking.parkingapi.model.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumerator indicating the possible services offered by a Parking.
 * It is used by the Data Transfer Objects.
 */
public enum ParkingServiceType {

  STANDARD_PARKING("StandardParking"),
  TWENTY_KW_POWER_SUPPLY("20KwPowerSupply"),
  FIFTY_KW_POWER_SUPPLY("50KwPowerSupply");

  private String description;

  ParkingServiceType(String description) {
    this.description = description;
  }

  @JsonValue
  public String getDescription() {
    return description;
  }
}
