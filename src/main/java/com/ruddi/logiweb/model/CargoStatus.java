package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum CargoStatus {
    PREPARED("Prepared for dispatch"),
    IN_ORDER("In order"),
    DISPATCHED("Dispatched"),
    DELIEVERED("Delivered");

    @Setter
    @Getter
    private String status;
}
