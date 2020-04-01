package com.parking.parkingapi.controller;

import com.parking.parkingapi.controller.mapper.OrderJsonMapper;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.model.car.OrderDto;
import com.parking.parkingapi.model.car.request.OrderRequest;
import com.parking.parkingapi.model.car.response.OrderResponse;
import com.parking.parkingapi.service.OrderBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

  private OrderJsonMapper orderJsonMapper;

  private OrderBusinessService orderBusinessService;

  @Autowired
  public OrderController(OrderJsonMapper orderJsonMapper, OrderBusinessService orderBusinessService) {
    this.orderJsonMapper = orderJsonMapper;
    this.orderBusinessService = orderBusinessService;
  }

  @PostMapping("parkings/{parkingId}/order")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponse checkInCar(@RequestBody OrderRequest request, @Valid @PathVariable long parkingId)
      throws EntityNotFoundException, EntityCreationViolation, NoSlotAvailableException {

    OrderDto order = orderBusinessService.createOrder(orderJsonMapper.mapToDto(request, parkingId));
    return orderJsonMapper.mapToResponse(order);
  }
}
