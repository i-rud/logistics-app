package com.ruddi.logiweb.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue
    @Column(name = "driver_id")
    private int id;

    @Column(name="driver_first_name")
    private String firstName;

    @Column(name="driver_second_name")
    private String secondName;

    @Column(name="driver_email", unique = true)
    private String email;

    @Column(name="driver_hours_worked")
    private int hoursWorked;

    @Column(name="driver_status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private DriverStatus status;

    @Column(name="driver_city")
    @NotNull
    private String city;

    @Column(name = "driver_telegram")
    private String telegram;

    public String idAsString() {return (id + firstName).trim();}
}
