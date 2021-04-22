package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.exception.VehicleAlreadyParkedException;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.vehicle.Vehicle;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.VehicleEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** The aim of this orchestrator is to assign a vehicle to a free parking slot. */
@Component
public class CheckInOrchestrator implements Orchestrator<Order> {

  private static final Logger LOG = LoggerFactory.getLogger(CheckInOrchestrator.class);

  private static final String VEHICLE_ALREADY_PARKED_ERROR_MESSAGE =
      "It is not possible to park a vehicle which results already parked.";

  private static final String NO_SLOT_AVAILABLE_ERROR_MESSAGE = "No slot available in parking %s";

  private static final String SLOT_ENTITY_NOT_FOUND_ERROR_MESSAGE = "Slot entity not found";

  private final ParkingManagerService parkingManagerService;

  private final VehicleManagerService vehicleManagerService;

  private final VehicleMapper vehicleMapper;

  private final ParkingSlotDao parkingSlotDao;

  private final ParkingLogsDao parkingLogsDao;

  @Autowired
  public CheckInOrchestrator(
      ParkingManagerService parkingManagerService,
      VehicleManagerService vehicleManagerService,
      VehicleMapper vehicleMapper,
      ParkingSlotDao parkingSlotDao,
      ParkingLogsDao parkingLogsDao) {
    this.parkingManagerService = parkingManagerService;
    this.vehicleManagerService = vehicleManagerService;
    this.vehicleMapper = vehicleMapper;
    this.parkingSlotDao = parkingSlotDao;
    this.parkingLogsDao = parkingLogsDao;
  }

  @Override
  public Order run(Order order)
      throws EntityCreationViolation, NoSlotAvailableException, VehicleAlreadyParkedException,
          EntityNotFoundException {

    Vehicle vehicle = vehicleManagerService.create(order.getVehicle());
    VehicleEntity vehicleEntity = vehicleMapper.mapToEntity(vehicle);

    if (isCurrentlyParked(vehicleEntity)) {
      LOG.error(VEHICLE_ALREADY_PARKED_ERROR_MESSAGE);
      throw new VehicleAlreadyParkedException(VEHICLE_ALREADY_PARKED_ERROR_MESSAGE);
    }

    ParkingLogEntity createdLog = registerCheckIn(order, vehicleEntity);

    order.setOrderId(createdLog.getId());

    return order;
  }

  private ParkingLogEntity registerCheckIn(Order order, VehicleEntity vehicleEntity)
      throws EntityNotFoundException, NoSlotAvailableException {
    ParkingLogEntity logEntity = createParkingLogEntity(order, vehicleEntity);

    return parkingLogsDao.save(logEntity);
  }

  private ParkingLogEntity createParkingLogEntity(Order order, VehicleEntity vehicleEntity)
      throws EntityNotFoundException, NoSlotAvailableException {
    ParkingSlotEntity parkingSlotEntity = getParkingSlotEntity(order);
    order.setSlotNumber(parkingSlotEntity.getSlotNumber());

    ParkingLogEntity logEntity = new ParkingLogEntity();
    logEntity.setTimeStampIn(order.getTimeStampIn());
    logEntity.setVehicleEntity(vehicleEntity);
    logEntity.setParkingSlotEntity(parkingSlotEntity);
    return logEntity;
  }

  private ParkingSlotEntity getParkingSlotEntity(Order order)
      throws EntityNotFoundException, NoSlotAvailableException {
    Parking parking = parkingManagerService.find(order.getParkingId());

    Optional<ParkingSlot> parkingSlotOptional =
        parking.getParkingSlots().stream()
            .filter(ParkingSlot::isFree)
            .filter(
                s ->
                    Objects.equals(order.getVehicle().getRequestedService(), s.getOfferedService()))
            .findAny();

    ParkingSlot parkingSlot =
        parkingSlotOptional.orElseThrow(
            () ->
                new NoSlotAvailableException(
                    String.format(NO_SLOT_AVAILABLE_ERROR_MESSAGE, parking.getId())));

    return parkingSlotDao
        .findById(parkingSlot.getId())
        .orElseThrow(() -> new EntityNotFoundException(SLOT_ENTITY_NOT_FOUND_ERROR_MESSAGE));
  }

  private boolean isCurrentlyParked(VehicleEntity vehicleEntity) {
    List<ParkingLogEntity> logsByVehicle = parkingLogsDao.findByVehicleEntity(vehicleEntity);
    return logsByVehicle.stream().anyMatch(l -> Objects.isNull(l.getTimeStampOut()));
  }
}
