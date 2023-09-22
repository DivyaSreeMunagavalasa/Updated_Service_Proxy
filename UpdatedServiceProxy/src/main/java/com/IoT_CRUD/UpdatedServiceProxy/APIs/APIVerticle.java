package com.IoT_CRUD.UpdatedServiceProxy.APIs;

import com.IoT_CRUD.UpdatedServiceProxy.service.DatabaseServiceProxy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class APIVerticle extends AbstractVerticle {

  private DatabaseServiceProxy databaseServiceProxy;
  public APIVerticle(DatabaseServiceProxy proxy) {
    this.databaseServiceProxy = proxy;
  }
  public APIVerticle() {
    this.databaseServiceProxy = DatabaseServiceProxy.createProxy(vertx, "database-service-address");
  }
  @Override
  public void start(Promise<Void> startPromise) {

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.post("/devices").handler(this::addDevice);
    router.put("/devices/:deviceId").handler(this::updateDevice);
    router.get("/devices/:deviceId").handler(this::getDevice);
    router.delete("/devices/:deviceId").handler(this::deleteDevice);

    vertx.createHttpServer().requestHandler(router).listen(8080, result -> {
      if (result.succeeded()) {
        startPromise.complete();
      } else {
        startPromise.fail(result.cause());
      }
    });
  }

  private void addDevice(RoutingContext context) {
    JsonObject deviceData = context.getBodyAsJson();
    //System.out.println(deviceData.encodePrettily());
    databaseServiceProxy.addDevice(deviceData, reply -> {
      if(reply.succeeded()) {
        context.response().setStatusCode(201).end(reply.result());
      } else {
        System.err.println("Error: " + reply.cause().getMessage());  // Print the error
        context.response().setStatusCode(500).end(reply.cause().getMessage());
      }
    });
  }

  private void updateDevice(RoutingContext context) {
    String deviceId = context.request().getParam("deviceId");
    JsonObject updatedData = context.getBodyAsJson().put("deviceId", deviceId);
    databaseServiceProxy.updateDevice(updatedData, reply -> {
      if(reply.succeeded()) {
        context.response().setStatusCode(204).end();
      } else {
        context.response().setStatusCode(500).end(reply.cause().getMessage());
      }
    });
  }

  private void getDevice(RoutingContext context) {
    String deviceId = context.request().getParam("deviceId");
    databaseServiceProxy.getDevice(deviceId, reply -> {
      if(reply.succeeded()) {
        context.response().setStatusCode(200).end(reply.result().encode());
      } else {
        context.response().setStatusCode(404).end(reply.cause().getMessage());
      }
    });
  }


  private void deleteDevice(RoutingContext context) {
    String deviceId = context.request().getParam("deviceId");
    databaseServiceProxy.deleteDevice(deviceId, reply -> {
      if(reply.succeeded()) {
        context.response().setStatusCode(204).end();
      } else {
        context.response().setStatusCode(500).end(reply.cause().getMessage());
      }
    });
  }
}
