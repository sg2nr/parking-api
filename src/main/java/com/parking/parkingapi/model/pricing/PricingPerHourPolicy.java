package com.parking.parkingapi.model.pricing;

import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * PricingPerHourPolicy computes the final amount to pay based on the hours spent in the parking.
 */
public class PricingPerHourPolicy implements PricingPolicy {

  private int pricePerHour;

  private Currency currency;

  public PricingPerHourPolicy(int pricePerHour, Currency currency) {
    this.pricePerHour = pricePerHour;
    this.currency = currency;
  }

  @Override
  public Price calculateAmount(ZonedDateTime dateTimeIn, ZonedDateTime dateTimeOut)
      throws TemporaryDataInconsistencyException {

    if (dateTimeOut.isBefore(dateTimeIn)) {
      throw new TemporaryDataInconsistencyException("The date time of exit is before the entrance!");
    }

    long hoursSpent = ChronoUnit.HOURS.between(dateTimeIn, dateTimeOut);

    long totalAmount = Math.multiplyExact(hoursSpent, pricePerHour);

    return Price.PriceBuilder.builder()
        .withAmount(totalAmount)
        .withCurrency(getPolicyCurrency())
        .build();
  }

  public int getPricePerHour() {
    return pricePerHour;
  }

  public void setPricePerHour(int pricePerHour) {
    this.pricePerHour = pricePerHour;
  }

  @Override
  public Currency getPolicyCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
