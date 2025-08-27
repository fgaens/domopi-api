package be.codesolutions.domopi.api.resource;

import be.codesolutions.domopi.api.dto.ValveUpdateRequest;
import be.codesolutions.domopi.domain.model.Output;
import be.codesolutions.domopi.domain.service.OutputCacheRepository;
import be.codesolutions.domopi.infrastructure.gpio.GpioInitializer;
import com.pi4j.io.gpio.digital.DigitalState;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(ValveResource.class)
class ValveResourceIT {

    private static final String COOLING_TEST_VALVE_ID = "zk_co";

    @Inject
    OutputCacheRepository outputCacheRepository;

    @Inject
    GpioInitializer gpioInitializer;

    @BeforeEach
    void setUp() {
        gpioInitializer.reset();
    }

    @Test
    void updateValve_withoutAuthentication_shouldReturn401() {
        ValveUpdateRequest request = new ValveUpdateRequest(DigitalState.LOW);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{valveId}", COOLING_TEST_VALVE_ID)
                .then()
                .statusCode(401);
    }

    @Test
    void updateValve_withInvalidCredentials_shouldReturn401() {
        ValveUpdateRequest request = new ValveUpdateRequest(DigitalState.LOW);

        given()
                .auth().basic("invalid", "wrong")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{valveId}", COOLING_TEST_VALVE_ID)
                .then()
                .statusCode(401);
    }

    @Test
    void updateValve_withAdminCredentials_shouldReturn200() {
        Output output = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(output.getDigitalOutput().state()).isEqualTo(DigitalState.HIGH);

        ValveUpdateRequest request = new ValveUpdateRequest(DigitalState.LOW);
        given()
                .auth().basic("user1", "user1_pw")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{valveId}", COOLING_TEST_VALVE_ID)
                .then()
                .statusCode(200);

        Output savedOutput = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(savedOutput.getDigitalOutput().state()).isEqualTo(DigitalState.LOW);
    }

    @Test
    void updateValve_withUserCredentials_shouldReturn200() {
        Output output = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(output.getDigitalOutput().state()).isEqualTo(DigitalState.HIGH);

        ValveUpdateRequest request = new ValveUpdateRequest(DigitalState.LOW);
        given()
                .auth().basic("user2", "user2_pw")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/{valveId}", COOLING_TEST_VALVE_ID)
                .then()
                .statusCode(200);

        Output savedOutput = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(savedOutput.getDigitalOutput().state()).isEqualTo(DigitalState.LOW);
    }
}