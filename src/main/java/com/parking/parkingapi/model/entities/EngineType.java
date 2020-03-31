package com.parking.parkingapi.model.entities;

/**
 * Enumerator indicating the engine type
 * of a vehicle.
 *
 */
public enum EngineType {
  GASOLINE("gasoline"),
  ELECTRICAL_TWENTY_KW("20kw"),
  ELECTRICAL_FIFTY_KW("50kw");

  private String type;

  EngineType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
