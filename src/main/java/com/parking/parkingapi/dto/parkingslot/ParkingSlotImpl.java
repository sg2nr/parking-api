package com.parking.parkingapi.dto.parkingslot;

/**
 * Implementation of ParkingSlot.
 */
public class ParkingSlotImpl extends ParkingSlot {

  public ParkingSlotImpl(long number, ParkingSlotService parkingSlotService) {
    super(number, parkingSlotService);
  }
}
