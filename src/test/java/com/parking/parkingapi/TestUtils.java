package com.parking.parkingapi;

import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.entities.CurrencyEntity;
import com.parking.parkingapi.model.entities.EngineType;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.PricingPolicyEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.model.pricing.Currency;
import com.parking.parkingapi.model.pricing.PricingPerHourPolicy;
import com.parking.parkingapi.model.pricing.PricingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

  public static final long PARKING_ID_1 = 1;

  public static final String PARKING_NAME_1 = "Parking de la Baie";

  public static final String PARKING_ADDRESS_1 = "Boulevard de la Croisette";

  public static final String PARKING_CITY_1 = "Cannes";

  public static final int TOTAL_STANDARD_SLOTS = 100;

  public static final int TOTAL_20KW_SLOTS = 50;

  public static final int TOTAL_50KW_SLOTS = 30;

  public static final int TOTAL_SLOTS = 180;

  private static final String CURRENCY_CODE = "EUR";

  private static final int DECIMAL_PLACES = 2;

  private static final int PRICE_PER_HOUR = 100;

  public static final int POLICY_UNIT_PRICE = 100;

  public static final int POLICY_FIXED_PRICE = 50;

  public static Parking getTestParking() {
    Parking parking = new Parking();
    parking.setId(PARKING_ID_1);
    parking.setName(PARKING_NAME_1);
    parking.setAddress(PARKING_ADDRESS_1);
    parking.setCity(PARKING_CITY_1);

    List<ParkingSlot> parkingSlots = new ArrayList<>();
    parkingSlots.addAll(createParkingSlots(ParkingServiceType.STANDARD_PARKING, TOTAL_STANDARD_SLOTS));
    parkingSlots.addAll(createParkingSlots(ParkingServiceType.TWENTY_KW_POWER_SUPPLY, TOTAL_20KW_SLOTS));
    parkingSlots.addAll(createParkingSlots(ParkingServiceType.FIFTY_KW_POWER_SUPPLY, TOTAL_50KW_SLOTS));
    parking.setParkingSlots(parkingSlots);

    return parking;
  }

  private static List<ParkingSlot> createParkingSlots(ParkingServiceType type, int size) {

    AtomicInteger slotNumber = new AtomicInteger(1);
    return Stream.generate(ParkingSlot::new)
        .limit(size)
        .map(s -> {
          ParkingSlot ps = new ParkingSlot();
          ps.setOfferedService(type);
          ps.setNumber(slotNumber.getAndIncrement());
          return ps;
        })
        .collect(Collectors.toList());
  }

  public static ParkingEntity getTestParkingEntity() {
    ParkingEntity parkingEntity = new ParkingEntity();
    parkingEntity.setId(PARKING_ID_1);
    parkingEntity.setName(PARKING_NAME_1);
    parkingEntity.setAddress(PARKING_ADDRESS_1);
    parkingEntity.setCity(PARKING_CITY_1);
    parkingEntity.setPricingPolicyEntity(getTestPricingPolicyEntity());

    return parkingEntity;
  }

  public static List<ParkingSlotEntity> getTestParkingSlots() {
    ParkingEntity parkingEntity = getTestParkingEntity();

    List<ParkingSlotEntity> parkingSlotEntities = new ArrayList<>();
    parkingSlotEntities.addAll(createParkingSlotEntities(parkingEntity, EngineType.ELECTRICAL_TWENTY_KW, TOTAL_20KW_SLOTS));
    parkingSlotEntities.addAll(createParkingSlotEntities(parkingEntity, EngineType.ELECTRICAL_FIFTY_KW, TOTAL_50KW_SLOTS));
    parkingSlotEntities.addAll(createParkingSlotEntities(parkingEntity, EngineType.GASOLINE, TOTAL_STANDARD_SLOTS));

    return parkingSlotEntities;
  }

  private static List<ParkingSlotEntity> createParkingSlotEntities(
      ParkingEntity parkingEntity, EngineType type, int size) {

    AtomicInteger slotNumber = new AtomicInteger(1);
    return Stream.generate(ParkingSlotEntity::new)
        .limit(size)
        .map(s -> {
          ParkingSlotEntity ps = new ParkingSlotEntity();
          ps.setVehicleAllowed(type);
          ps.setSlotNumber(slotNumber.getAndIncrement());
          ps.setParkingLogEntities(new ArrayList<>());
          ps.setParkingEntity(parkingEntity);
          return ps;
        })
        .collect(Collectors.toList());
  }

  private static PricingPolicy createPricingPolicy() {
    Currency currency = new Currency(CURRENCY_CODE, DECIMAL_PLACES);
    return new PricingPerHourPolicy(PRICE_PER_HOUR, currency);
  }

  public static PricingPolicyEntity getTestPricingPolicyEntity() {
    PricingPolicyEntity pricingPolicyEntity = new PricingPolicyEntity();
    pricingPolicyEntity.setId(1);
    pricingPolicyEntity.setUnitPriceUnscaled(POLICY_UNIT_PRICE);
    pricingPolicyEntity.setCurrencyEntity(createCurrencyEntity());

    return pricingPolicyEntity;
  }

  public static PricingPolicyEntity getTestFixedPricePricingPolicyEntity() {
    PricingPolicyEntity pricingPolicyEntity = new PricingPolicyEntity();
    pricingPolicyEntity.setId(1);
    pricingPolicyEntity.setUnitPriceUnscaled(POLICY_UNIT_PRICE);
    pricingPolicyEntity.setFixedPriceUnscaled(POLICY_FIXED_PRICE);
    pricingPolicyEntity.setCurrencyEntity(createCurrencyEntity());

    return pricingPolicyEntity;
  }

  private static CurrencyEntity createCurrencyEntity() {
    CurrencyEntity currencyEntity = new CurrencyEntity();
    currencyEntity.setCode("EUR");
    currencyEntity.setDecimalPlaces(2);

    return currencyEntity;
  }
}
