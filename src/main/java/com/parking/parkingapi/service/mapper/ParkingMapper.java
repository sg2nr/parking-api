package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.ParkingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for Parking DTO - Entity conversion and vice - versa.
 */
@Component
public class ParkingMapper {

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
   * @param parkingEntity
   * @param slotEntities
   * @param logEntities
   * @return
   */
  public Parking mapToDto(
      @NotNull ParkingEntity parkingEntity, @NotNull List<ParkingSlotEntity> slotEntities, @NotNull List<ParkingLogEntity> logEntities) {

    List<ParkingSlotEntity> correspondingSlotEntities = slotEntities.stream()
        .filter(s -> Objects.equals(parkingEntity, s.getParkingEntity()))
        .collect(toList());

    return ParkingBuilder.builder()
        .withId(parkingEntity.getId())
        .withName(parkingEntity.getName())
        .withAddress(parkingEntity.getAddress())
        .withCity(parkingEntity.getCity())
        .withParkingSlots(parkingSlotMapper.mapSlotEntitiesToDto(correspondingSlotEntities, logEntities))
        .build();
  }
}
