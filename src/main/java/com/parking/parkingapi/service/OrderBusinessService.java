package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.exception.VehicleAlreadyParkedException;
import com.parking.parkingapi.model.car.OrderDto;
import com.parking.parkingapi.model.car.Vehicle;
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

@Component
public class OrderBusinessService {

  private static final Logger LOG = LoggerFactory.getLogger(OrderBusinessService.class);

  public static final String VEHICLE_ALREADY_PARKED_ERROR_MESSAGE = "It is not possible to park a vehicle which results already parked.";

  private ParkingManagerService parkingManagerService;

  private VehicleManagerService vehicleManagerService;

  private VehicleMapper vehicleMapper;

  private ParkingSlotDao parkingSlotDao;

  private ParkingLogsDao parkingLogsDao;

  @Autowired
  public OrderBusinessService(
      ParkingManagerService parkingManagerService, VehicleManagerService vehicleManagerService,
      VehicleMapper vehicleMapper, ParkingSlotDao parkingSlotDao, ParkingLogsDao parkingLogsDao) {
    this.parkingManagerService = parkingManagerService;
    this.vehicleManagerService = vehicleManagerService;
    this.vehicleMapper = vehicleMapper;
    this.parkingSlotDao = parkingSlotDao;
    this.parkingLogsDao = parkingLogsDao;
  }

  public OrderDto createOrder(OrderDto orderDto)
      throws EntityNotFoundException, NoSlotAvailableException, EntityCreationViolation, VehicleAlreadyParkedException {

    Vehicle vehicle = vehicleManagerService.create(orderDto.getVehicle());
    VehicleEntity vehicleEntity = vehicleMapper.mapToEntity(vehicle);

    List<ParkingLogEntity> logsByVehicle = parkingLogsDao.findByVehicleEntity(vehicleEntity);
    boolean isCurrentlyParked = logsByVehicle.stream()
        .anyMatch(l -> Objects.isNull(l.getTimeStampOut()));

    if (isCurrentlyParked) {
      LOG.error(VEHICLE_ALREADY_PARKED_ERROR_MESSAGE);
      throw new VehicleAlreadyParkedException(VEHICLE_ALREADY_PARKED_ERROR_MESSAGE);
    }

    Parking parking = parkingManagerService.find(orderDto.getParkingId());

    Optional<ParkingSlot> parkingSlotOptional = parking.getParkingSlots().stream()
        .filter(ParkingSlot::isFree)
        .filter(s -> Objects.equals(orderDto.getVehicle().getRequestedService(), s.getOfferedService()))
        .findAny();

    ParkingSlot parkingSlot = parkingSlotOptional
        .orElseThrow(() -> new NoSlotAvailableException(String.format("No slot available in parking %s", parking.getId())));

    ParkingSlotEntity parkingSlotEntity = parkingSlotDao.findById(parkingSlot.getId())
        .orElseThrow(() -> new EntityNotFoundException("Slot entity not found"));
    orderDto.setSlotNumber(parkingSlotEntity.getSlotNumber());

    ParkingLogEntity logEntity = new ParkingLogEntity();
    logEntity.setTimeStampIn(orderDto.getTimeStampIn());
    logEntity.setVehicleEntity(vehicleEntity);
    logEntity.setParkingSlotEntity(parkingSlotEntity);

    ParkingLogEntity createdLog = parkingLogsDao.save(logEntity);
    orderDto.setOrderId(createdLog.getId());

    return orderDto;
  }

}
