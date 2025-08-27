package be.codesolutions.domopi.service;

import be.codesolutions.domopi.infrastructure.gpio.GpioInitializer;
import be.codesolutions.domopi.domain.model.Output;
import be.codesolutions.domopi.domain.service.OutputCacheRepository;
import be.codesolutions.domopi.application.service.ValveService;
import com.pi4j.io.gpio.digital.DigitalState;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class OutputServiceTest {

    private static final String COOLING_TEST_VALVE_ID = "zk_co";

    @Inject
    GpioInitializer gpioInitializer;

    @Inject
    OutputCacheRepository outputCacheRepository;

    @Inject
    ValveService valveService;

    @BeforeEach
    void setUp() {
        gpioInitializer.reset();
    }

    @Test
    void switchValve() {
        // Given
        Output output = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(output.getDigitalOutput().state()).isEqualTo(DigitalState.HIGH);

        // When
        valveService.switchValve(output.getId(), DigitalState.LOW);

        // Then
        Output savedOutput = outputCacheRepository.getById(COOLING_TEST_VALVE_ID);
        assertThat(savedOutput.getDigitalOutput().state()).isEqualTo(DigitalState.LOW);
    }
}