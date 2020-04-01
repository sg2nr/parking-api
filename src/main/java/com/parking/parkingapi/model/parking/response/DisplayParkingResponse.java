package com.parking.parkingapi.model.parking.response;

/**
 * Response with Parking information for display operations
 */
public class DisplayParkingResponse {

  private long id;

  private String name;

  private String address;

  private String city;

  private ParkingStatistics statistics;

  public DisplayParkingResponse() {
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

  public ParkingStatistics getStatistics() {
    return statistics;
  }

  public void setStatistics(ParkingStatistics statistics) {
    this.statistics = statistics;
  }
}
