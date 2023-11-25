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
@Table(name="trucks")
public class Truck {
    @Id
    @GeneratedValue
    @Column(name="truck_id")
    private int id;

    @Column(name="truck_plate", unique = true)
    private String plate;

    @Column(name="truck_workshift")
    private int workshift;

    @Column(name="truck_capacity")
    private int capacity;

    @Column(name="truck_condition")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Condition condition;

    @Column(name="current_city")
    @NotNull
    private String city;

    @Column(name="truck_status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private TruckStatus status;
}
