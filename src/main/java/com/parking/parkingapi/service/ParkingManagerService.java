package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingDao;
import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.service.mapper.ParkingMapper;
import com.parking.parkingapi.service.mapper.ParkingSlotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * This class is responsible for the Parking business logic.
 */
@Component
public class ParkingManagerService implements ManagerService<Parking, Long> {

  private static final Logger LOG = LoggerFactory.getLogger(ParkingManagerService.class);

  private static final String RETRIEVE_PARKING_ERROR_MESSAGE = "Impossible to retrieve parking with id: %s.";

  private static final String CREATION_PARKING_ERROR_MESSAGE = "Impossible to create the requested parking.";

  private final ParkingDao parkingDao;

  private final ParkingSlotDao parkingSlotDao;

  private final ParkingLogsDao parkingLogsDao;

  private final ParkingMapper mapper;

  private final ParkingSlotMapper slotMapper;

  @Autowired
  public ParkingManagerService(
      ParkingDao parkingDao, ParkingSlotDao parkingSlotDao, ParkingLogsDao parkingLogsDao,
      ParkingMapper mapper, ParkingSlotMapper slotMapper) {
    this.parkingDao = parkingDao;
    this.parkingSlotDao = parkingSlotDao;
    this.parkingLogsDao = parkingLogsDao;
    this.mapper = mapper;
    this.slotMapper = slotMapper;
  }

  public Parking find(Long id) throws EntityNotFoundException {
    ParkingEntity parkingEntity = findParkingEntity(id);
    List<ParkingSlotEntity> slotEntities = parkingSlotDao.findByParkingEntity(parkingEntity);
    List<ParkingLogEntity> logEntities = parkingLogsDao.findByParkingSlotEntityIn(slotEntities);
    return mapper.mapToDto(parkingEntity, slotEntities, logEntities);
  }

  public Parking create(@NotNull Parking createParkingRequest) throws EntityCreationViolation {
    ParkingEntity parkingEntity = mapper.mapToEntity(createParkingRequest);

    try {
      ParkingEntity createdEntity = parkingDao.save(parkingEntity);

      // Create the parking slots
      List<ParkingSlotEntity> slotEntities = slotMapper.mapSlotsToEntities(parkingEntity, createParkingRequest.getParkingSlots());
      List<ParkingSlotEntity> createdSlotEntities = parkingSlotDao.saveAll(slotEntities);

      return mapper.mapToDto(createdEntity, createdSlotEntities, new ArrayList<>());
    } catch (DataIntegrityViolationException e) {
      LOG.error(CREATION_PARKING_ERROR_MESSAGE);
      throw new EntityCreationViolation(CREATION_PARKING_ERROR_MESSAGE);
    }
  }

  public List<Parking> findAll() {
    List<ParkingEntity> parkingEntities = parkingDao.findAll();
    List<ParkingSlotEntity> slotEntities = parkingSlotDao.findAll();
    List<ParkingLogEntity> logEntities = parkingLogsDao.findAll();

    return parkingEntities.stream()
        .map(p -> mapper.mapToDto(p, slotEntities, logEntities))
        .collect(toList());
  }

  public void delete(Long id) throws EntityNotFoundException {
    ParkingEntity parkingEntity = findParkingEntity(id);
    parkingDao.delete(parkingEntity);
  }

  private ParkingEntity findParkingEntity(Long id) throws EntityNotFoundException {
    Optional<ParkingEntity> optionalParkingEntity = parkingDao.findById(id);

    if (optionalParkingEntity.isPresent()) {
      return optionalParkingEntity.get();
    } else {
      String message = String.format(RETRIEVE_PARKING_ERROR_MESSAGE, id);
      LOG.error(message);
      throw new EntityNotFoundException(message);
    }
  }
}
