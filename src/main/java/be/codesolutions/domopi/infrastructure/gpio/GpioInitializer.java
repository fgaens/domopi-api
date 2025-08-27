package be.codesolutions.domopi.infrastructure.gpio;

import be.codesolutions.domopi.domain.model.Output;
import be.codesolutions.domopi.domain.model.OutputMapper;
import be.codesolutions.domopi.domain.service.OutputCacheRepository;
import be.codesolutions.domopi.infrastructure.persistence.repository.OutputRepository;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup
public class GpioInitializer {

    private Context pi4j;

    private final OutputRepository outputRepository;
    private final OutputCacheRepository outputCacheRepository;

    public GpioInitializer(final OutputCacheRepository outputCacheRepository, final OutputRepository outputRepository) {
        this.outputCacheRepository = outputCacheRepository;
        this.outputRepository = outputRepository;

        initializePi4J();
        provisionOutputPins();
    }

    public void reset() {
        pi4j.shutdown();
        initializePi4J();
        provisionOutputPins();
    }

    private void initializePi4J() {
        this.pi4j = Pi4J.newAutoContext();
    }

    private void provisionOutputPins() {
        outputRepository.findAll().stream()
                .map(OutputMapper::fromEntity)
                .forEach(this::initializeDigitalOutput);
    }

    private void initializeDigitalOutput(Output output) {
        DigitalOutput digitalOutput = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id(output.getId())
                .name(output.getName())
                .address(output.getAddress())
                .shutdown(DigitalState.HIGH)
                .initial(DigitalState.HIGH));
        output.setDigitalOutput(digitalOutput);
        outputCacheRepository.addToCache(output);
    }
}