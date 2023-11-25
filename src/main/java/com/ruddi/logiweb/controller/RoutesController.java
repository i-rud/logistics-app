package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.bl.GraphService;
import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.dto.OrderDto;
import com.ruddi.logiweb.exception.exceptions.NotEnoughDriversException;
import com.ruddi.logiweb.exception.exceptions.NullSourceException;
import com.ruddi.logiweb.service.api.*;
import com.ruddi.logiweb.telegram.MessageConverter;
import com.ruddi.logiweb.telegram.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * controller for order table
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class RoutesController {

    /**
     * service for working with cargo table
     */
    @Autowired
    CargoService cargoService;

    /**
     * service for working with distance table
     */
    @Autowired
    CountryService countryService;

    /**
     * service for working with driver table
     */
    @Autowired
    DriverService driverService;

    /**
     * service for working with truck table
     */
    @Autowired
    TruckService truckService;

    /**
     * service for working with order table
     */
    @Autowired
    OrderService orderService;

    /**
     * service for working with graph
     */
    @Autowired
    GraphService graphService;

    /**
     * service for working with mq
     */
    @Autowired
    MQService mqService;

    /**
     * service for working with telegram api
     */
    @Autowired
    MessageSender messageSender;

    private static final String REDIRECT_PATH = "redirect:/routes";
    private static final String MQ_TRIGGER = "updated";

    /**
     * controller home page
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/routes")
    public String routesStart(Model model) {

        log.info("/routes was requested");

        model.addAttribute("cargos", cargoService.getPrepared());
        model.addAttribute("trucks", truckService.getAvailable());
        model.addAttribute("newOrder", new OrderDto());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("drivers", driverService.getResting());
        model.addAttribute("allDrivers", driverService.getAll());
        model.addAttribute("newDriver", new DriverDto());

        return "routes";
    }

    /**
     * order adding request url
     * @param order entity to add
     * @return jsp appearence
     */
    @PostMapping("/routes/add")
    public String routeAdd(@ModelAttribute OrderDto order) {

        log.info("/routes/add was requested");

        orderService.save(order);
        mqService.send(MQ_TRIGGER);
        messageSender.send(MessageConverter.orderPosted(order));

        return REDIRECT_PATH;
    }

    /**
     * entity deleting request url
     * @param id entity id to delete
     * @return jsp appearence
     */
    @GetMapping("/routes/delete")
    public String orderDelete(@RequestParam int id) {

        log.info("/routes/delete was requested");

        orderService.remove(id);
        mqService.send(MQ_TRIGGER);
        messageSender.send(MessageConverter.orderDeleted(id));
        return REDIRECT_PATH;
    }

    /**
     * google maps api route displaying page
     * @param model frontend model
     * @param id entity id to display
     * @return jsp map appearence
     */
    @GetMapping("/routes/display")
    public String displayOrder(Model model, @RequestParam int id) {
        OrderDto order = orderService.find(id);

        log.info("/routes/display was requested");

        List<String> cities =
                new ArrayList<String>(Arrays.asList(order.getPath().split("→")));

        cities.remove(0);
        cities.remove(cities.size() - 1);

        model.addAttribute("cities", cities);

        model.addAttribute("order", order);
        model.addAttribute("from", order.getDeparture());
        model.addAttribute("to", order.getDestination());

        return "orderroute";
    }

    /**
     * adding truck request url
     * @param order order to add truck
     * @return jsp appearence
     * @throws NullSourceException provlems with adding truck
     */
    @PostMapping("/routes/truck")
    public String addTruck(@ModelAttribute OrderDto order) throws NullSourceException {
        log.info("/routes/truck was requested");

        try {
            orderService.addTruck(order);
            messageSender.send(MessageConverter.orderTruckAdded(orderService.find(order.getId())));
        } catch (Exception e) {
            throw new NullSourceException();
        }
        return REDIRECT_PATH;
    }

    /**
     * drivers adding request url
     * @param order order to add drivers
     * @return jsp appearence
     */
    @PostMapping("/routes/drivers")
    //@Secured("MANAGER")
    public String addDrivers(@ModelAttribute OrderDto order) {

        log.info("/routes/drivers was requested");

        orderService.addDrivers(order);
        messageSender.send(MessageConverter.orderDriversAdded(orderService.find(order.getId())));
        mqService.send(MQ_TRIGGER);
        return REDIRECT_PATH;
    }

    /**
     * start order request page
     * @param id order id to start
     * @return jsp appearence
     * @throws NotEnoughDriversException problems with starting the order
     */
    @GetMapping("routes/start")
    public String start(@RequestParam int id) throws NotEnoughDriversException {

        log.info("/routes/start was requested");

        try {
            orderService.start(id);
            mqService.send(MQ_TRIGGER);
            messageSender.send(MessageConverter.orderStarted(orderService.find(id)));
        } catch (Exception e) {
            throw new NotEnoughDriversException();
        }

        return REDIRECT_PATH;
    }

    /**
     * complete order request url
     * @param id order id to complete
     * @return jsp appearence
     */
    @GetMapping("routes/complete")
    public String complete(@RequestParam int id) {

        log.info("/routes/complete was requested");

        OrderDto order = orderService.find(id);
        orderService.complete(id);
        mqService.send(MQ_TRIGGER);
        messageSender.send(MessageConverter.orderCompleted(order));

        return REDIRECT_PATH;
    }
}
