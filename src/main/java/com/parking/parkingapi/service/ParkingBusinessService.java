package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingDao;
import com.parking.parkingapi.dto.Parking;
import com.parking.parkingapi.entities.ParkingEntity;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.service.mapper.ParkingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class ParkingBusinessService implements BusinessService<Parking, Long> {

  private static final Logger LOG = LoggerFactory.getLogger(ParkingBusinessService.class);

  private static final String RETRIEVE_PARKING_ERROR_MESSAGE = "Impossible to retrieve parking with id: %s.";

  private static final String CREATION_PARKING_ERROR_MESSAGE = "Impossible to create the requested parking.";

  ParkingDao parkingDao;

  ParkingMapper mapper;

  @Autowired
  public ParkingBusinessService(ParkingDao parkingDao, ParkingMapper mapper) {
    this.parkingDao = parkingDao;
    this.mapper = mapper;
  }

  @Override
  public Parking find(Long id) throws EntityNotFoundException {
    ParkingEntity parkingEntity = findParkingEntity(id);
    return mapper.mapToDto(parkingEntity);
  }

  @Override
  public Parking create(@NotNull Parking newParking) throws EntityCreationViolation {
    ParkingEntity parkingEntity = mapper.mapToEntity(newParking);

    try {
      ParkingEntity createdEntity = parkingDao.save(parkingEntity);
      return mapper.mapToDto(createdEntity);
    } catch (DataIntegrityViolationException e) {
      LOG.error(CREATION_PARKING_ERROR_MESSAGE);
      throw new EntityCreationViolation(CREATION_PARKING_ERROR_MESSAGE);
    }
  }

  @Override
  public List<Parking> findAll() {
    List<ParkingEntity> parkingEntities = parkingDao.findAll();

    return parkingEntities.stream().map(parkingEntity -> mapper.mapToDto(parkingEntity)).collect(toList());
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
}
