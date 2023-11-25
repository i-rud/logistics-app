package com.ruddi.logiweb.dto;

import com.ruddi.logiweb.model.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private int id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private int hoursWorked;
    private DriverStatus status;
    private String city;
    private String telegram;

    public String idAsString() {return (id + firstName).trim();}
}
