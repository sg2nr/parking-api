package com.parking.parkingapi.model.parkingslot;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.vehicle.Vehicle;

/**
 * It represents a parking slot and provides method to allow a vehicle
 * to use it or not.
 */
public class ParkingSlot implements ParkingApiDO {

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
   * @return <code>true</code>: the slot is available, <code>false</code> otherwise.
   */
  public boolean isFree() {
    return vehicle == null;
  }
}
