package com.parking.parkingapi.model.order.response;

import com.parking.parkingapi.model.pricing.Price;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CheckOutResponse {

  private OrderResponse orderDetails;

  private ZonedDateTime checkout;

  private Price amountToPay;
}
