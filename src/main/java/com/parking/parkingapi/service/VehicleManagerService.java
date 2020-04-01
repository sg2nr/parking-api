package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.VehicleDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.vehicle.Vehicle;
import com.parking.parkingapi.model.entities.VehicleEntity;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class VehicleManagerService implements ManagerService<Vehicle, String> {

  private static final Logger LOG = LoggerFactory.getLogger(VehicleManagerService.class);

  private static final String CREATION_VEHICLE_ERROR_MESSAGE = "Impossible to create the requested vehicle";

  private final VehicleDao vehicleDao;

  private final VehicleMapper vehicleMapper;

  @Autowired
  public VehicleManagerService(VehicleDao vehicleDao, VehicleMapper vehicleMapper) {
    this.vehicleDao = vehicleDao;
    this.vehicleMapper = vehicleMapper;
  }

  @Override
  public Vehicle find(String plate) throws EntityNotFoundException {
    VehicleEntity vehicleEntity = findVehicleEntityByPlate(plate);

    return vehicleMapper.mapToDto(vehicleEntity);
  }

  @Override
  public Vehicle create(Vehicle newVehicle) throws EntityCreationViolation {
    VehicleEntity vehicleEntity = vehicleMapper.mapToEntity(newVehicle);
    try {
      VehicleEntity createdVehicle = vehicleDao.save(vehicleEntity);
      return vehicleMapper.mapToDto(createdVehicle);
    } catch (
        DataIntegrityViolationException e) {
      LOG.error(CREATION_VEHICLE_ERROR_MESSAGE);
      throw new EntityCreationViolation(CREATION_VEHICLE_ERROR_MESSAGE);
    }
  }

  @Override
  public List<Vehicle> findAll() {
    return vehicleDao.findAll().stream()
        .map(vehicleMapper::mapToDto)
        .collect(toList());
  }

  @Override
  public void delete(String plate) throws EntityNotFoundException {
    vehicleDao.delete(findVehicleEntityByPlate(plate));
  }

  private VehicleEntity findVehicleEntityByPlate(String plate) throws EntityNotFoundException {
    return vehicleDao.findByPlate(plate)
        .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
  }
}
