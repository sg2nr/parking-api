package com.parking.parkingapi.controller;

import com.parking.parkingapi.controller.mapper.OrderJsonMapper;
import com.parking.parkingapi.exception.CheckOutAlreadyPerformedException;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.InvalidInputDataException;
import com.parking.parkingapi.exception.NoPricingPolicyFound;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.exception.OrderNotFoundException;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.exception.VehicleAlreadyParkedException;
import com.parking.parkingapi.model.order.response.CheckOutResponse;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.order.request.OrderRequest;
import com.parking.parkingapi.model.order.response.OrderResponse;
import com.parking.parkingapi.service.CheckInOrchestrator;
import com.parking.parkingapi.service.CheckOutOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

  private final OrderJsonMapper orderJsonMapper;

  private final CheckInOrchestrator checkInOrchestrator;

  private final CheckOutOrchestrator checkOutOrchestrator;

  @Autowired
  public OrderController(OrderJsonMapper orderJsonMapper, CheckInOrchestrator checkInOrchestrator, CheckOutOrchestrator checkOutOrchestrator) {
    this.orderJsonMapper = orderJsonMapper;
    this.checkInOrchestrator = checkInOrchestrator;
    this.checkOutOrchestrator = checkOutOrchestrator;
  }

  @PostMapping("parkings/{parkingId}/order")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponse checkIn(@RequestBody OrderRequest request, @Valid @PathVariable long parkingId)
      throws EntityNotFoundException, EntityCreationViolation, NoSlotAvailableException,
      VehicleAlreadyParkedException, InvalidInputDataException {

    Order order = checkInOrchestrator.run(orderJsonMapper.mapToOrder(request, parkingId));
    return orderJsonMapper.mapToResponse(order);
  }

  @PostMapping("parkings/checkout")
  @ResponseStatus(HttpStatus.CREATED)
  public CheckOutResponse checkOut(@RequestParam long orderId) throws OrderNotFoundException,
      TemporaryDataInconsistencyException, NoPricingPolicyFound, CheckOutAlreadyPerformedException {

    Order orderDtoRequest = new Order();
    orderDtoRequest.setOrderId(orderId);

    Order orderDtoResponse = checkOutOrchestrator.run(orderDtoRequest);
    return orderJsonMapper.mapToCheckoutResponse(orderDtoResponse);
  }

}
