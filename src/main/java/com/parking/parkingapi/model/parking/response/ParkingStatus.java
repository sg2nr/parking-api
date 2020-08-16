package com.parking.parkingapi.model.parking.response;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the status of a Parking lot.
 * It indicates total and free parking slots.
 */
@Getter
@Setter
public class ParkingStatus {

  private long totalSlots;

  private long freeSlots;

  public ParkingStatus() {
  }

  public ParkingStatus(long totalSlots, long freeSlots) {
    this.totalSlots = totalSlots;
    this.freeSlots = freeSlots;
  }
}
