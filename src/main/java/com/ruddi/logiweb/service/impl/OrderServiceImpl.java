package com.ruddi.logiweb.service.impl;

import com.ruddi.logiweb.bl.GraphService;
import com.ruddi.logiweb.dao.api.OrderDao;
import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.dto.OrderDto;
import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.exception.exceptions.NotEnoughDriversException;
import com.ruddi.logiweb.exception.exceptions.NullSourceException;
import com.ruddi.logiweb.model.*;
import com.ruddi.logiweb.service.api.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * order service
 * @author Ivan Rud
 */
@Service
@Slf4j
public class OrderServiceImpl extends GenericServiceImpl<Order, OrderDto, OrderDao> implements OrderService {
    @Autowired
    CargoService cargoService;

    @Autowired
    CountryService countryService;

    @Autowired
    DriverService driverService;

    @Autowired
    TruckService truckService;

    @Autowired
    GraphService graphService;

    @Autowired
    public OrderServiceImpl(OrderDao dao, ModelMapper mapper) {
        super(Order.class, OrderDto.class, dao, mapper);
    }

    /**
     * saving order and calculate optimal path
     * @param order order to save
     */
    @Override
    @Transactional
    public void save(OrderDto order) {
        log.info("save(order) was called");
        List<Integer> ids = order.getCargosId();

        int totalWeight = 0;
        for (Integer id : ids) {
            CargoDto buf = cargoService.find(id);
            totalWeight += buf.getMass();
            buf.setStatus(CargoStatus.IN_ORDER);
            cargoService.update(buf);
            order.getCargos().add(cargoService.find(id));
        }

        List<String> optimalPath = graphService.getPath(order, countryService.getAll());
        int optimalCost = graphService.countCost(optimalPath, countryService.getAll());

        order.setTripTime(optimalCost / 50);
        order.setDistance(optimalCost);
        order.setDeparture(optimalPath.get(0));
        order.setDestination(optimalPath.get(optimalPath.size() - 1));
        order.setTotalWeight(totalWeight);

        StringBuilder fullPath = new StringBuilder();
        for (String city : optimalPath) {
            fullPath.append(city).append(" → ");
        }

        fullPath.delete(fullPath.length() - 3, fullPath.length());
        order.setPath(fullPath.toString());

        dao.save(mapper.map(order, Order.class));
    }

    /**
     * removing entity by id
     * @param id entity id to remove
     */
    @Override
    @Transactional
    public void remove(int id) {
        log.info("remove(id) was called");
        Order order = dao.find(id);

        if (order.getStatus() != OrderStatus.CLOSED) {
            List<Cargo> cargos = order.getCargos();
            for (Cargo cargo : cargos)
                cargo.setStatus(CargoStatus.PREPARED);

            order.setCargos(new ArrayList<>());

            List<Driver> drivers = order.getDrivers();
            for (Driver driver : drivers)
                driver.setStatus(DriverStatus.RESTING);

            order.setDrivers(new ArrayList<>());

            Truck truck = order.getTruck();
            if (!(truck == null))
                truck.setStatus(TruckStatus.FREE);
        }

        dao.remove(order);
    }

    /**
     * adding truck to order
     * @param order order to add truck
     * @throws NullSourceException problems with adding truck
     */
    @Override
    @Transactional
    public void addTruck(OrderDto order) throws NullSourceException {
        log.info("addTruck(order) was called");
        if (order.getTruckInWork() != 0) {
            Order sourceOrder = dao.find(order.getId());
            Truck truck = mapper.map(truckService.find(order.getTruckInWork()), Truck.class);

            truck.setStatus(TruckStatus.BOOKED);
            truckService.update(mapper.map(truck, TruckDto.class));

            sourceOrder.setTruck(truck);

            dao.update(sourceOrder);
        } else {
            throw new NullSourceException();
        }
    }

    /**
     * adding drivers to order
     * @param order order to add drivers
     */
    @Override
    @Transactional
    public void addDrivers(OrderDto order) {
        log.info("addDrivers(order) was called");
        Order sourceOrder = dao.find(order.getId());
        List<Integer> driversIds = order.getDriversId();

        for (Integer driverId : driversIds) {
            Driver driver = mapper.map(driverService.find(driverId), Driver.class);
            driver.setStatus(DriverStatus.ON_SHIFT);
            driverService.update(mapper.map(driver, DriverDto.class));

            sourceOrder.getDrivers().add(driver);
        }

        if (sourceOrder.getDrivers().size() >= order.getRequiredDrivers())
            sourceOrder.setStatus(OrderStatus.READY);
        dao.update(sourceOrder);
    }

    /**
     * starting the order
     * @param id order id to start
     * @throws NotEnoughDriversException problems with starting the order
     */
    @Override
    @Transactional
    public void start(int id) throws NotEnoughDriversException {
        Order sourceOrder = dao.find(id);
        log.info("start(id) was called");

        if (sourceOrder.getDrivers().size() >=
                (mapper.map(sourceOrder, OrderDto.class)).getRequiredDrivers()) {
            Truck truck = mapper.map(sourceOrder.getTruck(), Truck.class);

            truck.setStatus(TruckStatus.BUSY);
            truckService.update(mapper.map(truck, TruckDto.class));

            List<Cargo> cargos = sourceOrder.getCargos();

            for (Cargo cargo : cargos) {
                cargo.setStatus(CargoStatus.DISPATCHED);
                cargoService.update(mapper.map(cargo, CargoDto.class));
            }

            sourceOrder.setStatus(OrderStatus.IN_PROGRESS);

            dao.update(sourceOrder);
        } else {
            throw new NotEnoughDriversException();
        }
    }

    /**
     * completing order
     * @param id order id to complete
     */
    @Override
    @Transactional
    public void complete(int id) {
        log.info("complete(id) was called");
        Order sourceOrder = dao.find(id);
        Truck truck = mapper.map(sourceOrder.getTruck(), Truck.class);

        truck.setStatus(TruckStatus.FREE);
        truck.setCity(sourceOrder.getDestination());
        truckService.update(mapper.map(truck, TruckDto.class));

        List<Cargo> cargos = sourceOrder.getCargos();

        for (Cargo cargo : cargos) {
            cargo.setStatus(CargoStatus.DELIEVERED);
            cargoService.update(mapper.map(cargo, CargoDto.class));
        }

        List<Driver> drivers = sourceOrder.getDrivers();

        for (Driver driver : drivers) {
            driver.setStatus(DriverStatus.RESTING);
            driver.setCity(sourceOrder.getDestination());
            driver.setHoursWorked(driver.getHoursWorked() + sourceOrder.getTripTime());
            driverService.update(mapper.map(driver, DriverDto.class));
        }

        sourceOrder.setStatus(OrderStatus.CLOSED);
        sourceOrder.setDrivers(null);
        sourceOrder.setCargos(null);
        sourceOrder.setTruck(null);
        dao.update(sourceOrder);
    }
}
