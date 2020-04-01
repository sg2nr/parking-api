package com.parking.parkingapi.model.pricing;

import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.model.common.ParkingApiDO;

import java.time.ZonedDateTime;

/**
 * Abstraction of a Pricing Policy that prices in function of the time spent in the parking.
 */
public interface PricingPolicy extends ParkingApiDO {

  /**
   * It computes the amount to pay based on the conditions of the pricing policy.
   *
   * @param dateTimeIn
   *    timestamp of entrance
   * @param dateTimeOut
   *    timestamp of exit
   * @return
   *    The final price to pay.
   * @throws TemporaryDataInconsistencyException
   *    In case dateTimeOut is before dateTimeIn
   */
  Price calculateAmount(ZonedDateTime dateTimeIn, ZonedDateTime dateTimeOut) throws TemporaryDataInconsistencyException;

  /**
   * It returns the currency used by the policy.
   *
   * @return
   */
  Currency getPolicyCurrency();

  /**
   * The identifier of the pricing policy
   *
   * @return
   */
  Long getId();
}
