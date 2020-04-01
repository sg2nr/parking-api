package com.parking.parkingapi.model.car;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.model.pricing.Price;

import java.time.ZonedDateTime;

/**
 * DTO to support the check-in and check-out of a
 * vehicle.
 */
public class OrderDto implements ParkingApiDto {

  private Vehicle vehicle;

  private long parkingId;

  private long slotNumber;

  private ZonedDateTime timeStampIn;

  private ZonedDateTime timeStampOut;

  private Price amount;

  private long orderId;

  public OrderDto() {
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
