package com.IoT_CRUD.UpdatedServiceProxy.service;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@ProxyGen
@VertxGen
public interface DatabaseServiceProxy {
@GenIgnore
  static DatabaseServiceProxy createProxy(Vertx vertx, String address) {
    return new DatabaseServiceProxyVertxEBProxy(vertx, address);
  }

  // Add a new device to the database
  void addDevice(JsonObject deviceData, Handler<AsyncResult<String>> resultHandler);

  // Update device details in the database
  void updateDevice(JsonObject deviceData, Handler<AsyncResult<String>> resultHandler);

  // Fetch device details from the database
  void getDevice(String deviceId, Handler<AsyncResult<JsonObject>> resultHandler);

  // Remove a device from the database
  void deleteDevice(String deviceId, Handler<AsyncResult<String>> resultHandler);
}
