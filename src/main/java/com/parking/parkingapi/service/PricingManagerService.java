package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.PricingPolicyDao;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.entities.PricingPolicyEntity;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import com.parking.parkingapi.service.mapper.PricingPolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class PricingManagerService implements ManagerService<PricingPolicy, Long> {

  private static final String PRICING_POLICY_NOT_FOUND_ERROR_MESSAGE = "PricingPolicy not found.";

  private final PricingPolicyDao pricingPolicyDao;

  @Autowired
  public PricingManagerService(PricingPolicyDao pricingPolicyDao) {
    this.pricingPolicyDao = pricingPolicyDao;
  }

  @Override
  public PricingPolicy find(Long pricingPolicyId) throws EntityNotFoundException {

    PricingPolicyEntity pricingPolicyEntity = findPricingPolicyEntity(pricingPolicyId);

    return PricingPolicyFactory.createPricingPolicy(pricingPolicyEntity)
        .orElseThrow(() -> new EntityNotFoundException(PRICING_POLICY_NOT_FOUND_ERROR_MESSAGE));
  }

  @Override
  public PricingPolicy create(@NotNull PricingPolicy newElement) {
    // Not implemented
    return null;
  }

  @Override
  public List<PricingPolicy> findAll() {
    return pricingPolicyDao.findAll().stream()
        .map(PricingPolicyFactory::createPricingPolicy)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  @Override
  public void delete(Long pricingPolicyId) throws EntityNotFoundException {
    PricingPolicyEntity pricingPolicyEntity = findPricingPolicyEntity(pricingPolicyId);
    pricingPolicyDao.delete(pricingPolicyEntity);
  }

  private PricingPolicyEntity findPricingPolicyEntity(Long pricingPolicyId) throws EntityNotFoundException {
    return pricingPolicyDao.findById(pricingPolicyId)
        .orElseThrow(() -> new EntityNotFoundException(PRICING_POLICY_NOT_FOUND_ERROR_MESSAGE));
  }
}
