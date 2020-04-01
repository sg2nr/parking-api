package com.parking.parkingapi.model.order.response;

import java.time.ZonedDateTime;

public class OrderResponse {

  private long orderId;

  private ZonedDateTime checkin;

  private String carPlate;

  private long parkingId;

  private long slotNumber;

  public OrderResponse() {
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public ZonedDateTime getCheckin() {
    return checkin;
  }

  public void setCheckin(ZonedDateTime checkin) {
    this.checkin = checkin;
  }

  public String getCarPlate() {
    return carPlate;
  }

  public void setCarPlate(String carPlate) {
    this.carPlate = carPlate;
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
}
