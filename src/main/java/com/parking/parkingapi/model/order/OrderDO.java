package com.parking.parkingapi.model.order;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.vehicle.Vehicle;

import java.time.ZonedDateTime;

/**
 * DTO to support the check-in and check-out of a
 * vehicle.
 */
public class OrderDO implements ParkingApiDO {

  private Vehicle vehicle;

  private long parkingId;

  private long slotNumber;

  private ZonedDateTime timeStampIn;

  private ZonedDateTime timeStampOut;

  private Price amount;

  private long orderId;

  public OrderDO() {
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public long getParkingId() {
    return parkingId;
  }

  public void setParkingId(long parkingId) {
    this.parkingId = parkingId;
  }

  public long getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(long slotNumber) {
    this.slotNumber = slotNumber;
  }

  public ZonedDateTime getTimeStampIn() {
    return timeStampIn;
  }

  public void setTimeStampIn(ZonedDateTime timeStampIn) {
    this.timeStampIn = timeStampIn;
  }

  public ZonedDateTime getTimeStampOut() {
    return timeStampOut;
  }

  public void setTimeStampOut(ZonedDateTime timeStampOut) {
    this.timeStampOut = timeStampOut;
  }

  public Price getAmount() {
    return amount;
  }

  public void setAmount(Price amount) {
    this.amount = amount;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }
}
