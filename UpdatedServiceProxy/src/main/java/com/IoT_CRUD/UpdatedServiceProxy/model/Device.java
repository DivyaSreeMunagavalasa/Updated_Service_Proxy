package com.IoT_CRUD.UpdatedServiceProxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
  @JsonProperty("deviceId")
  private String deviceId;

  @JsonProperty("domain")
  private String domain;

  @JsonProperty("state")
  private String state;

  @JsonProperty("city")
  private String city;

  @JsonProperty("location")
  private Location location;

  @JsonProperty("deviceType")
  private String deviceType;

  public Device() {
    // Default constructor
  }

  public Device(String deviceId, String domain, String state, String city, Location location, String deviceType) {
    this.deviceId = deviceId;
    this.domain = domain;
    this.state = state;
    this.city = city;
    this.location = location;
    this.deviceType = deviceType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public JsonObject toJson() {
    return JsonObject.mapFrom(this);
  }
}
