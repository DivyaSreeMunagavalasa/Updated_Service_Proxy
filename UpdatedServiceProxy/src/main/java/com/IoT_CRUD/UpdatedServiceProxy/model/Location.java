package com.IoT_CRUD.UpdatedServiceProxy.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;

public class Location {
  private String type;
  private double[] coordinates;

  public Location() {}

  public Location(String type, double[] coordinates) {
    this.type = type;
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(double[] coordinates) {
    this.coordinates = coordinates;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject()
      .put("type", type)
      .put("coordinates", new JsonArray(Arrays.toString(coordinates)));
    return json;
  }
}
