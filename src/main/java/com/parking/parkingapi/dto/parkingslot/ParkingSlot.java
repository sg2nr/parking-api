package com.parking.parkingapi.dto.parkingslot;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.dto.car.Vehicle;

/**
 * Abstraction of a parking slot.
 */
public abstract class ParkingSlot implements ParkingApiDto {

  private long number;

  private ParkingSlotService<Vehicle> parkingSlotService;

  public ParkingSlot(long number, ParkingSlotService<Vehicle> parkingSlotService) {
    this.number = number;
    this.parkingSlotService = parkingSlotService;
  }

  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }

  public void setParkingSlotService(ParkingSlotService<Vehicle> parkingSlotService) {
    this.parkingSlotService = parkingSlotService;
  }

  /**
   * It returns the status of the parking slot.
   *
   * @return
   *    <code>true</code>: the slot is available, <code>false</code> otherwise.
   */
  public boolean isFree() {
    return !this.parkingSlotService.getParkedVehicle().isPresent();
  }

  /**
   * The vehicle in input is parked in the slot.
   *
   * @param vehicle
   * @return
   */
  public void checkIn(Vehicle vehicle) {
    this.parkingSlotService.insert(vehicle);
  }

  /**
   * It removes the parked vehicle.
   *
   * @return
   */
  public void checkOut() {
    this.parkingSlotService.removeVehicle();
  }
}
