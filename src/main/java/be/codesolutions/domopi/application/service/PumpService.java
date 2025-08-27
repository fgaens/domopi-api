package be.codesolutions.domopi.application.service;

import be.codesolutions.domopi.domain.event.ValveSwitchedEvent;
import be.codesolutions.domopi.domain.model.OperationMode;
import be.codesolutions.domopi.domain.model.Output;
import be.codesolutions.domopi.domain.service.OutputCacheRepository;
import be.codesolutions.domopi.util.Log;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.locks.LockSupport;

@ApplicationScoped
public class PumpService {

    private static final long SLEEP_TIME_MILLIS = 120_000L;

    private final OutputCacheRepository outputCacheRepository;
    private final ValveService valveService;

    public PumpService(OutputCacheRepository outputCacheRepository, ValveService valveService) {
        this.outputCacheRepository = outputCacheRepository;
        this.valveService = valveService;
    }


    public void switchPump(OperationMode operationMode, DigitalState state) {
        Output pump = outputCacheRepository.getPumpByOperationMode(operationMode);

        if (pump.getDigitalOutput().state() != state) {
            Log.info("Switching pump {} to {}", Instant.now().toString(), pump.getId(), state.toString());
            LockSupport.parkNanos(SLEEP_TIME_MILLIS * 1000 * 1000);
            pump.setState(state);
        }
    }

    public void onValveSwitched(@Observes ValveSwitchedEvent event) {
        Optional<OperationMode> currentOperationModeOptional = valveService.getCurrentOperationMode();
        Output valve = valveService.getValve(event.valveId());

        if (currentOperationModeOptional.isEmpty()) {
            switchPump(valve.getOperationMode(), event.state());
        } else if (currentOperationModeOptional.get() != valve.getOperationMode()) {
            // TODO FGA: handle switching operation modes
            // switch off all active valves
            // switch off pump

        }
    }
}