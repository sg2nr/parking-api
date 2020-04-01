package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.model.car.OrderDto;
import com.parking.parkingapi.model.car.Vehicle;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.VehicleEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class OrderBusinessService {

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
      throws EntityNotFoundException, NoSlotAvailableException, EntityCreationViolation {

    Parking parking = parkingManagerService.find(orderDto.getParkingId());

    Optional<ParkingSlot> parkingSlotOptional = parking.getParkingSlots().stream()
        .filter(ParkingSlot::isFree)
        .filter(s -> Objects.equals(orderDto.getVehicle().getRequestedService(), s.getOfferedService()))
        .findAny();

    ParkingSlot parkingSlot = parkingSlotOptional
        .orElseThrow(() -> new NoSlotAvailableException(String.format("No slot available in parking %s", parking.getId())));

    Vehicle vehicle = vehicleManagerService.create(orderDto.getVehicle());
    VehicleEntity vehicleEntity = vehicleMapper.mapToEntity(vehicle);

    ParkingSlotEntity parkingSlotEntity = parkingSlotDao.findById(parkingSlot.getId())
        .orElseThrow(() -> new EntityNotFoundException("Slot entity not found"));

    ParkingLogEntity logEntity = new ParkingLogEntity();
    logEntity.setTimeStampIn(orderDto.getTimeStampIn());
    logEntity.setVehicleEntity(vehicleEntity);
    logEntity.setParkingSlotEntity(parkingSlotEntity);

    ParkingLogEntity createdLog = parkingLogsDao.save(logEntity);

    orderDto.setOrderId(createdLog.getId());
    return orderDto;
  }

}
