package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.exception.CheckOutAlreadyPerformedException;
import com.parking.parkingapi.exception.NoPricingPolicyFound;
import com.parking.parkingapi.exception.OrderNotFoundException;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.order.OrderDO;
import com.parking.parkingapi.model.order.OrderDtoBuilder;
import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import com.parking.parkingapi.model.vehicle.Vehicle;
import com.parking.parkingapi.service.mapper.PricingPolicyFactory;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class CheckOutOrchestrator implements Orchestrator<OrderDO> {

  private static final Logger LOG = LoggerFactory.getLogger(CheckOutOrchestrator.class);

  private static final String NO_POLICY_FOUND_ERROR_MESSAGE = "No pricing policy found for parking id: %s";

  private static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order id: %s not found";

  private static final String CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE = "Checkout already perfomed.";

  private final ParkingLogsDao parkingLogsDao;

  private final VehicleMapper vehicleMapper;

  @Autowired
  public CheckOutOrchestrator(ParkingLogsDao parkingLogsDao, VehicleMapper vehicleMapper) {
    this.parkingLogsDao = parkingLogsDao;
    this.vehicleMapper = vehicleMapper;
  }

  @Override
  public OrderDO run(OrderDO orderDto) throws OrderNotFoundException, NoPricingPolicyFound,
      TemporaryDataInconsistencyException, CheckOutAlreadyPerformedException {

    long orderId = orderDto.getOrderId();

    ParkingLogEntity savedLog = updateLog(orderId);

    Price amount = computeAmount(savedLog);

    // Mapping
    Vehicle vehicle = vehicleMapper.mapToDto(savedLog.getVehicleEntity());
    ParkingEntity parkingEntity = savedLog.getParkingSlotEntity().getParkingEntity();
    return OrderDtoBuilder.builder()
        .withOrderId(orderId)
        .withTimeStampIn(savedLog.getTimeStampIn())
        .withTimeStampOut(savedLog.getTimeStampOut())
        .withVehicle(vehicle)
        .withParkingId(parkingEntity.getId())
        .withSlotNumber(savedLog.getParkingSlotEntity().getSlotNumber())
        .withAmount(amount)
        .build();
  }

  private ParkingLogEntity updateLog(long orderId) throws OrderNotFoundException, CheckOutAlreadyPerformedException {
    Optional<ParkingLogEntity> optionalLog = parkingLogsDao.findById(orderId);

    ParkingLogEntity log = optionalLog
        .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_ERROR_MESSAGE, orderId)));

    if (Objects.nonNull(log.getTimeStampOut())) {
      LOG.error(CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE);
      throw new CheckOutAlreadyPerformedException(CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE);
    }

    log.setTimeStampOut(ZonedDateTime.now());

    return parkingLogsDao.save(log);
  }

  private Price computeAmount(ParkingLogEntity savedLog) throws NoPricingPolicyFound, TemporaryDataInconsistencyException {
    ParkingEntity parkingEntity = savedLog.getParkingSlotEntity().getParkingEntity();
    PricingPolicy pricingPolicy = PricingPolicyFactory.createPricingPolicy(parkingEntity)
        .orElseThrow(() -> new NoPricingPolicyFound(String.format(NO_POLICY_FOUND_ERROR_MESSAGE, parkingEntity.getId())));

    ZonedDateTime timeStampIn = savedLog.getTimeStampIn();
    ZonedDateTime timeStampOut = savedLog.getTimeStampOut();

    return pricingPolicy.calculateAmount(timeStampIn, timeStampOut);
  }

}
