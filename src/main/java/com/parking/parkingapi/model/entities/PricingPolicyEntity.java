package com.parking.parkingapi.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "pricing_policies")
public class PricingPolicyEntity {

  @Id
  @GeneratedValue(strategy=IDENTITY)
  private long id;

  @Column(name = "unit_price")
  private Integer unitPriceUnscaled;

  @Column(name = "fixed_price")
  private Integer fixedPriceUnscaled;

  @ManyToOne
  @JoinColumn(name = "currency")
  private CurrencyEntity currencyEntity;

  public PricingPolicyEntity() {
  }

  public PricingPolicyEntity(long id, int unitPriceUnscaled, int fixedPriceUnscaled, CurrencyEntity currencyEntity) {
    this.id = id;
    this.unitPriceUnscaled = unitPriceUnscaled;
    this.fixedPriceUnscaled = fixedPriceUnscaled;
    this.currencyEntity = currencyEntity;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getUnitPriceUnscaled() {
    return unitPriceUnscaled;
  }

  public void setUnitPriceUnscaled(int unitPriceUnscaled) {
    this.unitPriceUnscaled = unitPriceUnscaled;
  }

  public Integer getFixedPriceUnscaled() {
    return fixedPriceUnscaled;
  }

  public void setFixedPriceUnscaled(int fixedPriceUnscaled) {
    this.fixedPriceUnscaled = fixedPriceUnscaled;
  }

  public CurrencyEntity getCurrencyEntity() {
    return currencyEntity;
  }

  public void setCurrencyEntity(CurrencyEntity currencyEntity) {
    this.currencyEntity = currencyEntity;
  }
}
