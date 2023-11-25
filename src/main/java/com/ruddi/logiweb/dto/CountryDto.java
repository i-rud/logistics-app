package com.ruddi.logiweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CountryDto {
    private int id;
    private String departure;
    private String destination;
    private int distance;
}
