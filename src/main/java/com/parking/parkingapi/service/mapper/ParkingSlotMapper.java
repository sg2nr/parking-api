package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.entities.EngineType;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.VehicleEntity;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Mapper in charge for the ParkingSlot model - Entity conversion.
 */
@Component
public class ParkingSlotMapper {

  private final VehicleMapper vehicleMapper;

  private final ParkingServiceTypeConverter parkingServiceTypeConverter;

  @Autowired
  public ParkingSlotMapper(VehicleMapper vehicleMapper, ParkingServiceTypeConverter parkingServiceTypeConverter) {
    this.vehicleMapper = vehicleMapper;
    this.parkingServiceTypeConverter = parkingServiceTypeConverter;
  }

  public List<ParkingSlotEntity> mapSlotsToEntities(ParkingEntity parkingEntity, List<ParkingSlot> parkingSlots) {
    return parkingSlots.stream()
        .map(s -> mapSlotToEntity(parkingEntity, s))
        .collect(toList());
  }

  private ParkingSlotEntity mapSlotToEntity(ParkingEntity parkingEntity, ParkingSlot parkingSlot) {

    ParkingSlotEntity slotEntity = new ParkingSlotEntity();
    slotEntity.setSlotNumber(parkingSlot.getNumber());
    slotEntity.setParkingEntity(parkingEntity);

    EngineType vehicleAllowed = parkingServiceTypeConverter.convertToVehiclePoweredType(parkingSlot.getOfferedService());
    slotEntity.setVehicleAllowed(vehicleAllowed);

    return slotEntity;
  }

  public List<ParkingSlot> mapSlotEntitiesToDto(List<ParkingSlotEntity> slotEntities, List<ParkingLogEntity> logEntities) {
    return slotEntities.stream()
        .map(se -> mapSlotEntityToParkingSlot(se, logEntities))
        .collect(toList());
  }

  private ParkingSlot mapSlotEntityToParkingSlot(ParkingSlotEntity slotEntity, List<ParkingLogEntity> logEntities) {
    ParkingSlot parkingSlot = new ParkingSlot();
    parkingSlot.setId(slotEntity.getId());
    parkingSlot.setNumber(slotEntity.getId());
    parkingSlot.setOfferedService(parkingServiceTypeConverter.convertToParkingService(slotEntity.getVehicleAllowed()));
    getVehicleEntity(slotEntity, logEntities).ifPresent(v -> parkingSlot.setVehicle(vehicleMapper.mapToVehicle(v)));

    return parkingSlot;
  }

  private Optional<VehicleEntity> getVehicleEntity(ParkingSlotEntity slotEntity, List<ParkingLogEntity> logEntities) {
    return logEntities.parallelStream()
        .filter(l -> Objects.isNull(l.getTimeStampOut()))
        .filter(l -> Objects.equals(l.getParkingSlotEntity().getId(), slotEntity.getId()))
        .map(ParkingLogEntity::getVehicleEntity)
        .findFirst();
  }
}
