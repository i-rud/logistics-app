package com.ruddi.logiweb.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRest {
    private int id;
    private String departure;
    private String destination;
    private int totalWeight;
    private String status;
}
