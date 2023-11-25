package com.ruddi.logiweb.controller.rest;

import com.ruddi.logiweb.model.rest.DriverRest;
import com.ruddi.logiweb.model.rest.OrderRest;
import com.ruddi.logiweb.model.rest.TruckRest;
import com.ruddi.logiweb.service.api.DriverService;
import com.ruddi.logiweb.service.api.OrderService;
import com.ruddi.logiweb.service.api.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rest controller for reaching orders, drivers and trucks
 * @author Ivan Rud
 */
@RestController("/orders")
public class LogiwebRestController {

    /**
     * service for working with order table
     */
    @Autowired
    OrderService orderService;

    /**
     * service for working with truck table
     */
    @Autowired
    TruckService truckService;

    /**
     * service for working with driver table
     */
    @Autowired
    DriverService driverService;

    @Autowired
    ModelMapper mapper;

    /**
     * /orders rest request
     * @return json with oders list
     */
    @GetMapping("/orders")
    public List<OrderRest> orders() {
        return orderService.getAll().stream()
                .map(order ->
                mapper.map(order, OrderRest.class)).collect(Collectors.toList());
    }

    /**
     * /drivers rest request
     * @return json with drivers list
     */
    @GetMapping("/drivers/list")
    public List<DriverRest> drivers() {
        return driverService.getAll().stream()
                .map(driver ->
                        mapper.map(driver, DriverRest.class)).collect(Collectors.toList());
    }

    /**
     * /trucks rest request
     * @return json with trucks list
     */
    @GetMapping("/trucks/list")
    public List<TruckRest> trucks() {
        return truckService.getAll().stream()
                .map(truck ->
                        mapper.map(truck, TruckRest.class)).collect(Collectors.toList());
    }
}
