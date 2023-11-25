package com.ruddi.logiweb.service.api;

import com.ruddi.logiweb.dto.OrderDto;
import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.exception.exceptions.NotEnoughDriversException;
import com.ruddi.logiweb.exception.exceptions.NullSourceException;
import com.ruddi.logiweb.model.Order;
import com.ruddi.logiweb.model.Truck;

public interface OrderService extends GenericService<Order, OrderDto>{
    void addTruck(OrderDto order) throws NullSourceException;
    void addDrivers(OrderDto order);
    void start(int id) throws NotEnoughDriversException;
    void complete(int id);
}
