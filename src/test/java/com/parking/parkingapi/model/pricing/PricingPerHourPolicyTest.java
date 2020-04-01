package com.parking.parkingapi.model.pricing;

import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.model.pricing.Currency;
import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.pricing.PricingPerHourPolicy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class PricingPerHourPolicyTest {

  private static final String CURRENCY_CODE = "EUR";

  private static final int DECIMAL_PLACES = 2;

  private static final int PRICE_PER_HOUR = 100;

  private PricingPerHourPolicy policy;

  @BeforeAll
  public void setUp() {
    Currency currency = new Currency(CURRENCY_CODE, DECIMAL_PLACES);
    policy = new PricingPerHourPolicy(PRICE_PER_HOUR, currency);
  }

  @Test
  public void testLessThanOneHourTotalAmount() throws TemporaryDataInconsistencyException {
    ZonedDateTime in = ZonedDateTime.now();
    ZonedDateTime out = ZonedDateTime.now();

    Price amount = policy.calculateAmount(in, out);

    assertNotNull(amount);
    assertEquals(0, amount.getAmount());
    assertEquals(CURRENCY_CODE, amount.getCurrency().getCurrencyCode());
  }

  @Test
  public void testExactlyOneHourTotalAmount() throws TemporaryDataInconsistencyException {
    ZonedDateTime in = ZonedDateTime.now();
    ZonedDateTime out = in.plus(1, ChronoUnit.HOURS);

    Price amount = policy.calculateAmount(in, out);

    assertNotNull(amount);
    assertEquals(PRICE_PER_HOUR, amount.getAmount());
    assertEquals(CURRENCY_CODE, amount.getCurrency().getCurrencyCode());
  }

  @Test
  public void testWrongTemporalData() {
    ZonedDateTime in = ZonedDateTime.now();
    ZonedDateTime out = in.minus(10, ChronoUnit.HOURS);

    Exception exception = assertThrows(TemporaryDataInconsistencyException.class,
        () -> policy.calculateAmount(in, out));

    String expectedMessage = "The date time of exit is before the entrance!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

}