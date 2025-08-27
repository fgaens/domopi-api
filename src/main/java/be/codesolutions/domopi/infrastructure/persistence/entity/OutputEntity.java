package be.codesolutions.domopi.infrastructure.persistence.entity;

import be.codesolutions.domopi.domain.model.OperationMode;
import be.codesolutions.domopi.domain.model.OutputType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "output")
public class OutputEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationMode operationMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutputType outputType;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAddress() {
        return address;
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public OutputType getOutputType() {
        return outputType;
    }
}