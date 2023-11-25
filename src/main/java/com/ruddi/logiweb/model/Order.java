package com.ruddi.logiweb.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private int id;

    @Column(name = "order_status")
    @NotNull
    private OrderStatus status;

    @Column(name="trip_time")
    private int tripTime;

    @Column(name="trip_distance")
    private int distance;

    @Column(name="total_weight")
    private int totalWeight;

    @Column(name="departure_city")
    private String departure;

    @Column(name="destination_city")
    private String destination;

    @Column(name="path")
    private String path;

    @OneToMany(cascade=CascadeType.MERGE)
    private List<Cargo> cargos = new ArrayList<>();

    @OneToOne(cascade=CascadeType.MERGE)
    private Truck truck;

    @OneToMany(cascade=CascadeType.MERGE)
    private List<Driver> drivers = new ArrayList<>();

}
