package com.parking.parkingapi.model.order;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * The data model to support the check-in and check-out of a
 * vehicle.
 */
@Getter @Setter
public class Order implements ParkingApiDO {

  private Vehicle vehicle;

  private long parkingId;

  private long slotNumber;

  private ZonedDateTime timeStampIn;

  private ZonedDateTime timeStampOut;

  private Price amount;

  private long orderId;
}
