package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.exception.exceptions.EntityDeleteException;
import com.ruddi.logiweb.exception.exceptions.EntityEditException;
import com.ruddi.logiweb.service.api.CityService;
import com.ruddi.logiweb.service.api.MQService;
import com.ruddi.logiweb.service.api.OrderService;
import com.ruddi.logiweb.service.api.TruckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

/**
 * controller for working with truck table
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class TruckController {

    /**
     * service for working with truck table
     */
    @Autowired
    TruckService truckService;

    /**
     * service for working with city table
     */
    @Autowired
    CityService cityService;

    /**
     * service for working with order table
     */
    @Autowired
    OrderService orderService;

    /**
     * service for working with mq
     */
    @Autowired
    MQService mqService;

    private static final String REDIRECT_PATH = "redirect:/trucks";
    private static final String MQ_TRIGGER = "trucksUpd";

    /**
     * home page
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/trucks")
    public String trucksStart(Model model) {

        log.info("/trucks was requested");

        model.addAttribute("truckslist", truckService.getAll());
        model.addAttribute("newTruck", new TruckDto());
        model.addAttribute("cities", cityService.getAll());

        return "trucksinfo";
    }

    /**
     * entity delete request url
     * @param id entity id to delete
     * @return jsp appearence
     * @throws EntityDeleteException problems with deleting entity
     */
    @GetMapping("/trucks/delete")
    public String truckDelete(@RequestParam int id) throws EntityDeleteException {

        log.info("/trucks/delete was requested");

        try {
            truckService.remove(id);
            mqService.send(MQ_TRIGGER);
        } catch (Exception e) {
            throw new EntityDeleteException();
        }

        return REDIRECT_PATH;
    }

    /**
     * entity adding request url
     * @param truck entity to delete
     * @return jsp appearence
     */
    @PostMapping("/trucks/add")
    public String addTruck(@ModelAttribute TruckDto truck) {

        log.info("/trucks/add was requested");

        truckService.save(truck);
        mqService.send(MQ_TRIGGER);
        return REDIRECT_PATH;
    }

    /**
     * entity editing request url
     * @param truck entity to edit
     * @return jsp appearence
     * @throws EntityEditException problems with editing entity
     */
    @PostMapping("/trucks/edit")
    public String editTruck(@ModelAttribute TruckDto truck) throws EntityEditException {
        log.info("/trucks/edit was requested");

        if (truck.getStatus().getStatus().equals("Busy"))
            throw new EntityEditException();
        else {
            truckService.update(truck);
            mqService.send(MQ_TRIGGER);
        }

        return REDIRECT_PATH;
    }
}
