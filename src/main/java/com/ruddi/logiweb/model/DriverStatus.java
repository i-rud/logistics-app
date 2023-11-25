package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DriverStatus {
    RESTING("Resting"),
    ON_SHIFT("On shift"),
    DRIVING("Driving");

    @Getter
    private final String status;
}
