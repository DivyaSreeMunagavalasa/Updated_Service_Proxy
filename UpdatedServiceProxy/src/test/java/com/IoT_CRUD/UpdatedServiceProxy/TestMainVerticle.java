package com.IoT_CRUD.UpdatedServiceProxy;
import com.IoT_CRUD.UpdatedServiceProxy.APIs.APIVerticle;
import com.IoT_CRUD.UpdatedServiceProxy.service.PostgresVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  private static final Logger logger = LoggerFactory.getLogger(TestMainVerticle.class);

  @Test
  public static void main(String[] args) {
    // Configure Vert.x options for testing (e.g., clustered mode)
    VertxOptions vertxOptions = new VertxOptions();

    // Create a Vert.x instance
    Vertx vertx = Vertx.vertx(vertxOptions);

    // Deploy your Verticles for testing
    vertx.deployVerticle(new PostgresVerticle(), ar -> {
      if (ar.succeeded()) {
        logger.info("PostgresVerticle deployed successfully.");

        vertx.deployVerticle(new APIVerticle(), result -> {
          if (result.succeeded()) {
            logger.info("APIVerticle deployed successfully.");

            // Directly run your tests here
            runYourTests(vertx);
          } else {
            logger.error("Failed to deploy APIVerticle: " + result.cause());
            vertx.close();
          }
        });
      } else {
        logger.error("Failed to deploy PostgresVerticle: " + ar.cause());
        vertx.close();
      }
    });
  }

  private static void runYourTests(Vertx vertx) {
    // When all tests are complete, close the Vert.x instance
    vertx.close();
  }
}
