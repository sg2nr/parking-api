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
@Table(name = "parkings")
public class ParkingEntity {

  @Id
  @GeneratedValue(strategy=IDENTITY)
  private long id;

  @Column(nullable = false, name = "parking_name")
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @ManyToOne
  @JoinColumn(name = "pricing_policy")
  private PricingPolicyEntity pricingPolicyEntity;

  public ParkingEntity() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public PricingPolicyEntity getPricingPolicyEntity() {
    return pricingPolicyEntity;
  }

  public void setPricingPolicyEntity(PricingPolicyEntity pricingPolicyEntity) {
    this.pricingPolicyEntity = pricingPolicyEntity;
  }
}
