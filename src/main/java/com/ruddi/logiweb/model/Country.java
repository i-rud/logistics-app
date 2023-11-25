package com.ruddi.logiweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="distances")
public class Country {
    @Id
    @GeneratedValue
    @Column(name="pair_id")
    private int id;

    @Column(name="from_city")
    private String departure;

    @Column(name="to_city")
    private String destination;

    @Column(name="distance")
    private int distance;

}
