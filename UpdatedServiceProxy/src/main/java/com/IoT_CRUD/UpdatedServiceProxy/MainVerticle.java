package com.IoT_CRUD.UpdatedServiceProxy;
import com.IoT_CRUD.UpdatedServiceProxy.APIs.APIVerticle;
import com.IoT_CRUD.UpdatedServiceProxy.service.PostgresVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    // Deploying PostgresVerticle first.....
    vertx.deployVerticle(new PostgresVerticle(), res -> {
      if (res.succeeded()) {
        logger.info("PostgresVerticle deployed successfully.");

        // Once PostgresVerticle is deployed, deploying APIVerticle.....
        vertx.deployVerticle(new APIVerticle(), result -> {
          if (result.succeeded()) {
            logger.info("APIVerticle deployed successfully.");
          } else {
            logger.error("Failed to deploy APIVerticle: " + result.cause());
          }
        });

      } else {
        logger.error("Failed to deploy PostgresVerticle: " + res.cause());
      }
    });
  }
}
