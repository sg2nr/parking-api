package com.parking.parkingapi.entities;

/**
 * Enumerator indicating the vehicle type.
 * It is used by Entity POJOs.
 */
public enum VehiclePoweredType {
  GASOLINE("gasoline"),
  ELECTRICAL_TWENTY_KW("20kw"),
  ELECTRICAL_FIFTY_KW("50kw");

  private String type;

  VehiclePoweredType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
