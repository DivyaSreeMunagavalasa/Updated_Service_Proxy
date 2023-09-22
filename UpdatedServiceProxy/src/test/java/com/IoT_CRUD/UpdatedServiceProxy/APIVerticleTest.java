package com.IoT_CRUD.UpdatedServiceProxy;
import com.IoT_CRUD.UpdatedServiceProxy.service.DatabaseServiceProxy;
import com.IoT_CRUD.UpdatedServiceProxy.APIs.APIVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(VertxExtension.class)
public class APIVerticleTest {

  @Test
  void testAddDevice(Vertx vertx, VertxTestContext testContext) {
    DatabaseServiceProxy mockedServiceProxy = Mockito.mock(DatabaseServiceProxy.class);
    APIVerticle verticle = new APIVerticle(mockedServiceProxy);
    vertx.deployVerticle(verticle, deploymentResult -> {

      WebClient client = WebClient.create(vertx);
      JsonObject deviceData = new JsonObject()
        .put("deviceId", "782-tnssdc-789")
        .put("domain", "smart-irrigation")
        .put("state", "TN")
        .put("city", "Chennai")
        .put("location", new JsonObject().put("type", "point").put("coordinates", new JsonArray().add(44.32).add(90.15)))
        .put("deviceType", "smart-phone");

      // Mock the service call
      doAnswer(invocation -> {
        Handler<AsyncResult<String>> handler = invocation.getArgument(1);
        handler.handle(Future.succeededFuture("Device added successfully"));
        return null;
      }).when(mockedServiceProxy).addDevice(any(), any());

      client.post(8080, "localhost", "/devices")
        .expect(ResponsePredicate.SC_CREATED)
        .sendJsonObject(deviceData, response -> {
          if (response.succeeded()) {
            testContext.completeNow();
          } else {
            testContext.failNow(response.cause());
          }
        });
    });
  }
  @Test
  void testUpdateDevice(Vertx vertx, VertxTestContext testContext) {
    DatabaseServiceProxy mockedServiceProxy = Mockito.mock(DatabaseServiceProxy.class);
    APIVerticle verticle = new APIVerticle(mockedServiceProxy);
    vertx.deployVerticle(verticle, deploymentResult -> {

      WebClient client = WebClient.create(vertx);
      JsonObject updatedData = new JsonObject()
        .put("deviceId", "789-tnssdc-789")
        .put("domain", "smart-irrigation")
        .put("state", "TN")
        .put("city", "Chennai")
        .put("location", new JsonObject().put("type", "point").put("coordinates", new JsonArray().add(44.32).add(90.15)))
        .put("deviceType", "smart-tv");

      // Mock the service call
      doAnswer(invocation -> {
        Handler<AsyncResult<String>> handler = invocation.getArgument(1);
        handler.handle(Future.succeededFuture("Device updated successfully"));
        return null;
      }).when(mockedServiceProxy).updateDevice(any(), any());

      client.put(8080, "localhost", "/devices/1234")
        .expect(ResponsePredicate.SC_NO_CONTENT)
        .sendJsonObject(updatedData, response -> {
          if (response.succeeded()) {
            testContext.completeNow();
          } else {
            testContext.failNow(response.cause());
          }
        });
    });
  }

  @Test
  void testGetDevice(Vertx vertx, VertxTestContext testContext) {
    DatabaseServiceProxy mockedServiceProxy = Mockito.mock(DatabaseServiceProxy.class);
    APIVerticle verticle = new APIVerticle(mockedServiceProxy);
    vertx.deployVerticle(verticle, deploymentResult -> {

      WebClient client = WebClient.create(vertx);

      // Mock the service call
      doAnswer(invocation -> {
        String deviceId = invocation.getArgument(0);
        Handler<AsyncResult<JsonObject>> handler = invocation.getArgument(1);
        handler.handle(Future.succeededFuture(new JsonObject().put("deviceId", deviceId).put("domain", "smart-irrigation")));
        return null;
      }).when(mockedServiceProxy).getDevice(anyString(), any());

      client.get(8080, "localhost", "/devices/123-asdasd-123")
        .expect(ResponsePredicate.SC_OK)
        .send(response -> {
          if (response.succeeded()) {
            testContext.completeNow();
          } else {
            testContext.failNow(response.cause());
          }
        });
    });
  }

  @Test
  void testDeleteDevice(Vertx vertx, VertxTestContext testContext) {
    DatabaseServiceProxy mockedServiceProxy = Mockito.mock(DatabaseServiceProxy.class);
    APIVerticle verticle = new APIVerticle(mockedServiceProxy);
    vertx.deployVerticle(verticle, deploymentResult -> {

      WebClient client = WebClient.create(vertx);

      // Mock the service call
      doAnswer(invocation -> {
        Handler<AsyncResult<String>> handler = invocation.getArgument(1);
        handler.handle(Future.succeededFuture("Device deleted successfully"));
        return null;
      }).when(mockedServiceProxy).deleteDevice(anyString(), any());

      client.delete(8080, "localhost", "/devices/010-pjbcdc-010")
        .expect(ResponsePredicate.SC_NO_CONTENT)
        .send(response -> {
          if (response.succeeded()) {
            testContext.completeNow();
          } else {
            testContext.failNow(response.cause());
          }
        });
    });
  }
}
