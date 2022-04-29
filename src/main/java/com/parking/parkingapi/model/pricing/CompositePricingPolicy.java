package com.parking.parkingapi.model.pricing;

import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Abstraction a composite pricing policy.
 * A composite pricing policy that combines its own policy conditions on top of the
 * ones of another policy called Base Policy.
 */
public abstract class CompositePricingPolicy implements PricingPolicy {

  @Getter
  PricingPolicy basePolicy;

  Currency currency;

  Long id;

  protected CompositePricingPolicy() {
  }

  protected CompositePricingPolicy(PricingPolicy basePolicy) {
    this.basePolicy = basePolicy;
    currency = basePolicy.getPolicyCurrency();
  }

  protected CompositePricingPolicy(PricingPolicy basePolicy, Currency currency, Long id) {
    if (!Objects.equals(basePolicy.getPolicyCurrency(), currency)) {
      throw new IllegalArgumentException("All pricing policies must use the same currency");
    }
    this.basePolicy = basePolicy;
    this.currency = currency;
    this.id = id;
  }

  @Override
  public Currency getPolicyCurrency() {
    return this.currency;
  }

  public Long getId() {
    return id;
  }

  /**
   * It calculates the final price based on the combination of the Base policy and specific policy.
   *
   * @param dateTimeIn  timestamp of entrance
   * @param dateTimeOut timestamp of exit
   * @return The final price to pay.
   */
  public Price calculateAmount(ZonedDateTime dateTimeIn, ZonedDateTime dateTimeOut)
      throws TemporaryDataInconsistencyException {

    Price basePrice = basePolicy.calculateAmount(dateTimeIn, dateTimeOut);
    Price compositeSpecificPrice = calculateCompositeSpecificAmount(dateTimeIn, dateTimeOut);

    return combineFunction().apply(basePrice, compositeSpecificPrice);
  }

  /**
   * It calculates the price based only on the conditions of specific policy.
   *
   * @param dateTimeIn  timestamp of entrance
   * @param dataTimeOut timestamp of exit
   * @return
   */
  abstract Price calculateCompositeSpecificAmount(ZonedDateTime dateTimeIn, ZonedDateTime dataTimeOut);

  /**
   * This method provides the function used to combine the amount of two policies.
   *
   * @return The function that it should be applied to get the combined amount.
   */
  abstract BiFunction<Price, Price, Price> combineFunction();
}
