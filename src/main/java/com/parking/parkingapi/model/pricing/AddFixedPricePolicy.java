package com.parking.parkingapi.model.pricing;

import java.time.ZonedDateTime;
import java.util.function.BiFunction;

/**
 * AddFixedPricePolicy is a composite pricing policy that uses addition
 * to combine the amounts of the applied policies.
 */
public class AddFixedPricePolicy extends CompositePricingPolicy {

  private long fixedPrice;

  public AddFixedPricePolicy(PricingPolicy basePolicy, long fixedPrice, Currency currency) {
    super(basePolicy, currency);
    this.fixedPrice = fixedPrice;
  }

  public long getFixedPrice() {
    return fixedPrice;
  }

  public void setFixedPrice(long fixedPrice) {
    this.fixedPrice = fixedPrice;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  Price calculateCompositeSpecificAmount(ZonedDateTime dateTimeIn, ZonedDateTime dataTimeOut) {
    return Price.PriceBuilder.builder()
        .withAmount(fixedPrice)
        .withCurrency(currency)
        .build();
  }

  @Override
  BiFunction<Price, Price, Price> combineFunction() {
    return ((price1, price2) -> Price.PriceBuilder.builder()
        .withAmount(price1.getAmount() + price2.getAmount())
        .withCurrency(price2.getCurrency())
        .build());
  }
}
