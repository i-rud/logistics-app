package com.ruddi.logiweb.dto;

import com.ruddi.logiweb.model.CargoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CargoDto {
    private int id;
    private String title;
    private long mass;
    private String departure;
    private String destination;
    private CargoStatus status;

    public String idAsString() {
        return (title + id).trim();
    }
}
