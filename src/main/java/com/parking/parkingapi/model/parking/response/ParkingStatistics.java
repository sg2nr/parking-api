package com.parking.parkingapi.model.parking.response;

import com.parking.parkingapi.model.common.ParkingServiceType;

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
public class ParkingStatistics {

  private ParkingStatus parkingStatus;

  private Map<ParkingServiceType, ParkingStatus> statusPerType;

  public ParkingStatistics() {
  }

  public ParkingStatistics(ParkingStatus parkingStatus, Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.parkingStatus = parkingStatus;
    this.statusPerType = statusPerType;
  }

  public ParkingStatus getParkingStatus() {
    return parkingStatus;
  }

  public void setParkingStatus(ParkingStatus parkingStatus) {
    this.parkingStatus = parkingStatus;
  }

  public Map<ParkingServiceType, ParkingStatus> getStatusPerType() {
    return statusPerType;
  }

  public void setStatusPerType(Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.statusPerType = statusPerType;
  }
}
