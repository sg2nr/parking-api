package com.parking.parkingapi.model.order.response;

import java.time.ZonedDateTime;

public final class OrderResponseBuilder {

  private long orderId;

  private ZonedDateTime checkin;

  private String carPlate;

  private long parkingId;

  private long slotNumber;

  private OrderResponseBuilder() {
  }

  public static OrderResponseBuilder builder() {
    return new OrderResponseBuilder();
  }

  public OrderResponseBuilder withOrderId(long orderId) {
    this.orderId = orderId;
    return this;
  }

  public OrderResponseBuilder withCheckin(ZonedDateTime checkin) {
    this.checkin = checkin;
    return this;
  }

  public OrderResponseBuilder withCarPlate(String carPlate) {
    this.carPlate = carPlate;
    return this;
  }

  public OrderResponseBuilder withParkingId(long parkingId) {
    this.parkingId = parkingId;
    return this;
  }

  public OrderResponseBuilder withSlotNumber(long slotNumber) {
    this.slotNumber = slotNumber;
    return this;
  }

  public OrderResponse build() {
    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setOrderId(orderId);
    orderResponse.setCheckin(checkin);
    orderResponse.setCarPlate(carPlate);
    orderResponse.setParkingId(parkingId);
    orderResponse.setSlotNumber(slotNumber);
    return orderResponse;
  }
}
