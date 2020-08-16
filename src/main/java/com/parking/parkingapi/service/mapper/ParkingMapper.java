package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.ParkingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for Parking data model - Entity conversion and vice - versa.
 */
@Component
public class ParkingMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParkingMapper.class);

  final ParkingSlotMapper parkingSlotMapper;

  @Autowired
  public ParkingMapper(ParkingSlotMapper parkingSlotMapper) {
    this.parkingSlotMapper = parkingSlotMapper;
  }

  /**
   * Given a Parking DTO, it returns the corresponding entity
   *
   * @param parking
   * @return
   */
  public ParkingEntity mapToEntity(@NotNull Parking parking) {
    String name = parking.getName();
    String address = parking.getAddress();
    String city = parking.getCity();

    ParkingEntity parkingEntity = new ParkingEntity();
    parkingEntity.setName(name);
    parkingEntity.setAddress(address);
    parkingEntity.setCity(city);
    return parkingEntity;
  }

  /**
   * It creates the Parking domain object from the entities in input.
   *
   * @param parkingEntity
   * @param slotEntities
   * @param logEntities
   * @return
   */
  public Parking mapToParking(
      @NotNull ParkingEntity parkingEntity, @NotNull List<ParkingSlotEntity> slotEntities, @NotNull List<ParkingLogEntity> logEntities) {

    long parkingId = parkingEntity.getId();
    List<ParkingSlotEntity> correspondingSlotEntities = slotEntities.stream()
        .filter(s -> Objects.equals(parkingEntity, s.getParkingEntity()))
        .collect(toList());

    if (CollectionUtils.isEmpty(correspondingSlotEntities)) {
      LOGGER.warn("No slots associated to PARKING ID {}.", parkingId);
    }

    Parking parking = ParkingBuilder.builder()
        .withId(parkingId)
        .withName(parkingEntity.getName())
        .withAddress(parkingEntity.getAddress())
        .withCity(parkingEntity.getCity())
        .withParkingSlots(parkingSlotMapper.mapSlotEntitiesToDto(correspondingSlotEntities, logEntities))
        .build();

    // Add the pricing policy
    PricingPolicyFactory.createPricingPolicy(parkingEntity)
        .ifPresent(parking::setPricingPolicy);
    return parking;
  }
}
