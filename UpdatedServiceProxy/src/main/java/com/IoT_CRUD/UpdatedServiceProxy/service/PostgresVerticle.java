package com.IoT_CRUD.UpdatedServiceProxy.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;

public class PostgresVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    // Database configuration
    JsonObject config = new JsonObject()
      .put("url", "jdbc:postgresql://localhost:5432/devices")
      .put("driver_class", "org.postgresql.Driver")
      .put("user", "postgres")
      .put("password", "Postgres@12");

    // Create service instance
    DatabaseServiceProxy databaseService = new DatabaseServiceImpl(vertx, config);

    // Register the service proxy
    new ServiceBinder(vertx)
      .setAddress("database-service-address")
      .register(DatabaseServiceProxy.class, databaseService);
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    // Clean up any resources or bindings when verticle is undeployed
    stopPromise.complete();
  }
}
