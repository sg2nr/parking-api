package com.parking.parkingapi.model.parkingslot;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.model.car.Vehicle;
import com.parking.parkingapi.model.common.ParkingServiceType;

import java.util.Objects;

/**
 * It represents a parking slot and provides method to allow a vehicle
 * to use it or not.
 */
public class ParkingSlot implements ParkingApiDto {

  private long id;

  private long number;

  private ParkingServiceType offeredService;

  private Vehicle vehicle;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }

  public ParkingServiceType getOfferedService() {
    return offeredService;
  }

  public void setOfferedService(ParkingServiceType offeredService) {
    this.offeredService = offeredService;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  /**
   * It returns the status of the parking slot.
   *
   * @return
   *    <code>true</code>: the slot is available, <code>false</code> otherwise.
   */
  public boolean isFree() {
    return vehicle == null;
  }

  /**
   * The vehicle in input is parked in the slot.
   *
   * @param vehicle
   * @return
   */
  /**
   * The vehicle in input is parked in the slot, if eligible.
   *
   * @param vehicle
   * @return
   *    It returns <code>true</code> if check-in is successful.
   */
  public boolean checkIn(Vehicle vehicle) {
    if (Objects.equals(vehicle.getRequestedService(), offeredService)) {
      this.vehicle = vehicle;
      return true;
    }
    return false;
  }

  /**
   * It removes the parked vehicle.
   *
   * @return
   *    It returns <code>true</code> if check-out was successful.
   */
  public boolean checkOut() {
    if (isFree()) {
      return false;
    }
    vehicle = null;
    return true;
  }
}
