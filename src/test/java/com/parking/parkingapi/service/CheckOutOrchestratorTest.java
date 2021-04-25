package com.parking.parkingapi.service;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.exception.NoPricingPolicyFound;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.pricing.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.ZonedDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CheckOutOrchestratorTest {

  public static final int HOURS_SPENT = 10;

  @Autowired CheckOutOrchestrator checkOutOrchestrator;

  @Test
  void testAmountComputationOk() throws TemporaryDataInconsistencyException, NoPricingPolicyFound {
    ParkingLogEntity logEntity =
        getTestLogEntityForCheckout(now().minus(HOURS_SPENT, ChronoUnit.HOURS), now());

    Price amount = checkOutOrchestrator.computeAmount(logEntity);

    assertNotNull(amount);
    assertEquals(Math.multiplyExact(HOURS_SPENT, TestUtils.POLICY_UNIT_PRICE), amount.getAmount());
    assertEquals(TestUtils.CURRENCY_CODE, amount.getCurrency().getCurrencyCode());
    assertEquals(TestUtils.DECIMAL_PLACES, amount.getCurrency().getDecimalPlaces());
  }

  @Test
  void testAmountComputationWithException() {
    ParkingLogEntity logEntity =
        getTestLogEntityForCheckout(now(), now().minus(HOURS_SPENT, ChronoUnit.HOURS));
    logEntity.getParkingSlotEntity().getParkingEntity().setPricingPolicyEntity(null);

    Exception exception =
        assertThrows(
            NoPricingPolicyFound.class, () -> checkOutOrchestrator.computeAmount(logEntity));

    String expectedMessage = "No pricing policy found for parking id: 1";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  private static ParkingLogEntity getTestLogEntityForCheckout(ZonedDateTime in, ZonedDateTime out) {
    ParkingLogEntity logEntity = new ParkingLogEntity();
    logEntity.setTimeStampOut(out);
    logEntity.setTimeStampIn(in);
    logEntity.setVehicleEntity(TestUtils.getTestVehicleEntity());
    TestUtils.getTestParkingSlots().stream().findAny().ifPresent(logEntity::setParkingSlotEntity);

    return logEntity;
  }
}
