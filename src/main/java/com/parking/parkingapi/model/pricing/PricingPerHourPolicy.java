package com.parking.parkingapi.model.pricing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * PricingPerHourPolicy computes the final amount to pay based on the hours spent in the parking.
 */
public class PricingPerHourPolicy implements PricingPolicy {

  @Setter
  @JsonInclude(Include.NON_NULL)
  private Long id;

  @Getter
  @Setter
  private int pricePerHour;

  @Setter
  private Currency policyCurrency;

  public PricingPerHourPolicy() {
  }

  public PricingPerHourPolicy(int pricePerHour, Currency currency, Long id) {
    this.pricePerHour = pricePerHour;
    this.policyCurrency = currency;
    this.id = id;
  }

  @Override
  public Currency getPolicyCurrency() {
    return policyCurrency;
  }

  @Override
  public Long getId() {
    return id;
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
}
