package be.codesolutions.domopi.domain.event;

import com.pi4j.io.gpio.digital.DigitalState;

public record ValveSwitchedEvent(String valveId, DigitalState state) {
}