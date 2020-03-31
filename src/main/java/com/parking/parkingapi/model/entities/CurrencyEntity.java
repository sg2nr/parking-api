package com.parking.parkingapi.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
public class CurrencyEntity {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "decimal_places")
  private int decimalPlaces;

  public CurrencyEntity() {
  }

  public CurrencyEntity(String code, int decimalPlaces) {
    this.code = code;
    this.decimalPlaces = decimalPlaces;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public void setDecimalPlaces(int decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
  }
}
