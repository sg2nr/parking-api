package com.parking.parkingapi.model.order;

import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.vehicle.Vehicle;

import java.time.ZonedDateTime;

public final class OrderBuilder {

  private Vehicle vehicle;

  private long parkingId;

  private long slotNumber;

  private ZonedDateTime timeStampIn;

  private ZonedDateTime timeStampOut;

  private Price amount;

  private long orderId;

  private OrderBuilder() {
  }

  public static OrderBuilder builder() {
    return new OrderBuilder();
  }

  public OrderBuilder withVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
    return this;
  }

  public OrderBuilder withParkingId(long parkingId) {
    this.parkingId = parkingId;
    return this;
  }

  public OrderBuilder withSlotNumber(long slotNumber) {
    this.slotNumber = slotNumber;
    return this;
  }

  public OrderBuilder withTimeStampIn(ZonedDateTime timeStampIn) {
    this.timeStampIn = timeStampIn;
    return this;
  }

  public OrderBuilder withTimeStampOut(ZonedDateTime timeStampOut) {
    this.timeStampOut = timeStampOut;
    return this;
  }

  public OrderBuilder withAmount(Price amount) {
    this.amount = amount;
    return this;
  }

  public OrderBuilder withOrderId(long orderId) {
    this.orderId = orderId;
    return this;
  }

  public Order build() {
    Order order = new Order();
    order.setVehicle(vehicle);
    order.setParkingId(parkingId);
    order.setSlotNumber(slotNumber);
    order.setTimeStampIn(timeStampIn);
    order.setTimeStampOut(timeStampOut);
    order.setAmount(amount);
    order.setOrderId(orderId);
    return order;
  }
}
