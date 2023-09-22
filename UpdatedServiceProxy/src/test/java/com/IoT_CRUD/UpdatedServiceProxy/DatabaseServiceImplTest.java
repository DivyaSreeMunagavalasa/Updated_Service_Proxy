package com.IoT_CRUD.UpdatedServiceProxy;

import com.IoT_CRUD.UpdatedServiceProxy.service.DatabaseServiceImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DatabaseServiceImplTest {

  private SQLClient sqlClient;
  private DatabaseServiceImpl databaseService;

  @BeforeEach
  public void setup() {
    sqlClient = mock(SQLClient.class);
    databaseService = new DatabaseServiceImpl(sqlClient);
  }

  @Test
  public void testAddDevice() {
    SQLConnection connection = mock(SQLConnection.class);
    when(sqlClient.getConnection(any())).thenAnswer(inv -> {
      Handler<AsyncResult<SQLConnection>> handler = inv.getArgument(0);
      handler.handle(Future.succeededFuture(connection));
      return null;
    });

    UpdateResult updateResult = mock(UpdateResult.class);
    when(connection.updateWithParams(anyString(), any(), any())).thenAnswer(inv -> {
      Handler<AsyncResult<UpdateResult>> handler = inv.getArgument(2);
      handler.handle(Future.succeededFuture(updateResult));
      return null;
    });

    JsonObject deviceData = new JsonObject()
      .put("deviceId", "782-tnssdc-789")
      .put("domain", "smart-irrigation")
      .put("state", "TN")
      .put("city", "Chennai")
      .put("location", new JsonObject().put("type", "point").put("coordinates", new JsonArray().add(44.32).add(90.15)))
      .put("deviceType", "smart-phone");
    databaseService.addDevice(deviceData, result -> {
      assertTrue(result.succeeded());
      assertEquals("Device added successfully", result.result());
    });
  }

  @Test
  public void testUpdateDevice() {
    SQLConnection connection = mock(SQLConnection.class);
    when(sqlClient.getConnection(any())).thenAnswer(inv -> {
      Handler<AsyncResult<SQLConnection>> handler = inv.getArgument(0);
      handler.handle(Future.succeededFuture(connection));
      return null;
    });

    UpdateResult updateResult = mock(UpdateResult.class);
    when(connection.updateWithParams(anyString(), any(), any())).thenAnswer(inv -> {
      Handler<AsyncResult<UpdateResult>> handler = inv.getArgument(2);
      handler.handle(Future.succeededFuture(updateResult));
      return null;
    });

    JsonObject deviceData = new JsonObject()
      .put("deviceId", "789-tnssdc-789")
      .put("domain", "smart-irrigation")
      .put("state", "TN")
      .put("city", "Chennai")
      .put("location", new JsonObject().put("type", "point").put("coordinates", new JsonArray().add(44.32).add(90.15)))
      .put("deviceType", "smart-tv");

    databaseService.updateDevice(deviceData, result -> {
      assertTrue(result.succeeded());
      assertEquals("Device updated successfully", result.result());
    });
  }

  @Test
  public void testGetDevice() {
    SQLConnection connection = mock(SQLConnection.class);
    when(sqlClient.getConnection(any())).thenAnswer(inv -> {
      Handler<AsyncResult<SQLConnection>> handler = inv.getArgument(0);
      handler.handle(Future.succeededFuture(connection));
      return null;
    });

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getRows()).thenReturn(Collections.singletonList(new JsonObject().put("deviceId", "123-asdasd-123")));
    when(connection.queryWithParams(anyString(), any(), any())).thenAnswer(inv -> {
      Handler<AsyncResult<ResultSet>> handler = inv.getArgument(2);
      handler.handle(Future.succeededFuture(resultSet));
      return null;
    });

    databaseService.getDevice("123-asdasd-123", result -> {
      assertTrue(result.succeeded());
      assertEquals("123-asdasd-123", result.result().getString("deviceId"));
    });
  }

  @Test
  public void testDeleteDevice() {
    SQLConnection connection = mock(SQLConnection.class);
    when(sqlClient.getConnection(any())).thenAnswer(inv -> {
      Handler<AsyncResult<SQLConnection>> handler = inv.getArgument(0);
      handler.handle(Future.succeededFuture(connection));
      return null;
    });

    UpdateResult updateResult = mock(UpdateResult.class);
    when(connection.updateWithParams(anyString(), any(), any())).thenAnswer(inv -> {
      Handler<AsyncResult<UpdateResult>> handler = inv.getArgument(2);
      handler.handle(Future.succeededFuture(updateResult));
      return null;
    });

    databaseService.deleteDevice("010-pjbcdc-010", result -> {
      assertTrue(result.succeeded());
      assertEquals("Device deleted successfully", result.result());
    });
  }
}
