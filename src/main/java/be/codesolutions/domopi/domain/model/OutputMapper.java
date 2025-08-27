package be.codesolutions.domopi.domain.model;

import be.codesolutions.domopi.infrastructure.persistence.entity.OutputEntity;

public final class OutputMapper {

    private OutputMapper() {
        // Utility class
    }

    public static Output fromEntity(OutputEntity entity) {
        return new Output(
            entity.getId(),
            entity.getName(),
            entity.getAddress(),
            entity.getOperationMode(),
            entity.getOutputType()
        );
    }
}