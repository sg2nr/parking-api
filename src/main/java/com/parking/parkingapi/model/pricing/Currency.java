package com.parking.parkingapi.model.pricing;

import lombok.Getter;

import java.util.Objects;

/**
 * The data model representing a Currency
 */
@Getter
public class Currency {

  private String currencyCode;

  private int decimalPlaces;

  public Currency() {
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
