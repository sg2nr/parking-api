package com.parking.parkingapi.model.order.response;

import com.parking.parkingapi.model.pricing.Price;

import java.time.ZonedDateTime;

public class CheckOutResponse {

  private OrderResponse orderDetails;

  private ZonedDateTime checkout;

  private Price amountToPay;

  public CheckOutResponse() {
  }

  public OrderResponse getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(OrderResponse orderDetails) {
    this.orderDetails = orderDetails;
  }

  public ZonedDateTime getCheckout() {
    return checkout;
  }

  public void setCheckout(ZonedDateTime checkout) {
    this.checkout = checkout;
  }

  public Price getAmountToPay() {
    return amountToPay;
  }

  public void setAmountToPay(Price amountToPay) {
    this.amountToPay = amountToPay;
  }
}
