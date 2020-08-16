package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Order not found.")
public class OrderNotFoundException extends ParkingApiException {

  public OrderNotFoundException(String message) {
    super(message);
  }
}
