package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad JSON request")
public class BadJsonRequest extends Exception {

  public BadJsonRequest(String message) {
    super(message);
  }
}
