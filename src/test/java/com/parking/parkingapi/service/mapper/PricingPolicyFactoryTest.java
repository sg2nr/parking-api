package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.PricingPolicyEntity;
import com.parking.parkingapi.model.pricing.AddFixedPricePolicy;
import com.parking.parkingapi.model.pricing.PricingPerHourPolicy;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PricingPolicyFactoryTest {

  @Test
  public void testPerHourPricingPolicyCreation() {
    // Get Test parking entity with pricing per hour policy
    ParkingEntity testParkingEntity = TestUtils.getTestParkingEntity();

    Optional<PricingPolicy> optionalPricingPolicy = PricingPolicyFactory.createPricingPolicy(testParkingEntity);

    assertTrue(optionalPricingPolicy.isPresent());
    PricingPolicy policy = optionalPricingPolicy.get();
    assertTrue(policy instanceof PricingPerHourPolicy);
    assertNotNull(policy.getPolicyCurrency());
  }

  @Test
  public void testAddFixedPricePricingPolicyCreation() {
    // Get Test parking entity with pricing per hour policy
    ParkingEntity testParkingEntity = TestUtils.getTestParkingEntity();

    // Get a test fixed price policy entity
    PricingPolicyEntity testFixedPricePricingPolicyEntity = TestUtils.getTestFixedPricePricingPolicyEntity();

    testParkingEntity.setPricingPolicyEntity(testFixedPricePricingPolicyEntity);

    Optional<PricingPolicy> optionalPricingPolicy = PricingPolicyFactory.createPricingPolicy(testParkingEntity);

    assertTrue(optionalPricingPolicy.isPresent());
    PricingPolicy policy = optionalPricingPolicy.get();
    assertTrue(policy instanceof AddFixedPricePolicy);
    assertNotNull(policy.getPolicyCurrency());
  }

}