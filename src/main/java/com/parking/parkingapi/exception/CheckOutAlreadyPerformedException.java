package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This order has already been checked-out.")
public class CheckOutAlreadyPerformedException extends ParkingApiException {

  public CheckOutAlreadyPerformedException(String message) {
    super(message);
  }
}
