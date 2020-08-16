package com.parking.parkingapi.model.parking.response;

import com.parking.parkingapi.model.common.ParkingServiceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * This class is intended to store statistics about a Parking lot.
 * Information kept:
 * <ol>
 *   <li>Total number of slots;</li>
 *   <li>Number of free slots;</li>
 *   <li>Total and free slots per parking service type (e.g. standard parkings or
 *   parkings with power supply.</li>
 * </ol>
 */
@Getter
@Setter
public class ParkingStatistics {

  private ParkingStatus parkingStatus;

  private Map<ParkingServiceType, ParkingStatus> statusPerType;

  public ParkingStatistics() {
  }

  public ParkingStatistics(ParkingStatus parkingStatus, Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.parkingStatus = parkingStatus;
    this.statusPerType = statusPerType;
  }
}
