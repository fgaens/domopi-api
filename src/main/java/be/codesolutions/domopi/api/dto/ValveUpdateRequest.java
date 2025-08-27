package be.codesolutions.domopi.api.dto;

import com.pi4j.io.gpio.digital.DigitalState;

public record ValveUpdateRequest(DigitalState state) {
}