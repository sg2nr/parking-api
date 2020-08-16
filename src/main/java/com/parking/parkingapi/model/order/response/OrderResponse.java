package com.parking.parkingapi.model.order.response;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderResponse {

  private long orderId;

  private ZonedDateTime checkin;

  private String carPlate;

  private long parkingId;

  private long slotNumber;
}
