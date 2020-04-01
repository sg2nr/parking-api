package com.parking.parkingapi.model.pricing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * PricingPerHourPolicy computes the final amount to pay based on the hours spent in the parking.
 */
public class PricingPerHourPolicy implements PricingPolicy {

  @JsonInclude(Include.NON_NULL)
  private Long id;

  private int pricePerHour;

  private Currency currency;

  public PricingPerHourPolicy(int pricePerHour, Currency currency, Long id) {
    this.pricePerHour = pricePerHour;
    this.currency = currency;
    this.id = id;
  }

  @Override
  public Price calculateAmount(ZonedDateTime dateTimeIn, ZonedDateTime dateTimeOut)
      throws TemporaryDataInconsistencyException {

    if (Objects.isNull(dateTimeIn) || Objects.isNull(dateTimeOut) || dateTimeOut.isBefore(dateTimeIn)) {
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

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
