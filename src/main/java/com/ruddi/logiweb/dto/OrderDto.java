package com.ruddi.logiweb.dto;

import com.ruddi.logiweb.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private int id;
    private OrderStatus status;
    private int tripTime;
    private int distance;
    private int totalWeight;
    private String departure;
    private String destination;
    private String path;
    private List<CargoDto> cargos = new ArrayList<>();
    private List<Integer> cargosId = new ArrayList<>();
    private TruckDto truck;
    private int truckInWork;
    private List<DriverDto> drivers = new ArrayList<>();
    private int requiredDrivers;
    private List<Integer> driversId = new ArrayList<>();

    public int getRequiredDrivers() {
        if (truck != null)
            return (int) Math.ceil((double) tripTime / (double) truck.getWorkshift());
        return 0;
    }

    public boolean has(String username) {
        for (DriverDto driver : drivers) {
            if (driver.getEmail().equals(username))
                return true;
        }

        return false;
    }
}
