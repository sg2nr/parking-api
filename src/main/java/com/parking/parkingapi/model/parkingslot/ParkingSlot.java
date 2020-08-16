package com.parking.parkingapi.model.parkingslot;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

/**
 * It represents a parking slot and provides method to allow a vehicle
 * to use it or not.
 */
@Getter
@Setter
public class ParkingSlot implements ParkingApiDO {

  private long id;

  private long number;

  private ParkingServiceType offeredService;

  private Vehicle vehicle;

  /**
   * It returns the status of the parking slot.
   *
   * @return <code>true</code>: the slot is available, <code>false</code> otherwise.
   */
  public boolean isFree() {
    return vehicle == null;
  }
}
