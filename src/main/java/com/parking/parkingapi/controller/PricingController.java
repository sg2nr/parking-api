package com.parking.parkingapi.controller;

import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import com.parking.parkingapi.service.PricingManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pricing-policies")
public class PricingController {

  private final PricingManagerService pricingManagerService;

  public PricingController(PricingManagerService pricingManagerService) {
    this.pricingManagerService = pricingManagerService;
  }

  @GetMapping
  public List<PricingPolicy> findAllPricingPolicies() {
    return pricingManagerService.findAll();
  }

  @GetMapping("/{policyId}")
  public PricingPolicy retrievePricingPolicy(@Valid @PathVariable long policyId) throws EntityNotFoundException {
    return pricingManagerService.find(policyId);
  }

  @DeleteMapping("/{policyId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePricingPolicy(@Valid @PathVariable long policyId) throws EntityNotFoundException {
    pricingManagerService.delete(policyId);
  }
}
