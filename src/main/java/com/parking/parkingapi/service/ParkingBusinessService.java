package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingDao;
import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.dto.parking.Parking;
import com.parking.parkingapi.dto.parking.ParkingBuilder;
import com.parking.parkingapi.dto.parking.ParkingStatus;
import com.parking.parkingapi.entities.ParkingEntity;
import com.parking.parkingapi.entities.ParkingLogEntity;
import com.parking.parkingapi.entities.ParkingSlotEntity;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class ParkingBusinessService implements BusinessService<Parking, Long> {

  private static final Logger LOG = LoggerFactory.getLogger(ParkingBusinessService.class);

  private static final String RETRIEVE_PARKING_ERROR_MESSAGE = "Impossible to retrieve parking with id: %s.";

  private static final String CREATION_PARKING_ERROR_MESSAGE = "Impossible to create the requested parking.";

  @Autowired
  ParkingDao parkingDao;

  @Autowired
  ParkingSlotDao parkingSlotDao;

  @Autowired
  ParkingLogsDao parkingLogsDao;

  @Override
  public Parking find(Long id) throws EntityNotFoundException {
    ParkingEntity parkingEntity = findParkingEntity(id);
    return mapToDto(parkingEntity);
  }

  @Override
  public Parking create(@NotNull Parking newParking) throws EntityCreationViolation {
    long parkingId = newParking.getId();
    String parkingName = newParking.getName();
    String parkingAddress = newParking.getAddress();
    String parkingCity = newParking.getCity();

    ParkingEntity parkingEntity = new ParkingEntity(parkingId, parkingName, parkingAddress, parkingCity);

    try {
      ParkingEntity createdEntity = parkingDao.save(parkingEntity);
      return mapToDto(createdEntity);
    } catch (DataIntegrityViolationException e) {
      LOG.error(CREATION_PARKING_ERROR_MESSAGE);
      throw new EntityCreationViolation(CREATION_PARKING_ERROR_MESSAGE);
    }
  }

  @Override
  public List<Parking> findAll() {
    List<ParkingEntity> parkingEntities = parkingDao.findAll();

    return parkingEntities.stream()
        .map(this::mapToDto)
        .collect(toList());
  }

  @Override
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

  private Parking mapToDto(ParkingEntity parkingEntity) {
    ParkingStatus overallStatus = getParkingStatusFromEntity(parkingEntity);

    return ParkingBuilder.builder()
        .withId(parkingEntity.getId())
        .withName(parkingEntity.getName())
        .withAddress(parkingEntity.getAddress())
        .withCity(parkingEntity.getCity())
        .withStatus(overallStatus)
        .build();
  }

  private ParkingStatus getParkingStatusFromEntity(ParkingEntity parkingEntity) {

    List<ParkingSlotEntity> slotEntities = parkingSlotDao.findByParkingEntity(parkingEntity);

    List<ParkingLogEntity> logEntities = parkingLogsDao.findAll();

    long totalSlots = slotEntities.size();
    long freeSlots = logEntities.parallelStream()
        .filter(l -> Objects.isNull(l.getTimeStampOut()))
        .filter(l -> slotEntities.contains(l.getParkingSlotEntity()))
        .count();

    return new ParkingStatus(totalSlots, freeSlots);
  }
}
