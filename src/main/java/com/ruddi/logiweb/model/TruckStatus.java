package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TruckStatus {
    FREE("Free"),
    BOOKED("Booked"),
    BUSY("Busy");

    @Getter
    private final String status;
}
