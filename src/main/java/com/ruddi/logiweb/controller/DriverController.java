package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.exception.exceptions.EntityDeleteException;
import com.ruddi.logiweb.exception.exceptions.EntityEditException;
import com.ruddi.logiweb.model.DriverStatus;
import com.ruddi.logiweb.service.api.CityService;
import com.ruddi.logiweb.service.api.DriverService;
import com.ruddi.logiweb.service.api.MQService;
import com.ruddi.logiweb.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controller for driver table
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class DriverController {

    /**
     * service for working with driver table
     */
    @Autowired
    DriverService driverService;

    /**
     * service for working with city table
     */
    @Autowired
    CityService cityService;

    /**
     * service for working with user table
     */
    @Autowired
    UserService userService;

    /**
     * service for working with mq
     */
    @Autowired
    MQService mqService;

    private static final String REDIRECT_PATH = "redirect:/drivers";
    private static final String MQ_TRIGGER = "driversUpd";

    /**
     * page with drivers list
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/drivers")
    public String driverStart(Model model) {
        model.addAttribute("driversList", driverService.getAll());
        model.addAttribute("newDriver", new DriverDto());
        model.addAttribute("cities", cityService.getAll());

        log.info("/drivers was requested");

        return "driversinfo";
    }

    /**
     * entity delete request url
     * @param id entity id to delete
     * @return jsp appearence
     * @throws EntityDeleteException problems with deleting entity
     */
    @GetMapping("/drivers/delete")
    public String deleteDriver(@RequestParam int id) throws EntityDeleteException {

        log.info("/drivers/delete was requested");

        try {
            driverService.remove(id);
            mqService.send(MQ_TRIGGER);
        } catch (Exception e) {
            throw new EntityDeleteException();
        }
        return REDIRECT_PATH;
    }

    /**
     * adding entity request url
     * @param driver entity to add
     * @return jsp appearence
     */
    @PostMapping("/drivers/add")
    public String addDriver(@ModelAttribute DriverDto driver) {

        log.info("/drivers/add was requested");

        driverService.save(driver);
        mqService.send(MQ_TRIGGER);

        return REDIRECT_PATH;
    }

    /**
     * editing entity request url
     * @param driver entity to edit
     * @return jsp appearence
     * @throws EntityEditException problems with editing entity
     */
    @PostMapping("/drivers/edit")
    public String editDriver(@ModelAttribute DriverDto driver) throws EntityEditException {
        DriverDto currentDriver = driverService.find(driver.getId());

        log.info("/drivers/edit was requested");

        if((currentDriver.getStatus() == DriverStatus.ON_SHIFT
                || currentDriver.getStatus() == DriverStatus.DRIVING) && driver.getStatus() == DriverStatus.RESTING )
            throw new EntityEditException();
        else {
            driverService.update(driver);
            mqService.send(MQ_TRIGGER);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("MANAGER"));

        return hasUserRole? REDIRECT_PATH : "redirect:/home";
    }

    /**
     * refreshing drivers hours worked request url
     * @return jsp appearence
     */
    @GetMapping("/drivers/refresh")
    public String refreshMonth() {

        log.info("/drivers/refresh was requested");

        driverService.refresh();
        mqService.send(MQ_TRIGGER);

        return REDIRECT_PATH;
    }
}
