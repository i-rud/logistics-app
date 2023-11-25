package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Condition {
    OK("OK"),
    DEFECTIVE("Defective");

    @Getter
    private final String condition;
}
