package com.parking.parkingapi.model.order.response;

import com.parking.parkingapi.model.pricing.Price;

import java.time.ZonedDateTime;

public final class CheckOutResponseBuilder {

  private OrderResponse orderDetails;

  private ZonedDateTime checkout;

  private Price amountToPay;

  private CheckOutResponseBuilder() {
  }

  public static CheckOutResponseBuilder builder() {
    return new CheckOutResponseBuilder();
  }

  public CheckOutResponseBuilder withOrderDetails(OrderResponse orderDetails) {
    this.orderDetails = orderDetails;
    return this;
  }

  public CheckOutResponseBuilder withCheckout(ZonedDateTime checkout) {
    this.checkout = checkout;
    return this;
  }

  public CheckOutResponseBuilder withAmountToPay(Price amountToPay) {
    this.amountToPay = amountToPay;
    return this;
  }

  public CheckOutResponse build() {
    CheckOutResponse checkOutResponse = new CheckOutResponse();
    checkOutResponse.setOrderDetails(orderDetails);
    checkOutResponse.setCheckout(checkout);
    checkOutResponse.setAmountToPay(amountToPay);
    return checkOutResponse;
  }
}
