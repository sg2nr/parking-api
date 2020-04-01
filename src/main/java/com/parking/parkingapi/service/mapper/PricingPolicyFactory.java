package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.entities.CurrencyEntity;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.PricingPolicyEntity;
import com.parking.parkingapi.model.pricing.AddFixedPricePolicy;
import com.parking.parkingapi.model.pricing.Currency;
import com.parking.parkingapi.model.pricing.PricingPerHourPolicy;
import com.parking.parkingapi.model.pricing.PricingPolicy;

import java.util.Objects;
import java.util.Optional;

/**
 * Factory class for PricingPolicy.
 */
public class PricingPolicyFactory {

  /**
   * It instantiates a PricingPolicy based on information in the input ParkingEntity.
   *
   * @param parkingEntity
   * @return
   *    An Optional contained the PricingPolicy. It can be empty if it was not possible
   *    to create a PricingPolicy.
   */
  public static Optional<PricingPolicy> createPricingPolicy(ParkingEntity parkingEntity) {
    if (Objects.isNull(parkingEntity) || Objects.isNull(parkingEntity.getPricingPolicyEntity())) {
      return Optional.empty();
    }
    PricingPolicyEntity pricingPolicyEntity = parkingEntity.getPricingPolicyEntity();
    CurrencyEntity currencyEntity = pricingPolicyEntity.getCurrencyEntity();
    Currency currency = new Currency(currencyEntity.getCode(), currencyEntity.getDecimalPlaces());

    int unitPrice = pricingPolicyEntity.getUnitPriceUnscaled();
    PricingPolicy pricePerHourPolicy = new PricingPerHourPolicy(unitPrice, currency);

    if (Objects.nonNull(pricingPolicyEntity.getFixedPriceUnscaled())) {
      int fixedPrice = pricingPolicyEntity.getFixedPriceUnscaled();
      return Optional.of(new AddFixedPricePolicy(pricePerHourPolicy, fixedPrice, currency));
    }
    return Optional.of(pricePerHourPolicy);
  }
}
