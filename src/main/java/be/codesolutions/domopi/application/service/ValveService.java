package be.codesolutions.domopi.application.service;

import be.codesolutions.domopi.domain.event.ValveSwitchedEvent;
import be.codesolutions.domopi.domain.model.OperationMode;
import be.codesolutions.domopi.domain.model.Output;
import be.codesolutions.domopi.domain.service.OutputCacheRepository;
import be.codesolutions.domopi.util.Log;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ValveService {

    private final OutputCacheRepository outputCacheRepository;
    private final Event<ValveSwitchedEvent> valveSwitchedEvent;

    public ValveService(OutputCacheRepository outputCacheRepository, Event<ValveSwitchedEvent> valveSwitchedEvent) {
        this.outputCacheRepository = outputCacheRepository;
        this.valveSwitchedEvent = valveSwitchedEvent;
    }

    public void switchValve(String valveId, DigitalState state) {
        Output valve = outputCacheRepository.getById(valveId);

        if (valve.getDigitalOutput().state() != state) {
            Log.info("Switching valve {} to {}", Instant.now().toString(), valveId, state.toString());
            valve.setState(state);
            valveSwitchedEvent.fire(new ValveSwitchedEvent(valveId, state));
        }
    }

    public Output getValve(String valveId) {
        return outputCacheRepository.getById(valveId);
    }

    public List<Output> getValves() {
        return outputCacheRepository.getValves();
    }

    public Optional<OperationMode> getCurrentOperationMode() {
        Set<OperationMode> currentOperationModes = getValves().stream()
                .filter(Output::isActive)
                .map(Output::getOperationMode)
                .collect(Collectors.toSet());

        if (currentOperationModes.size() > 1) {
            Log.warn("Multiple active operation modes detected");
            throw new IllegalStateException("Multiple active operation modes detected");
        } else if (currentOperationModes.isEmpty()) {
            return Optional.empty();
        }
        return currentOperationModes.stream().findFirst();
    }
}