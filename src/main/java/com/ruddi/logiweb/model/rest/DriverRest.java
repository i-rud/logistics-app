package com.ruddi.logiweb.model.rest;

import com.ruddi.logiweb.model.DriverStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DriverRest {
    private int id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private int hoursWorked;
    private String status;
    private String city;
    private String telegram;
}
