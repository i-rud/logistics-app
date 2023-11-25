package com.ruddi.logiweb.dto;

import com.ruddi.logiweb.model.Condition;
import com.ruddi.logiweb.model.TruckStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TruckDto {
    private int id;
    private String plate;
    private int workshift;
    private int capacity;
    private Condition condition;
    private String city;
    private TruckStatus status;
}
