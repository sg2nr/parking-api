package com.parking.parkingapi.model.pricing;

import java.util.Objects;

/**
 * Data Transfer Object representing Currency
 */
public class Currency {

  private final String currencyCode;

  private final int decimalPlaces;

  public String getCurrencyCode() {
    return currencyCode;
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public Currency(String currencyCode, int decimalPlaces) {
    this.currencyCode = currencyCode;
    this.decimalPlaces = decimalPlaces;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Currency currency = (Currency) o;
    return decimalPlaces == currency.decimalPlaces &&
        currencyCode.equals(currency.currencyCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currencyCode, decimalPlaces);
  }
}
