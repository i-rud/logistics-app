package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.service.api.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * home controller
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class HomeController {

    /**
     * service for working with driver table
     */
    @Autowired
    DriverService driverService;

    /**
     * home page
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("drivers", driverService.getAll());
        model.addAttribute("newDriver", new DriverDto());

        log.info("/home was requested");

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        log.info("/home was requested");
        return "login";
    }
}
