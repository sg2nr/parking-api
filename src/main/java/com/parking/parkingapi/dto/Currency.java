package com.parking.parkingapi.dto;

/**
 * Data Transfer Object representing Currency
 */
public class Currency implements ParkingApiDto {

  private String currencyCode;

  private int decimalPlaces;

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public void setDecimalPlaces(int decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
  }
}
