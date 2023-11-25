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
@Table(name = "cargos")
public class Cargo {
    @Id
    @GeneratedValue
    @Column(name = "cargo_id")
    private int id;

    @Column(name = "cargo_title")
    private String title;

    @Column(name = "cargo_mass")
    private long mass;

    @Column(name="cargo_departure")
    @NotNull
    private String departure;

    @Column(name="cargo_destination")
    @NotNull
    private String destination;

    @Column(name = "cargo_status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private CargoStatus status;

    public String idAsString() {
        return (title + id).trim();
    }
}
