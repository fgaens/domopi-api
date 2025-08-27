package be.codesolutions.domopi.domain.model;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

import java.util.Objects;

public class Output {

    private final String id;
    private final String name;
    private final int address;
    private final OperationMode operationMode;
    private final OutputType outputType;

    private DigitalOutput digitalOutput;

    public Output(String id, String name, int address, OperationMode operationMode, OutputType outputType) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.operationMode = operationMode;
        this.outputType = outputType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAddress() {
        return address;
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public DigitalOutput getDigitalOutput() {
        return digitalOutput;
    }

    public void setDigitalOutput(DigitalOutput digitalOutput) {
        this.digitalOutput = digitalOutput;
    }

    public void setState(DigitalState state) {
        if (digitalOutput != null) {
            digitalOutput.state(state);
        }
    }

    public boolean isActive() {
        return digitalOutput != null && digitalOutput.state() == DigitalState.LOW;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Output output = (Output) o;
        return address == output.address &&
                Objects.equals(id, output.id) &&
                Objects.equals(name, output.name) &&
                operationMode == output.operationMode &&
                outputType == output.outputType &&
                Objects.equals(digitalOutput, output.digitalOutput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, operationMode, outputType, digitalOutput);
    }

    @Override
    public String toString() {
        return "Output{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", operationMode=" + operationMode +
                ", outputType=" + outputType +
                ", digitalOutput=" + digitalOutput +
                '}';
    }
}