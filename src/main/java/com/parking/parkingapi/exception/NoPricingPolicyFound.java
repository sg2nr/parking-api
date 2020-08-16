package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Order cannot be processed.")
public class NoPricingPolicyFound extends ParkingApiException {

  public NoPricingPolicyFound(String message) {
    super(message);
  }
}
