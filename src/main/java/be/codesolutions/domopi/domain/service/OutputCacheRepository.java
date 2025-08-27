package be.codesolutions.domopi.domain.service;

import be.codesolutions.domopi.domain.model.OutputType;
import be.codesolutions.domopi.domain.model.OperationMode;
import be.codesolutions.domopi.domain.model.Output;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class OutputCacheRepository {

    private final Map<String, Output> outputCache = new ConcurrentHashMap<>();

    public List<Output> getValves() {
        return outputCache.values().stream()
                .filter(output -> output.getOutputType() == OutputType.VALVE)
                .toList();
    }

    public List<Output> getPumps() {
        return outputCache.values().stream()
                .filter(output -> output.getOutputType() == OutputType.PUMP)
                .toList();
    }

    public Output getById(String outputId) {
        return outputCache.get(outputId);
    }

    public Output getPumpByOperationMode(OperationMode operationMode) {
        return outputCache.values().stream()
                .filter(output -> output.getOutputType() == OutputType.PUMP)
                .filter(output -> output.getOperationMode() == operationMode)
                .findAny().orElseThrow(() ->
                        new IllegalArgumentException(String.format("Could not find pump with type %s", operationMode)));
    }

    public void addToCache(Output output) {
        outputCache.put(output.getId(), output);
    }
}