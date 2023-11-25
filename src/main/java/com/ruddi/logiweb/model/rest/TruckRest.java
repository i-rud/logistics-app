package com.ruddi.logiweb.model.rest;

import com.ruddi.logiweb.model.Condition;
import com.ruddi.logiweb.model.TruckStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TruckRest {
    private int id;
    private String plate;
    private int workshift;
    private int capacity;
    private String condition;
    private String city;
    private String status;
}
