package com.parking.parkingapi.dto.parking;

/**
 * This class represents the status of a Parking lot.
 * It indicates total and free parking slots.
 */
public class ParkingStatus {

  private long totalSlots;

  private long freeSlots;

  public ParkingStatus() {
  }

  public ParkingStatus(long totalSlots, long freeSlots) {
    this.totalSlots = totalSlots;
    this.freeSlots = freeSlots;
  }

  public long getTotalSlots() {
    return totalSlots;
  }

  public void setTotalSlots(long totalSlots) {
    this.totalSlots = totalSlots;
  }

  public long getFreeSlots() {
    return freeSlots;
  }

  public void setFreeSlots(long freeSlots) {
    this.freeSlots = freeSlots;
  }
}
