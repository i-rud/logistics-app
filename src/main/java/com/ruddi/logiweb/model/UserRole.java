package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum UserRole {
    MANAGER("MANAGER"),
    DRIVER("DRIVER");

    @Setter
    private String role;
}
