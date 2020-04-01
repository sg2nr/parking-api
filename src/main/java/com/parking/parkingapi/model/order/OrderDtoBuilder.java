package com.parking.parkingapi.model.order;

import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.vehicle.Vehicle;

import java.time.ZonedDateTime;

public final class OrderDtoBuilder {

  private Vehicle vehicle;

  private long parkingId;

  private long slotNumber;

  private ZonedDateTime timeStampIn;

  private ZonedDateTime timeStampOut;

  private Price amount;

  private long orderId;

  private OrderDtoBuilder() {
  }

  public static OrderDtoBuilder builder() {
    return new OrderDtoBuilder();
  }

  public OrderDtoBuilder withVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
    return this;
  }

  public OrderDtoBuilder withParkingId(long parkingId) {
    this.parkingId = parkingId;
    return this;
  }

  public OrderDtoBuilder withSlotNumber(long slotNumber) {
    this.slotNumber = slotNumber;
    return this;
  }

  public OrderDtoBuilder withTimeStampIn(ZonedDateTime timeStampIn) {
    this.timeStampIn = timeStampIn;
    return this;
  }

  public OrderDtoBuilder withTimeStampOut(ZonedDateTime timeStampOut) {
    this.timeStampOut = timeStampOut;
    return this;
  }

  public OrderDtoBuilder withAmount(Price amount) {
    this.amount = amount;
    return this;
  }

  public OrderDtoBuilder withOrderId(long orderId) {
    this.orderId = orderId;
    return this;
  }

  public OrderDto build() {
    OrderDto orderDto = new OrderDto();
    orderDto.setVehicle(vehicle);
    orderDto.setParkingId(parkingId);
    orderDto.setSlotNumber(slotNumber);
    orderDto.setTimeStampIn(timeStampIn);
    orderDto.setTimeStampOut(timeStampOut);
    orderDto.setAmount(amount);
    orderDto.setOrderId(orderId);
    return orderDto;
  }
}
