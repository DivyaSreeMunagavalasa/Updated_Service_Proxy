package com.IoT_CRUD.UpdatedServiceProxy.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class DatabaseServiceImpl implements DatabaseServiceProxy {

  private SQLClient sqlClient;
  public DatabaseServiceImpl(SQLClient sqlClient) {
    this.sqlClient = sqlClient;
  }
  public DatabaseServiceImpl(io.vertx.core.Vertx vertx, JsonObject config) {
    this.sqlClient = JDBCClient.createShared(vertx, config);
  }

  @Override
  public void addDevice(JsonObject deviceData, Handler<AsyncResult<String>> resultHandler) {
    sqlClient.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.updateWithParams(
          "INSERT INTO devices (\"deviceId\", domain, state, city, location, \"deviceType\") VALUES (?, ?, ?, ?, ?::json, ?)",
          new JsonArray().add(deviceData.getString("deviceId"))
            .add(deviceData.getString("domain"))
            .add(deviceData.getString("state"))
            .add(deviceData.getString("city"))
            .add(deviceData.getJsonObject("location").toString())
            .add(deviceData.getString("deviceType")),
          insertResult -> {
            if (insertResult.succeeded()) {
              resultHandler.handle(Future.succeededFuture("Device added successfully"));
            } else {
              resultHandler.handle(Future.failedFuture("Error while adding device"));
            }
            connection.close();
          }
        );
      } else {
        resultHandler.handle(Future.failedFuture("Failed to connect to the database"));
      }
    });
  }

  @Override
  public void updateDevice(JsonObject deviceData, Handler<AsyncResult<String>> resultHandler) {
    String deviceId = deviceData.getString("deviceId");
    sqlClient.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.updateWithParams(
          "UPDATE devices SET domain = ?, state = ?, city = ?, location = ?::json,\"deviceType\" = ? WHERE \"deviceId\" = ?",
          new JsonArray()
            .add(deviceData.getString("domain"))
            .add(deviceData.getString("state"))
            .add(deviceData.getString("city"))
            .add(deviceData.getJsonObject("location").toString())
            .add(deviceData.getString("deviceType"))
            .add(deviceId),
          updateResult -> {
            if (updateResult.succeeded()) {
              resultHandler.handle(Future.succeededFuture("Device updated successfully"));
            } else {
              resultHandler.handle(Future.failedFuture("Error while updating device"));
            }
            connection.close();
          }
        );
      } else {
        resultHandler.handle(Future.failedFuture("Failed to connect to the database"));
      }
    });
  }

  @Override
  public void getDevice(String deviceId, Handler<AsyncResult<JsonObject>> resultHandler) {
    sqlClient.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.queryWithParams(
          "SELECT \"deviceId\", domain, state, city, location::json, \"deviceType\" FROM devices WHERE \"deviceId\" = ?",
          new JsonArray().add(deviceId),
          queryResult -> {
            if (queryResult.succeeded()) {
              if (!queryResult.result().getRows().isEmpty()) {
                resultHandler.handle(Future.succeededFuture(queryResult.result().getRows().get(0)));
              } else {
                resultHandler.handle(Future.failedFuture("Device not found"));
              }
            } else {
              resultHandler.handle(Future.failedFuture("Error while retrieving device"));
            }
            connection.close();
          }
        );
      } else {
        resultHandler.handle(Future.failedFuture("Failed to connect to the database"));
      }
    });
  }

  @Override
  public void deleteDevice(String deviceId, Handler<AsyncResult<String>> resultHandler) {
    sqlClient.getConnection(res -> {
      if (res.succeeded()) {
        SQLConnection connection = res.result();
        connection.updateWithParams(
          "DELETE FROM devices WHERE \"deviceId\" = ?",
          new JsonArray().add(deviceId),
          deleteResult -> {
            if (deleteResult.succeeded()) {
              resultHandler.handle(Future.succeededFuture("Device deleted successfully"));
            } else {
              resultHandler.handle(Future.failedFuture("Error while deleting device"));
            }
            connection.close();
          }
        );
      } else {
        resultHandler.handle(Future.failedFuture("Failed to connect to the database"));
      }
    });
  }
}
