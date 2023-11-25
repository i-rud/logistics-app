package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatus {
    PREPARING("Preparing"),
    READY("Ready to take off"),
    IN_PROGRESS("In progress"),
    CLOSED("Completed");

    @Getter
    private String status;
}
